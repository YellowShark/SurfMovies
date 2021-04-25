package ru.yellowshark.favoritemovies.ui.home

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.favoritemovies.R
import ru.yellowshark.favoritemovies.databinding.FragmentHomeBinding
import ru.yellowshark.favoritemovies.domain.model.Movie
import ru.yellowshark.favoritemovies.utils.hideKeyboard
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), SwipeRefreshLayout.OnRefreshListener {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var adapter: MovieAdapter

    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
        initListeners()
    }

    override fun onRefresh() {
        with(binding) {
            homeRefresher.isRefreshing = false
            homeSearchEt.apply {
                setText("")
            }
            removeFocus()
            viewModel.getMovies()
        }
    }


    private fun observeViewModel() {
        viewModel.movies.observe(requireActivity(), {
            adapter.movies = it
        })
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

    fun removeFocus() {
        with(binding) {
            homeSearchEt.isFocusable = false
            requireContext().hideKeyboard(root)
        }
    }
}