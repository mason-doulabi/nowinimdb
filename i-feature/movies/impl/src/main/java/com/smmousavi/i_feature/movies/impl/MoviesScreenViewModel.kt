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
import com.smmousavi.domain.usecase.movies.top250.Top250MoviesUseCase
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.generalinfo.MoviesGeneralInfoModel
import com.smmousavi.i_core.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val generalUseCase: MoviesGeneralInfoUseCase,
    private val top250UseCase: Top250MoviesUseCase,
    private val favoriteUseCase: FavoriteMoviesUseCase,
) : ViewModel() {
    private val _generaInfoUiState =
        MutableStateFlow<UiState<MoviesGeneralInfoModel>>(UiState.Loading)
    val generalInfoUiState = _generaInfoUiState.asStateFlow()

    private val _top250MoviesState =
        MutableStateFlow<UiState<List<MovieItemModel>>>(UiState.Loading)
    val top250MoviesState = _top250MoviesState.asStateFlow()

    private val _favoriteMoviesState =
        MutableStateFlow<List<MovieItemModel>>(listOf())
    val favoriteMoviesState = _favoriteMoviesState.asStateFlow()

    fun getGeneralInfo() {
        viewModelScope.launch {
            generalUseCase().collect { info ->
                _generaInfoUiState.value = UiState.Success(info)
            }
        }
    }

    fun getTop250Movies() {
        viewModelScope.launch {
            top250UseCase().collect {
                it.fold(
                    onSuccess = { data -> _top250MoviesState.value = UiState.Success(data) },
                    onFailure = {
                        // display error
                    },
                )
            }
        }
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            favoriteUseCase.getFavoriteMovies().collect {
                _favoriteMoviesState.value = it
            }
        }
    }

    fun setFavoriteMovie(movie: MovieItemModel) {
        viewModelScope.launch {
            favoriteUseCase.upsertMovie(movie)
        }
    }
}