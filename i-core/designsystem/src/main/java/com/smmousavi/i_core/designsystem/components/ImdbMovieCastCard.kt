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

package com.smmousavi.i_core.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.mapper.MovieModelMapper.toModel
import com.smmousavi.i_core.model.movies.movie.MovieCast

@Composable
fun ImdbMovieCastCard(
    modifier: Modifier = Modifier,
    data: MovieCast,
    onClick: (MovieCast) -> Unit,
) {
    Card(
        modifier = modifier
            .width(140.dp)
            .aspectRatio(1f / 1.8f),
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick(data) },
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.primaryImage)
                        .crossfade(true)
                        .memoryCacheKey(data.id)
                        .diskCacheKey(data.id)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }

            Text(
                modifier = Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 4.dp,
                    ),
                text = data.fullName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ImdbMovieCastCardPreview() {
    ImdbMovieCastCard(
        data = MovieCast.DEFAULT1,
        onClick = {},
    )
}
