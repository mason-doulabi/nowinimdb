/*
 * Copyright 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smmousavi.i_feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smmousavi.domain.usecase.movies.details.MovieDetailsUseCase
import com.smmousavi.domain.usecase.movies.favorite.FavoriteMoviesUseCase
import com.smmousavi.i_core.model.movies.movie.MovieCast
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.model.movies.movie.MoviePoster
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.presentation.snackbar.SnackbarEvent
import com.smmousavi.i_core.presentation.snackbar.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsScreenViewModel @Inject constructor(
    private val movieDetailsUseCase: MovieDetailsUseCase,
    private val favoriteUseCase: FavoriteMoviesUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _moviePosterState = MutableStateFlow<UiState<MoviePoster>>(UiState.Loading)
    val moviePosterState = _moviePosterState.asStateFlow()

    private val _movieDetailsState = MutableStateFlow<UiState<MovieModel>>(UiState.Loading)
    val movieDetailsState = _movieDetailsState.asStateFlow()

    private val _movieCastsState = MutableStateFlow<UiState<List<MovieCast>>>(UiState.Loading)
    val movieCastsState = _movieCastsState.asStateFlow()

    fun observeMovieDetails(id: String) {
        viewModelScope.launch {
            movieDetailsUseCase.getMovieDetailsById(id).collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _movieDetailsState.value = UiState.Success(data)
                    },
                    onFailure = { e ->
                        _movieDetailsState.value = UiState.Error(e.message, e)
                    },
                )
            }
        }
    }

    fun observeMoviePoster(id: String) {
        viewModelScope.launch {
            movieDetailsUseCase.getMoviePosterById(id).collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _moviePosterState.value = UiState.Success(data)
                    },
                    onFailure = { e ->
                        _moviePosterState.value = UiState.Error(e.message, e)
                    },
                )
            }
        }
    }

    fun observeMovieCasts(id: String) {
        viewModelScope.launch {
            movieDetailsUseCase.getMovieCastsById(id).collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _movieCastsState.value = UiState.Success(data)
                    },
                    onFailure = { e ->
                        _moviePosterState.value = UiState.Error(e.message, e)
                    },
                )
            }
        }
    }

    fun setMovieAsFavorite(movie: MovieModel) {
        viewModelScope.launch {
            favoriteUseCase.upsertMovie(movie)

            if (movie.favorite) {
                snackbarManager.emit(SnackbarEvent.FavoriteAdded(movie.title))
            } else {
                snackbarManager.emit(SnackbarEvent.FavoriteRemoved(movie.title))
            }
        }
    }
}