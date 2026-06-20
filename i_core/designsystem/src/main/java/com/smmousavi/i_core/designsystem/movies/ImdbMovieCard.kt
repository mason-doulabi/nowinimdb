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

package com.smmousavi.i_core.designsystem.movies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.MovieItem
import com.smmousavi.i_core.model.movies.mapper.MoviesModelMapper.toModel

@Composable
fun ImdbMovieCard(
    modifier: Modifier = Modifier,
    item: MovieItemModel,
    favorite: Boolean,
    onFavoriteClick: (MovieItemModel) -> Unit,
    onClick: (MovieItemModel) -> Unit,
) {
    Card(
        modifier = modifier
            .width(140.dp)
            .height(260.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick(item) },
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
            ) {
                AsyncImage(
                    model = item.thumbnailUrl,
                    contentDescription = item.description,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
                IconButton(
                    modifier = Modifier.align(Alignment.BottomStart),
                    onClick = { onFavoriteClick(item) },
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.tertiary,
                        imageVector = if (favorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Filled.FavoriteBorder
                        },
                        contentDescription = null,
                    )
                }
            }

            Row(
                modifier = Modifier.padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary,
                )

                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = item.rating.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            Text(
                modifier = Modifier.padding(4.dp),
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ImdbMovieCardPreview() {
    ImdbMovieCard(
        item = MovieItem.DEFAULT1.toModel(),
        favorite = false,
        onClick = {},
        onFavoriteClick = {},
    )
}
