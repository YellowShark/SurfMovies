package ru.yellowshark.favoritemovies.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ru.yellowshark.favoritemovies.R
import ru.yellowshark.favoritemovies.databinding.FragmentHomeBinding
import ru.yellowshark.favoritemovies.domain.model.Movie
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private lateinit var adapter: MovieAdapter
    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeViewModel()
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
}