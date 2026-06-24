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

package com.smmousavi.i_feature.search.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smmousavi.domain.usecase.search.SearchMovieUseCase
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.mapper.MoviesModelMapper.toModel
import com.smmousavi.i_core.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    val searchMovieUseCase: SearchMovieUseCase,
) : ViewModel() {

    private val _searchQueryState = MutableStateFlow("")
    val searchQueryState = _searchQueryState.asStateFlow()

    private val _recentlySearchedMoviesState = MutableStateFlow<List<MovieItemModel>>(emptyList())
    val recentlySearchedMoviesState = _recentlySearchedMoviesState.asStateFlow()

    fun onQueryChange(query: String) {
        _searchQueryState.value = query
    }

    @OptIn(FlowPreview::class)
    val searchMovieState = _searchQueryState
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(UiState.Idle)
            } else {
                searchMovieUseCase.searchMovie(query)
                    .map { result ->
                        result.fold(
                            onSuccess = { data ->
                                UiState.Success(data.map { it.toModel() })
                            },
                            onFailure = { error ->
                                UiState.Error(error.message, error)
                            },
                        )
                    }
                    .onStart { emit(UiState.Loading) }
                    .catch { e ->
                        emit(UiState.Error(e.message, e))
                    }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Idle,
        )

    @OptIn(FlowPreview::class)
    val autoCompleteState = _searchQueryState
        .debounce(300) // prevents successive requests to server
        .distinctUntilChanged() // avoids requesting repetitive requests
        .flatMapLatest { query -> // cancels previous request and takes only one last request
            if (query.length < 2) {
                flowOf(UiState.Idle)
            } else {
                searchMovieUseCase.autoComplete(query)
                    .map { result ->
                        result.fold(
                            onSuccess = { data ->
                                UiState.Success(
                                    data
                                        .take(5)
                                        .map { it.toModel() },
                                )
                            },
                            onFailure = { error ->
                                UiState.Error(error.message, error)
                            },
                        )
                    }
                    .onStart { emit(UiState.Loading) } // before doing any emits
                    .catch { e -> // catch any errors on any emits
                        emit(
                            UiState.Error(
                                e.message,
                                e,
                            ),
                        )
                    }
            }
        }
        // converts cold flow to hot flow(Flow to StateFlow), otherwise, every new collect would start the whole pipeline again
        .stateIn(
            scope = viewModelScope, // binding to the lifecycle of the viewModel
            started = SharingStarted.WhileSubscribed(5000), // Keep upstream alive for 5 seconds after last subscriber disappears.
            initialValue = UiState.Idle, // every StateFlow should have an initial value
        )

    fun setMovieAsRecentlySearched(movie: MovieItemModel, recentlySearched: Boolean) {
        viewModelScope.launch {
            searchMovieUseCase.setMovieAsRecentlySearched(movie, recentlySearched)
        }
    }

    fun getRecentlySearchedMovies() {
        viewModelScope.launch {
            searchMovieUseCase.getRecentlySearchedMovies().collect { data ->
                _recentlySearchedMoviesState.value = data
            }
        }
    }
}