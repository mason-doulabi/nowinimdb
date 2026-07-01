package com.smmousavi.i_feature.movies.impl.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ListAdapter
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.presentation.collectOnLifecycleStarted
import com.smmousavi.i_core.presentation.xml.HorizontalSpaceItemDecoration
import com.smmousavi.i_feature.movies.impl.MoviesScreenViewModel
import com.smmousavi.i_feature.movies.impl.databinding.FragmentMoviesBinding
import com.smmousavi.i_feature.movies.impl.xml.rv.MoviesListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    val binding get() = _binding!!

    private val viewModel: MoviesScreenViewModel by viewModels()

    private val top250Adapter by lazy {
        MoviesListAdapter(
            onMovieClick = { /* handle click */ },
            onFavoriteClick = { viewModel.setMovieAsFavorite(it) },
        )
    }

    private val popularAdapter by lazy {
        MoviesListAdapter(
            onMovieClick = { /* handle click */ },
            onFavoriteClick = { viewModel.setMovieAsFavorite(it) },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesBinding.inflate(
            layoutInflater,
            container,
            false,
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeViewModel()

        viewModel.observeTop250Movies()
        viewModel.observeMostPopularMovies()
        viewModel.observeGenres()
        viewModel.observeTypes()
    }

    private fun setupRecyclerViews() {
        binding.rvTop250Movies.addItemDecoration(HorizontalSpaceItemDecoration(32))
        binding.rvTop250Movies.adapter = top250Adapter
        binding.rvMostPopularMovies.addItemDecoration(HorizontalSpaceItemDecoration(32))
        binding.rvMostPopularMovies.adapter = popularAdapter
    }

    private fun observeViewModel() {
        collectOnLifecycleStarted {
            viewModel.top250MoviesState.collect { state ->
                handleUiState(state, top250Adapter)
            }
        }
        collectOnLifecycleStarted {
            viewModel.mostPopularMoviesState.collect { state ->
                handleUiState(state, popularAdapter)
            }
        }
    }

    private fun <T> handleUiState(
        state: UiState<List<T>>,
        adapter: ListAdapter<T, *>,
    ) {
        when (state) {
            is UiState.Error -> {
                binding.prgLoading.visibility = View.GONE
            }

            UiState.Idle -> {}
            UiState.Loading -> {
                binding.prgLoading.visibility = View.VISIBLE
            }

            is UiState.Success -> {
                binding.prgLoading.visibility = View.GONE
                adapter.submitList(state.data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
