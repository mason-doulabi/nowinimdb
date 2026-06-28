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

package com.smmousavi.i_feature.profile.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smmousavi.i_core.designsystem.components.ImdbMovieCard
import com.smmousavi.i_core.designsystem.components.ImdbRowButton
import com.smmousavi.i_core.designsystem.components.ImdbRowButtonType
import com.smmousavi.i_core.designsystem.components.ImdbTitleChip
import com.smmousavi.i_core.designsystem.layouts.MoviesTitledLazyRow
import com.smmousavi.i_core.model.movies.mapper.MovieModelMapper.toModel
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.movie.MovieModel

@Composable
fun ProfilScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
    onMovieClick: (String) -> Unit,
) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    ProfileScreenContent(
        modifier = modifier,
        favorites = favorites,
        onFavoriteClick = { item -> viewModel.setMovieAsFavorite(item) },
    ) { movieId ->
        onMovieClick(movieId)
    }
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    favorites: List<MovieModel>,
    onFavoriteClick: (MovieModel) -> Unit,
    onMovieClick: (String) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp),
        ) {
            Image(
                modifier = Modifier.size(81.dp),
                imageVector = Icons.Default.PersonPin,
                contentDescription = null,
            )
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.titleLarge,
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                )
            }
        }
        ImdbRowButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            onClick = {},
            type = ImdbRowButtonType.Primary,
        ) {
            Text(
                text = "Sign in / Sign up",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            ImdbTitleChip(
                title = "Ratings",
                onClick = {},
            ) {
                Text(text = "0", style = MaterialTheme.typography.bodyMedium)
            }

            ImdbTitleChip(
                title = "WatchList",
                onClick = {},
            ) {
                Text(text = "0", style = MaterialTheme.typography.bodyMedium)
            }

            ImdbTitleChip(
                title = "Favorites",
                onClick = {},
            ) {
                Text(text = "0", style = MaterialTheme.typography.bodyMedium)
            }

            ImdbTitleChip(
                title = "More",
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreHoriz,
                    contentDescription = "More",
                )
            }
        }

        MoviesTitledLazyRow(
            title = "Favorite Movies",
            items = favorites,
            key = { item -> item.id },
        ) { item ->
            ImdbMovieCard(
                data = item,
                onFavoriteClick = { item ->
                    onFavoriteClick(item.copy(favorite = item.favorite.not()))
                },
            ) { item ->
                onMovieClick(item.id)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    ProfileScreenContent(
        favorites = listOf(
            Movie.DEFAULT1.toModel(favorite = true),
            Movie.DEFAULT2.toModel(favorite = true),
            Movie.DEFAULT3.toModel(favorite = true),
        ),
        onFavoriteClick = {},
    ) {}
}

