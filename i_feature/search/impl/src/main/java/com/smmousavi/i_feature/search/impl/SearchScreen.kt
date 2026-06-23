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

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.smmousavi.i_core.designsystem.movies.ImdbMovieRow
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
    val textFieldState = rememberTextFieldState()
    val searchMoviesState by searchScreenViewModel.searchMovieState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        snapshotFlow { textFieldState.text.toString() }
            .collect(searchScreenViewModel::onQueryChange)
    }

    SearchScreenContent(
        modifier = modifier,
        textFieldState = textFieldState,
        searchMoviesState = searchMoviesState,
    )
}

@Composable
fun SearchScreenContent(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    searchMoviesState: UiState<List<MovieItemModel>>,
    isFocused: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true,
) {
    Column(modifier = modifier.fillMaxSize()) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    color = when {
                        isFocused -> MaterialTheme.colorScheme.primary.copy(alpha = 0.05f)
                        enabled -> MaterialTheme.colorScheme.surface
                        else -> MaterialTheme.colorScheme.secondaryFixed
                    },
                    shape = RoundedCornerShape(8.dp),
                )
                .border(
                    width = 1.dp,
                    color = when {
                        isError -> MaterialTheme.colorScheme.error
                        isFocused -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.outline
                    },
                    shape = RoundedCornerShape(8.dp),
                )
                .padding(12.dp),
            state = textFieldState,
            lineLimits = TextFieldLineLimits.SingleLine,
            textStyle = MaterialTheme.typography.bodyLarge,
            decorator = { innerBox ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    if (textFieldState.text.isEmpty()) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterStart),
                            text = "Search Movies",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        )
                    }
                    Icon(
                        modifier = Modifier.align(Alignment.CenterEnd),
                        tint = MaterialTheme.colorScheme.outline,
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                    )
                    innerBox()
                }
            },
        )
        when (searchMoviesState) {
            is UiState.Error -> {
            }

            UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    NiaLoadingWheel(contentDesc = "Loading Search Result")
                }
            }

            is UiState.Success -> {
                MoviesRowLazyColumn(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    items = searchMoviesState.data,
                ) { item ->
                    ImdbMovieRow(data = item) { }
                }
            }
        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() {
    SearchScreenContent(
        textFieldState = rememberTextFieldState(),
        searchMoviesState = UiState.Success(
            listOf(
                MovieItem.DEFAULT1.toModel(),
                MovieItem.DEFAULT2.toModel(),
                MovieItem.DEFAULT3.toModel(),
            ),
        ),
        isFocused = false,
        isError = false,
        enabled = true,
    )
}
