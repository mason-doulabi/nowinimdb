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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smmousavi.i_core.designsystem.movies.ImdbFilterChip
import com.smmousavi.i_core.designsystem.movies.ImdbMovieCard
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.generalinfo.MoviesGeneralInfoModel
import com.smmousavi.i_core.presentation.movies.MoviesTitledLazyRow

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    top250Movies: List<MovieItemModel>,
    favoriteMovies: List<MovieItemModel>,
    generalInfo: MoviesGeneralInfoModel,
    onMovieClick: (MovieItemModel) -> Unit,
    onFavoriteClick: (MovieItemModel) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // top250Movies
        if (top250Movies.isNotEmpty()) {
            MoviesTitledLazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                items = top250Movies,
                title = "Top 250 Movies",
            ) { item ->
                var favorite by rememberSaveable { mutableStateOf(item.favorite) }
                ImdbMovieCard(
                    data = item,
                    onClick = onMovieClick,
                    favorite = favorite,
                    onFavoriteClick = {
                        favorite = favorite.not()
                        onFavoriteClick(item.copy(favorite = favorite))
                    },
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // genres row
        generalInfo.genres?.let { genres ->
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
        generalInfo.types?.let { types ->
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
        generalInfo.languages?.let { languages ->
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

        generalInfo.countries?.let { countries ->
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
                    onClick = onMovieClick,
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

