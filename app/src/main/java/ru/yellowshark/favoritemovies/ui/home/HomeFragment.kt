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
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getMovies()
        viewModel.movies.observe(requireActivity(), {
            val movies = it
            Log.d("TAG", "observeViewModel: $movies")
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
        })
    }
}