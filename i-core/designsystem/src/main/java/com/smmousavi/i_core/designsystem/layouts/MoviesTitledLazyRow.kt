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
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.MovieItem
import com.smmousavi.i_core.model.movies.mapper.MoviesModelMapper.toModel

@Composable
fun MoviesTitledLazyRow(
    modifier: Modifier = Modifier,
    title: String? = null,
    items: List<MovieItemModel>,
    rowItem: @Composable (MovieItemModel) -> Unit,
) {
    Column(modifier = modifier) {
        title?.let {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = it,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.tertiary,
            )
        }
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                items = items,
                key = { it.id },
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
                MovieItem.DEFAULT1.toModel(),
                MovieItem.DEFAULT2.toModel(),
                MovieItem.DEFAULT3.toModel(),
            ),
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
