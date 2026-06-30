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
import com.smmousavi.domain.usecase.search.recent.RecentSearchesUseCase
import com.smmousavi.i_core.common.error.toImdbError
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.presentation.snackbar.SnackbarEvent
import com.smmousavi.i_core.presentation.snackbar.SnackbarManager
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
    val recentSearchesUseCase: RecentSearchesUseCase,
    val snackbarManager: SnackbarManager,
) : ViewModel() {

    private val _searchQueryState = MutableStateFlow("")
    val searchQueryState = _searchQueryState.asStateFlow()

    private val _recentSearchesState = MutableStateFlow<List<MovieModel>>(emptyList())
    val recentSearchesState = _recentSearchesState.asStateFlow()

    fun onQueryChange(query: String) {
        _searchQueryState.value = query
    }

    @OptIn(FlowPreview::class)
    val searchMovieState = _searchQueryState
        .debounce(300) // prevent successive requests to server
        .distinctUntilChanged() // avoid making repetitive requests
        .flatMapLatest { query -> // cancel all the previous request and takes only the last one
            if (query.isBlank()) {
                flowOf(UiState.Idle)
            } else {
                searchMovieUseCase.searchMovie(query)
                    .map { result ->
                        result.fold(
                            onSuccess = { data ->
                                UiState.Success(data)
                            },
                            onFailure = { error ->
                                UiState.Error(error.message, error)
                            },
                        )
                    }
                    .onStart { emit(UiState.Loading) } // on flow starts emitting
                    .catch { e -> // catch any errors on any emits
                        emit(UiState.Error(e.message, e))
                        snackbarManager.emit(SnackbarEvent.Error(e.toImdbError()))
                    }
            }
        }
        // convert cold flow to hot flow(Flow to StateFlow), otherwise,
        // every new collect would start the whole pipeline again
        .stateIn(
            scope = viewModelScope, // binding to the lifecycle of the viewModel
            started = SharingStarted.WhileSubscribed(5000), // Keep upstream alive for 5 seconds after last subscriber disappears.
            initialValue = UiState.Idle, // every StateFlow should have an initial value
        )

    @OptIn(FlowPreview::class)
    val autoCompleteState = _searchQueryState
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.length < 2) {
                flowOf(UiState.Idle)
            } else {
                searchMovieUseCase.autoComplete(query)
                    .map { result ->
                        result.fold(
                            onSuccess = { data ->
                                UiState.Success(data)
                            },
                            onFailure = { error ->
                                UiState.Error(error.message, error)
                            },
                        )
                    }
                    .onStart { emit(UiState.Loading) }
                    .catch { e ->
                        emit(UiState.Error(e.message, e))
                        snackbarManager.emit(SnackbarEvent.Error(e.toImdbError()))
                    }
            }
        }

        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Idle,
        )

    fun setMovieAsRecentlySearched(movie: MovieModel, recentlySearched: Boolean) {
        viewModelScope.launch {
            recentSearchesUseCase.setMovieAsRecentlySearched(movie, recentlySearched)
        }
    }

    fun recentlySearchedMovies() {
        viewModelScope.launch {
            recentSearchesUseCase.getRecentlySearchedMovies().collect { data ->
                _recentSearchesState.value = data
            }
        }
    }
}