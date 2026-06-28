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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.smmousavi.i_core.designsystem.components.ImdbFilterChip
import com.smmousavi.i_core.designsystem.components.ImdbMovieCard
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.designsystem.layouts.MoviesTitledLazyRow
import com.smmousavi.i_core.model.movies.mapper.MovieModelMapper.toModel
import com.smmousavi.i_core.model.movies.movie.Movie

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesScreenViewModel = hiltViewModel(),
    onMovieClicked: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.observeTop250Movies()
        viewModel.observeGenres()
        viewModel.observeTypes()
        viewModel.observeMostPopularMovies()
    }

    val top250MoviesState by viewModel.top250MoviesState.collectAsStateWithLifecycle()
    val movieTypesState by viewModel.typesState.collectAsStateWithLifecycle()
    val moviesGenresState by viewModel.genresState.collectAsStateWithLifecycle()
    val mostPopularMoviesState by viewModel.mostPopularMoviesState.collectAsStateWithLifecycle()

    MoviesScreenContent(
        modifier = modifier,
        top250MoviesState = top250MoviesState,
        mostPopularMoviesState = mostPopularMoviesState,
        genresState = moviesGenresState,
        typesState = movieTypesState,
        onFavoriteClick = { item ->
            viewModel.setMovieAsFavorite(item)
        },
    ) { item ->
        onMovieClicked(item.id)
    }
}

@Composable
fun MoviesScreenContent(
    modifier: Modifier = Modifier,
    top250MoviesState: UiState<List<MovieModel>>,
    mostPopularMoviesState: UiState<List<MovieModel>>,
    genresState: UiState<List<String>>,
    typesState: UiState<List<String>>,
    onFavoriteClick: (MovieModel) -> Unit,
    onClick: (MovieModel) -> Unit,
) {
    Column(
        modifier = modifier.verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        // top250Movies
        when (top250MoviesState) {
            is UiState.Error -> {}
            UiState.Idle -> {}
            UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    NiaLoadingWheel(contentDesc = "Loading Top 250 Movies")
                }
            }

            is UiState.Success -> {
                MoviesTitledLazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    items = top250MoviesState.data.distinctBy { it.id },
                    title = "Top 250 Movies",
                    key = { it.id },
                ) { item ->

                    ImdbMovieCard(
                        data = item,
                        onClick = onClick,
                        onFavoriteClick = {
                            onFavoriteClick(item.copy(favorite = item.favorite.not()))
                        },
                    )
                }
            }
        }

        // genres row
        when (genresState) {
            is UiState.Error -> {}
            UiState.Idle -> {}
            UiState.Loading -> {}

            is UiState.Success -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = genresState.data.distinct(),
                        key = { it },
                    ) { genre ->
                        ImdbFilterChip(label = genre) {
                            // onClick actions
                        }
                    }
                }
            }
        }

        when (typesState) {
            is UiState.Error -> {}

            UiState.Idle -> {}

            UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    NiaLoadingWheel(contentDesc = "Loading General Info")
                }
            }

            is UiState.Success -> {
                // types row
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        items = typesState.data.distinct(),
                        key = { it },
                    ) { type ->
                        ImdbFilterChip(label = type) {
                            // onClick actions
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (mostPopularMoviesState) {
            is UiState.Error -> {}
            UiState.Idle -> {}
            UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    NiaLoadingWheel(contentDesc = "Loading Top 250 Movies")
                }
            }

            is UiState.Success -> {
                MoviesTitledLazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    items = mostPopularMoviesState.data.distinctBy { it.id },
                    title = "Most Popular Movies",
                    key = { it.id },
                ) { item ->
                    var favorite by rememberSaveable { mutableStateOf(item.favorite) }

                    ImdbMovieCard(
                        data = item,
                        onClick = onClick,
                        onFavoriteClick = {
                            favorite = favorite.not()
                            onFavoriteClick(item.copy(favorite = favorite))
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Stable
class MoviesScreenState(visible: Boolean = true)

@Composable
fun rememberMovieScreenState(): MoviesScreenState = remember { MoviesScreenState() }

@Composable
@Preview(showBackground = true)
fun MoviesScreenPreview() {
    MoviesScreenContent(
        top250MoviesState = UiState.Success(
            listOf(
                Movie.DEFAULT1.toModel(),
                Movie.DEFAULT2.toModel(),
                Movie.DEFAULT3.toModel(),
            ),
        ),
        mostPopularMoviesState = UiState.Success(
            listOf(
                Movie.DEFAULT2.toModel(),
                Movie.DEFAULT1.toModel(),
                Movie.DEFAULT3.toModel(),
            ),
        ),
        genresState = UiState.Success(listOf("Drama", "Action", "Fantasy")),
        typesState = UiState.Success(listOf("Movie", "TVSeries")),
        onFavoriteClick = {},
        onClick = {},
    )
}


