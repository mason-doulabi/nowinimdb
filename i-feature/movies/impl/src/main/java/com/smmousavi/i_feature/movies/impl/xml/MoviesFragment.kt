package com.smmousavi.i_feature.movies.impl.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.SimpleItemAnimator
import com.smmousavi.i_core.designsystem.R
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.presentation.collectOnLifecycleStarted
import com.smmousavi.i_core.presentation.xml.HorizontalSpaceItemDecoration
import com.smmousavi.i_core.presentation.xml.VerticalSpaceItemDecoration
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

    private val top250MoviesAdapter by lazy {
        MoviesListAdapter(
            onMovieClick = { /* handle click */ },
            onFavoriteClick = {
                viewModel.setMovieAsFavorite(
                    it.copy(
                        favorite = it.favorite.not(),
                    ),
                )
            },
        )
    }

    private val mostPopularMoviesAdapter by lazy {
        MoviesListAdapter(
            onMovieClick = { /* handle click */ },
            onFavoriteClick = {
                viewModel.setMovieAsFavorite(
                    it.copy(
                        favorite = it.favorite.not(),
                    ),
                )
            },
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
        setupTop250MoviesAdapter()
        setMostPopularMoviesAdapter()
        observeViewModel()

        viewModel.observeTop250Movies()
        viewModel.observeMostPopularMovies()
        viewModel.observeGenres()
        viewModel.observeTypes()
    }

    private fun setupTop250MoviesAdapter() = with(binding.rvTop250Movies) {
        adapter = top250MoviesAdapter
        addItemDecoration(
            HorizontalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.small_space),
            ),
        )
        (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
    }

    private fun setMostPopularMoviesAdapter() = with(binding.rvMostPopularMovies) {
        adapter = mostPopularMoviesAdapter
        addItemDecoration(
            HorizontalSpaceItemDecoration(
                resources.getDimensionPixelSize(R.dimen.small_space),
            ),
        )
        (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
    }

    private fun observeViewModel() {
        collectOnLifecycleStarted {
            viewModel.top250MoviesState.collect { state ->
                renderUiState(state, top250MoviesAdapter)
            }
        }

        collectOnLifecycleStarted {
            viewModel.mostPopularMoviesState.collect { state ->
                renderUiState(state, mostPopularMoviesAdapter)
            }
        }
    }

    private fun renderUiState(
        state: UiState<List<MovieModel>>,
        adapter: ListAdapter<MovieModel, *>,
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
