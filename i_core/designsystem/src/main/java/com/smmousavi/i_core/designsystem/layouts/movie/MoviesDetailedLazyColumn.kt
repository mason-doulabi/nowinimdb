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

package com.smmousavi.i_core.designsystem.layouts.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smmousavi.i_core.designsystem.components.movie.ImdbMovieRow
import com.smmousavi.i_core.model.movies.MovieItem
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.mapper.MoviesModelMapper.toModel

@Composable
fun MoviesDetailedLazyColumn(
    modifier: Modifier = Modifier,
    items: List<MovieItemModel>,
    columnItem: @Composable (MovieItemModel) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(
            items = items,
            key = { it.id },
        ) { item ->
            columnItem(item)
        }
    }
}

@Composable
@Preview
fun MoviesDetailedLazyColumnPreview() {
    MoviesDetailedLazyColumn(
        items = listOf(
            MovieItem.DEFAULT1.toModel(),
            MovieItem.DEFAULT2.toModel(),
            MovieItem.DEFAULT3.toModel(),
        ),
    ) { item ->
        ImdbMovieRow(data = item) { }
    }
}
