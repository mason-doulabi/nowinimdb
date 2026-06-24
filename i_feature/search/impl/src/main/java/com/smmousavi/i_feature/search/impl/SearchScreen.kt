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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.google.samples.apps.nowinandroid.core.designsystem.icon.NiaIcons
import com.smmousavi.i_core.designsystem.movies.ImdbMovieRow
import com.smmousavi.i_core.designsystem.movies.ImdbMovieTitleRow
import com.smmousavi.i_core.model.movies.MovieItem
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.mapper.MoviesModelMapper.toModel
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.presentation.movies.MoviesRowLazyColumn

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    searchScreenViewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val searchMoviesState by searchScreenViewModel.searchMovieState.collectAsStateWithLifecycle()
    val autoCompleteState by searchScreenViewModel.autoCompleteState.collectAsStateWithLifecycle()
    val searchQuery by searchScreenViewModel.searchQueryState.collectAsStateWithLifecycle()

    SearchScreenContent(
        modifier = modifier,
        searchMoviesState = searchMoviesState,
        autoCompleteState = autoCompleteState,
        query = searchQuery,
    ) { query ->
        searchScreenViewModel.onQueryChange(query)
    }
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    searchMoviesState: UiState<List<MovieItemModel>>,
    autoCompleteState: UiState<List<MovieItemModel>>,
    query: String,
    onQueryChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    var isSearchFocused by remember { mutableStateOf(false) }

    val showSuggestion = isSearchFocused && query.length >= 2

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier.fillMaxSize()) {
        SearchBar(
            modifier = Modifier.padding(top = 8.dp),
            query = query,
            focusRequester = focusRequester,
            onQueryChange = onQueryChange,
            onFocusChange = {
                isSearchFocused = it
            },
        )

        AnimatedVisibility(
            visible = showSuggestion,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            when (autoCompleteState) {
                is UiState.Error -> {}

                UiState.Idle -> {}

                UiState.Loading -> {}

                is UiState.Success -> {
                    AutoCompleteSuggestions(
                        modifier = Modifier.padding(top = 4.dp),
                        data = autoCompleteState.data,
                    ) { item ->
                        onQueryChange(item.title)
                        keyboardController?.hide()
                        focusManager.clearFocus()
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
                ) { }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                MaterialTheme.colorScheme.surface,
                RoundedCornerShape(8.dp),
            )
            .focusRequester(focusRequester)
            .onFocusChanged { onFocusChange(it.isFocused) },
        value = query,
        onValueChange = onQueryChange,
        singleLine = true,
        shape = RoundedCornerShape(32.dp),
        textStyle = MaterialTheme.typography.bodyLarge,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = "Search Movies",
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onQueryChange("")
                    },
                ) {
                    Icon(
                        imageVector = NiaIcons.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
        ),
    )
}

@Composable
fun AutoCompleteSuggestions(
    modifier: Modifier = Modifier,
    data: List<MovieItemModel>,
    onSuggestionClick: (MovieItemModel) -> Unit,
) {
    MoviesRowLazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        items = data,
    ) { item ->
        ImdbMovieTitleRow(data = item) {
            onSuggestionClick(item)
        }
    }
}

@Composable
fun SearchResult(
    modifier: Modifier = Modifier,
    data: List<MovieItemModel>,
    onResultClick: (MovieItemModel) -> Unit,
) {
    MoviesRowLazyColumn(
        modifier = modifier.padding(horizontal = 8.dp),
        items = data,
    ) { item ->
        ImdbMovieRow(data = item) {
            onResultClick(item)
        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    SearchScreenContent(
        searchMoviesState = UiState.Success(
            listOf(
                MovieItem.DEFAULT1.toModel(),
                MovieItem.DEFAULT2.toModel(),
                MovieItem.DEFAULT3.toModel(),
            ),
        ),
        autoCompleteState = UiState.Success(
            listOf(
                MovieItem.DEFAULT3.toModel(),
            ),
        ),
        query = "Dark Night",
    ) {}
}
