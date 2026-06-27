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

package com.smmousavi.i_core.designsystem.layouts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.smmousavi.i_core.designsystem.components.ImdbMovieCard
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.mapper.MovieModelMapper.toModel

@Composable
fun <T> MoviesTitledLazyRow(
    modifier: Modifier = Modifier,
    title: String? = null,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    items: List<T>,
    key: (T) -> Any,
    rowItem: @Composable (T) -> Unit,
) {
    Column(modifier = modifier) {
        title?.let {
            Text(
                modifier = Modifier.padding(contentPadding),
                text = it,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
        LazyRow(
            contentPadding = contentPadding,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = items,
                key = { item -> key(item) },
            ) { item ->
                rowItem(item)
            }
        }
    }
}

@Composable
@Preview
fun MoviesTitledRowPreview() {
    NiaTheme {
        MoviesTitledLazyRow(
            title = "Top 250 Movies",
            items = listOf(
                Movie.DEFAULT1.toModel(),
                Movie.DEFAULT2.toModel(),
                Movie.DEFAULT3.toModel(),
            ),
            key = { it.id },
        ) { item ->
            ImdbMovieCard(
                data = item,
                favorite = false,
                onClick = {},
                onFavoriteClick = {},
            )
        }
    }
}
