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

package com.smmousavi.i_feature.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.smmousavi.i_core.designsystem.components.ImdbFilterChip
import com.smmousavi.i_core.designsystem.components.ImdbMovieCastCard
import com.smmousavi.i_core.designsystem.components.ImdbRowButton
import com.smmousavi.i_core.designsystem.components.ImdbRowButtonType
import com.smmousavi.i_core.designsystem.layouts.MoviesTitledLazyRow
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.model.movies.mapper.MovieModelMapper.toModel
import com.smmousavi.i_core.model.movies.movie.MovieCast
import com.smmousavi.i_core.model.movies.movie.MoviePoster
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.presentation.utils.priceSeparated

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movieId: String,
    viewModel: MovieDetailsScreenViewModel = hiltViewModel(),
) {
    val movieDetailsState by viewModel.movieDetailsState.collectAsStateWithLifecycle()
    val moviePosterState by viewModel.moviePosterState.collectAsStateWithLifecycle()
    val movieCastState by viewModel.movieCastsState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.observeMovieDetails(movieId)
        viewModel.observeMovieCasts(movieId)
        viewModel.observeMoviePoster(movieId)
    }

    MovieDetailsScreenContent(
        modifier = modifier,
        detailsState = movieDetailsState,
        posterState = moviePosterState,
        castsState = movieCastState,
    )
}

@Composable
fun MovieDetailsScreenContent(
    modifier: Modifier = Modifier,
    detailsState: UiState<MovieModel>,
    posterState: UiState<MoviePoster>,
    castsState: UiState<List<MovieCast>>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        // Movie Title
        when (detailsState) {
            is UiState.Error -> {}
            UiState.Idle -> {}
            UiState.Loading -> {}
            is UiState.Success -> {
                MovieHeaderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    title = detailsState.data.title,
                    year = detailsState.data.year,
                    type = detailsState.data.type,
                    durationMins = detailsState.data.durationMins,
                )
                Spacer(Modifier.height(16.dp))
            }
        }

        // Movie poster
        when (posterState) {
            is UiState.Error -> {}
            UiState.Idle -> {}
            UiState.Loading -> {}
            is UiState.Success -> {
                MoviePosterSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    posterUrl = posterState.data.poster,
                    posterId = posterState.data.id,
                )
            }
        }

        // Movie Details
        when (detailsState) {
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
                MovieDetailsSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    data = detailsState.data,
                )
            }
        }

        // Movie Casts
        when (castsState) {
            is UiState.Error -> {}
            UiState.Idle -> {}
            UiState.Loading -> {}
            is UiState.Success -> {
                MoviesTitledLazyRow(
                    title = "Casts",
                    items = castsState.data.distinctBy { it.id },
                    key = { it.id },
                ) { item ->
                    ImdbMovieCastCard(
                        data = item,
                    ) { }
                }
            }
        }
    }
}

@Composable
fun MovieHeaderSection(
    modifier: Modifier = Modifier,
    title: String,
    year: Int,
    type: String,
    durationMins: Int,
) {
    // Movie Title Text
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.tertiary,
        )

        // Movie General Info Text
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
            ),
        ) {
            Text(text = year.toString(), style = MaterialTheme.typography.bodySmall)
            Text(text = type.uppercase(), style = MaterialTheme.typography.bodySmall)
            Text(text = "${durationMins}m", style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun MoviePosterSection(
    modifier: Modifier = Modifier,
    posterUrl: String,
    posterId: String,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterUrl)
            .crossfade(true)
            .memoryCacheKey(posterId)
            .diskCacheKey(posterId)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun MovieDetailsSection(
    modifier: Modifier = Modifier,
    data: MovieModel,
) {
    // Movie description Row
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .width(64.dp)
                .aspectRatio(1f / 1.7f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    data.thumbnails.let {
                        if (it.isNotEmpty()) {
                            it[0]
                        } else {
                            data.imageUrl
                        }
                    },
                )
                .crossfade(true)
                .memoryCacheKey(data.id)
                .diskCacheKey(data.id)
                .build(),
            contentDescription = data.description,
            contentScale = ContentScale.Crop,
        )
        Text(text = data.description, modifier = Modifier.padding(vertical = 8.dp))
    }

    // Movie genres row
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(
            items = data.interests.distinct(),
            key = { it },
        ) { interest ->
            ImdbFilterChip(label = interest) { }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Add To favorite Button
    ImdbRowButton(
        modifier = Modifier.padding(horizontal = 16.dp),
        type = ImdbRowButtonType.Primary,
        onClick = {},
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp),
            imageVector = if (data.favorite) {
                Icons.Default.Check
            } else {
                Icons.Default.Add
            },
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = if (data.favorite) {
                "Added to Favorites"
            } else {
                "Add to Favorites"
            },
        )
    }

    // Add to Watch Later Button
    ImdbRowButton(
        modifier = Modifier.padding(horizontal = 16.dp),
        type = ImdbRowButtonType.Secondary,
        onClick = {},
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp),
            imageVector = if (data.watchLater) {
                Icons.Default.Check
            } else {
                Icons.Default.RemoveRedEye
            },
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = if (data.watchLater) {
                "Added to Watch Later"
            } else {
                "Add to Watch Later"
            },
        )
    }

    // Preferences Service Button Row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 16.dp)
            .semantics { role = Role.Button }
            .clickable {},
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = null,
        )
        Text(text = "SET YOUR PREFERENCES SERVICES", style = MaterialTheme.typography.bodyLarge)
    }

    // Movie Ratings Row
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Default.Star,
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = null,
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${data.rating}", style = MaterialTheme.typography.titleMedium)
                Text(" / 10", style = MaterialTheme.typography.bodySmall)
            }

            Text(data.votes.priceSeparated(), style = MaterialTheme.typography.bodySmall)
        }

        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Default.StarBorder,
                tint = MaterialTheme.colorScheme.outline,
                contentDescription = null,
            )

            Text(
                "Rate This",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
            )
        }

        Text(text = "Cretic Review", style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
@Preview(showBackground = true)
fun MovieDetailsScreenContentPreview() {
    NiaTheme {
        MovieDetailsScreenContent(
            detailsState = UiState.Success(
                Movie.DEFAULT1.toModel(
                    favorite = true,
                    watchLater = false,
                ),
            ),
            posterState = UiState.Success(MoviePoster.DEFAULT),
            castsState = UiState.Success(listOf(MovieCast.DEFAULT1)),
        )
    }
}
