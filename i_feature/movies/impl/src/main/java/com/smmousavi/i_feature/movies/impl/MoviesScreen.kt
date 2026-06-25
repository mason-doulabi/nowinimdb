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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.smmousavi.i_core.designsystem.components.movie.ImdbFilterChip
import com.smmousavi.i_core.designsystem.components.movie.ImdbMovieCard
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.generalinfo.MoviesGeneralInfoModel
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.designsystem.layouts.movie.MoviesTitledLazyRow

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    viewModel: MoviesScreenViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getTop250Movies()
        viewModel.getGeneralInfo()
        viewModel.getFavoriteMovies()
    }

    val top250MoviesState by viewModel.top250MoviesState.collectAsStateWithLifecycle()
    val generalInfoState by viewModel.generalInfoUiState.collectAsStateWithLifecycle()
    val favoriteMovies by viewModel.favoriteMoviesState.collectAsStateWithLifecycle()

    MoviesScreenContent(
        modifier = modifier,
        top250MoviesState = top250MoviesState,
        generalInfoState = generalInfoState,
        favoriteMovies = favoriteMovies,
        onFavoriteClick = { item ->
            viewModel.setFavoriteMovie(item)
        },
    ) { item ->
        // onClick of the movie item
    }
}

@Composable
fun MoviesScreenContent(
    modifier: Modifier = Modifier,
    top250MoviesState: UiState<List<MovieItemModel>>,
    generalInfoState: UiState<MoviesGeneralInfoModel>,
    favoriteMovies: List<MovieItemModel>,
    onFavoriteClick: (MovieItemModel) -> Unit,
    onClick: (MovieItemModel) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // top250Movies
        when (top250MoviesState) {
            is UiState.Error -> {
                // display the error page
            }

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
                if (top250MoviesState.data.isNotEmpty()) {
                    MoviesTitledLazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        items = top250MoviesState.data,
                        title = "Top 250 Movies",
                    ) { item ->
                        var favorite by rememberSaveable { mutableStateOf(item.favorite) }

                        ImdbMovieCard(
                            data = item,
                            onClick = onClick,
                            favorite = favorite,
                            onFavoriteClick = {
                                favorite = favorite.not()
                                onFavoriteClick(item.copy(favorite = favorite))
                            },
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // genres row
        when (generalInfoState) {
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
                generalInfoState.data.genres?.let { genres ->
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(
                            items = genres,
                            key = { it },
                        ) { genre ->
                            ImdbFilterChip(label = genre) {
                                // onClick actions
                            }
                        }
                    }
                }

                // types row
                generalInfoState.data.types?.let { types ->
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(
                            items = types,
                            key = { it },
                        ) { type ->
                            ImdbFilterChip(label = type) {
                                // onClick actions
                            }
                        }
                    }
                }

                // languages row
                generalInfoState.data.languages?.let { languages ->
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(
                            items = languages,
                            key = { it.label },
                        ) { language ->
                            ImdbFilterChip(label = language.name) {
                                // onClick actions
                            }
                        }
                    }
                }

                generalInfoState.data.countries?.let { countries ->
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(
                            items = countries,
                            key = { it.label },
                        ) { country ->
                            ImdbFilterChip(label = country.name) {
                                // onClick actions
                            }
                        }
                    }
                }
            }
        }

        // favorite movies row
        if (favoriteMovies.isNotEmpty()) {
            MoviesTitledLazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                title = "Favorites Movies",
                items = favoriteMovies,
            ) { item ->
                var favorite by rememberSaveable { mutableStateOf(item.favorite) }
                ImdbMovieCard(
                    data = item,
                    onClick = onClick,
                    favorite = favorite,
                    onFavoriteClick = {
                        favorite = favorite.not()
                        onFavoriteClick(item.copy(favorite = favorite))
                    },
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    @Stable
    class MoviesScreenState(visible: Boolean = true)

    @Composable
    fun rememberMovieScreenState(): MoviesScreenState = remember { MoviesScreenState() }
}


