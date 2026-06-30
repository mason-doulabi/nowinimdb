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

package com.smmousavi.i_feature.movies.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smmousavi.domain.usecase.generalinfo.MoviesGeneralInfoUseCase
import com.smmousavi.domain.usecase.movies.favorite.FavoriteMoviesUseCase
import com.smmousavi.domain.usecase.movies.top.TopMoviesUseCase
import com.smmousavi.i_core.common.error.toImdbError
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.presentation.snackbar.SnackbarEvent
import com.smmousavi.i_core.presentation.snackbar.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val generalInfoUseCase: MoviesGeneralInfoUseCase,
    private val topMoviesUsesCase: TopMoviesUseCase,
    private val favoriteUseCase: FavoriteMoviesUseCase,
    private val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _genresState = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val genresState = _genresState.asStateFlow()

    private val _typesState = MutableStateFlow<UiState<List<String>>>(UiState.Loading)
    val typesState = _typesState.asStateFlow()

    private val _top250MoviesState =
        MutableStateFlow<UiState<List<MovieModel>>>(UiState.Loading)
    val top250MoviesState = _top250MoviesState.asStateFlow()

    private val _mostPopularMoviesState =
        MutableStateFlow<UiState<List<MovieModel>>>(UiState.Loading)
    val mostPopularMoviesState = _mostPopularMoviesState.asStateFlow()

    fun observeGenres() {
        viewModelScope.launch {
            generalInfoUseCase.getGenres().collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _genresState.value = UiState.Success(data)
                    },
                    onFailure = { e ->
                        _genresState.value = UiState.Error(e.message, e)
                        snackbarManager.emit(SnackbarEvent.Error(e.toImdbError()))
                    },
                )
            }
        }
    }

    fun observeTypes() {
        viewModelScope.launch {
            generalInfoUseCase.getTypes().collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _typesState.value = UiState.Success(data)
                    },
                    onFailure = { e ->
                        _typesState.value = UiState.Error(e.message, e)
                        snackbarManager.emit(SnackbarEvent.Error(e.toImdbError()))
                    },
                )
            }
        }
    }

    fun observeTop250Movies() {
        viewModelScope.launch {
            topMoviesUsesCase.top250Movies().collect {
                it.fold(
                    onSuccess = { data -> _top250MoviesState.value = UiState.Success(data) },
                    onFailure = { e->
                        _top250MoviesState.value = UiState.Error(e.message, e)
                        snackbarManager.emit(SnackbarEvent.Error(e.toImdbError()))
                    },
                )
            }
        }
    }

    fun observeMostPopularMovies() {
        viewModelScope.launch {
            topMoviesUsesCase.mostPopularMovies().collect { result ->
                result.fold(
                    onSuccess = { data ->
                        _mostPopularMoviesState.value = UiState.Success(data)
                    },
                    onFailure = { e ->
                        _mostPopularMoviesState.value = UiState.Error(e.message, e)
                        snackbarManager.emit(SnackbarEvent.Error(e.toImdbError()))
                    },
                )
            }
        }
    }

    fun setMovieAsFavorite(movie: MovieModel) {
        viewModelScope.launch {
            favoriteUseCase.upsertMovie(movie)

            snackbarManager.emit(
                if (movie.favorite) {
                    SnackbarEvent.FavoriteAdded(movie.title)
                } else {
                    SnackbarEvent.FavoriteRemoved(movie.title)
                }
            )
        }
    }
}