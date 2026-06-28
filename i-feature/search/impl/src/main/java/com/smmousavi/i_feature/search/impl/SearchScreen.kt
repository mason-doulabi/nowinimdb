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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.smmousavi.i_core.designsystem.components.ImdbDetailsRow
import com.smmousavi.i_core.designsystem.components.ImdbSearchBar
import com.smmousavi.i_core.designsystem.components.ImdbTitleRow
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.model.movies.mapper.MovieModelMapper.toModel
import com.smmousavi.i_core.presentation.UiState

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onMovieClicked: (String) -> Unit,
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val searchMoviesState by searchScreenViewModel.searchMovieState.collectAsStateWithLifecycle()
    val autoCompleteState by searchScreenViewModel.autoCompleteState.collectAsStateWithLifecycle()
    val searchQuery by searchScreenViewModel.searchQueryState.collectAsStateWithLifecycle()
    val recentSearches by searchScreenViewModel.recentSearchesState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        searchScreenViewModel.recentlySearchedMovies()
    }

    SearchScreenContent(
        modifier = modifier,
        searchMoviesState = searchMoviesState,
        autoCompleteState = autoCompleteState,
        recentSearches = recentSearches,
        query = searchQuery,
        onRecentlySearched = { movie, recentlySearched ->
            searchScreenViewModel.setMovieAsRecentlySearched(movie, recentlySearched)
        },
        onMovieClicked = onMovieClicked,
    ) { query ->
        searchScreenViewModel.onQueryChange(query)
    }
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    searchMoviesState: UiState<List<MovieModel>>,
    autoCompleteState: UiState<List<MovieModel>>,
    recentSearches: List<MovieModel>,
    query: String,
    onRecentlySearched: (MovieModel, Boolean) -> Unit,
    onMovieClicked: (String) -> Unit,
    onQueryChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    var isSearchFocused by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier.fillMaxSize()) {
        ImdbSearchBar(
            modifier = Modifier.padding(top = 8.dp),
            query = query,
            focusRequester = focusRequester,
            onQueryChange = onQueryChange,
            onFocusChange = {
                isSearchFocused = it
            },
        )

        AnimatedVisibility(
            visible = isSearchFocused,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            when (autoCompleteState) {
                is UiState.Error -> {}

                UiState.Idle -> {
                    if (recentSearches.isNotEmpty()) {
                        AutoCompleteSuggestions(
                            modifier = Modifier.padding(top = 4.dp),
                            data = recentSearches,
                            onDeleteSuggestionClick = { item -> onRecentlySearched(item, false) },
                        ) { item ->
                            onQueryChange(item.title)
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            onRecentlySearched(item, true)
                        }
                    }
                }

                UiState.Loading -> {}

                is UiState.Success -> {
                    if (autoCompleteState.data.isNotEmpty()) {
                        AutoCompleteSuggestions(
                            modifier = Modifier.padding(top = 4.dp),
                            data = autoCompleteState.data,
                            onDeleteSuggestionClick = null,
                        ) { item ->
                            onQueryChange(item.title)
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            onRecentlySearched(item, true)
                        }
                    }
                }
            }
        }

        when (searchMoviesState) {
            is UiState.Error -> {}

            UiState.Idle -> {}

            UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    NiaLoadingWheel(contentDesc = "Loading Search Result")
                }
            }

            is UiState.Success -> {
                SearchResult(
                    modifier = Modifier.padding(top = 16.dp),
                    data = searchMoviesState.data,
                ) { item ->
                    onMovieClicked(item.id)
                    onRecentlySearched(item, true)
                }
            }
        }
    }
}

@Composable
fun AutoCompleteSuggestions(
    modifier: Modifier = Modifier,
    data: List<MovieModel>,
    onDeleteSuggestionClick: ((MovieModel) -> Unit)?,
    onSuggestionClick: (MovieModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(
            items = data.distinctBy { it.id },
            key = { it.id },
        ) { item ->
            ImdbTitleRow(
                data = item,
                onDeleteClick = { onDeleteSuggestionClick?.invoke(item) },
            ) {
                onSuggestionClick(item)
            }
        }
    }
}

@Composable
fun SearchResult(
    modifier: Modifier = Modifier,
    data: List<MovieModel>,
    onResultClick: (MovieModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = data.distinctBy { it.id },
            key = { it.id },
        ) { item ->
            ImdbDetailsRow(data = item) {
                onResultClick(item)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    SearchScreenContent(
        searchMoviesState = UiState.Success(
            listOf(
                Movie.DEFAULT1.toModel(),
                Movie.DEFAULT2.toModel(),
                Movie.DEFAULT3.toModel(),
            ),
        ),
        autoCompleteState = UiState.Success(
            listOf(
                Movie.DEFAULT3.toModel(),
            ),
        ),
        recentSearches = listOf(
            Movie.DEFAULT2.toModel(),
            Movie.DEFAULT3.toModel(),
        ),
        query = "The Sha",
        onRecentlySearched = { _, _ -> },
        onMovieClicked = {},
    ) {}
}
