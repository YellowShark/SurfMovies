package ru.yellowshark.favoritemovies.ui.home

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.favoritemovies.R
import ru.yellowshark.favoritemovies.databinding.FragmentHomeBinding
import ru.yellowshark.favoritemovies.domain.exception.NoConnectivityException
import ru.yellowshark.favoritemovies.domain.exception.NoResultsException
import ru.yellowshark.favoritemovies.domain.model.Movie
import ru.yellowshark.favoritemovies.ui.base.ViewState.*
import ru.yellowshark.favoritemovies.utils.hideKeyboard

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), SwipeRefreshLayout.OnRefreshListener {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var adapter: MovieAdapter
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(HomeViewModel::class.java) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
        initListeners()
    }

    override fun onRefresh() {
        with(binding) {
            homeRefresher.isRefreshing = false
            homeSearchEt.setText("")
            removeFocus()
            viewModel.getMovies()
        }
    }


    private fun observeViewModel() {
        viewModel.movies.observe(requireActivity(), { state ->
            when (state) {
                is Loading -> { showLoading() }
                is Success -> { showData(state.data) }
                is Error -> {
                    when (state.throwable) {
                        is NoResultsException -> { showNoResults() }
                        is NoConnectivityException -> { showNoInternet() }
                        else -> { showError() }
                    }
                }
            }
        })
    }

    private fun showLoading() {
        with(binding) {
            homeMoviesRv.isVisible = false
            homeProgressBarWrapper.isVisible = true
            homeErrorWrapper.isVisible = false
            homeNoResultsWrapper.isVisible = false
        }
    }

    private fun showData(data: List<Movie>) {
        adapter.movies = data
        with(binding) {
            homeMoviesRv.isVisible = true
            homeProgressBarWrapper.isVisible = false
            homeErrorWrapper.isVisible = false
            homeNoResultsWrapper.isVisible = false
        }
    }

    private fun showNoResults() {
        with(binding) {
            homeMoviesRv.isVisible = false
            homeProgressBarWrapper.isVisible = false
            homeErrorWrapper.isVisible = false
            homeNoResultsWrapper.isVisible = true
            homeNoResultsTv.text = String.format(getString(R.string.no_results_message), homeSearchEt.text.toString())
        }
    }

    private fun showNoInternet() {
        Snackbar.make(binding.root, getString(R.string.error_no_internet), Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun showError() {
        with(binding) {
            homeMoviesRv.isVisible = false
            homeProgressBarWrapper.isVisible = false
            homeErrorWrapper.isVisible = true
            homeNoResultsWrapper.isVisible = false
        }
    }

    private fun initRecyclerView() {
        adapter = MovieAdapter(onItemClickListener = {
            Toast.makeText(requireContext(), it.title, Toast.LENGTH_SHORT).show()
        }, onLikeClickListener = { movie: Movie, pos: Int ->
            adapter.updateLike(pos)
            //save result local
        })
        binding.homeMoviesRv.adapter = adapter
    }

    private fun initListeners() {
        with(binding) {
            homeRefreshIv.setOnClickListener { viewModel.getMovies() }
            homeRefresher.setOnRefreshListener(this@HomeFragment)
            homeSearchEt.setOnEditorActionListener(TextView.OnEditorActionListener { _: TextView, _: Int, event: KeyEvent? ->
                if (event != null) {
                    if (!event.isShiftPressed) {
                        viewModel.searchMovies(homeSearchEt.text.toString())
                        removeFocus()
                        return@OnEditorActionListener true
                    }
                    return@OnEditorActionListener false
                }
                viewModel.searchMovies(homeSearchEt.text.toString())
                removeFocus()
                return@OnEditorActionListener true
            })
        }
    }

    private fun removeFocus() {
        with(binding) {
            homeSearchEt.clearFocus()
            requireContext().hideKeyboard(root)
        }
    }
}