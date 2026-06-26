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

package com.smmousavi.i_core.designsystem.components.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smmousavi.i_core.model.movies.MovieItem
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.mapper.MoviesModelMapper.toModel

@Composable
fun ImdbDetailsRow(
    modifier: Modifier = Modifier,
    data: MovieItemModel,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        data.thumbnailsUrl.let {
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
                contentDescription = null,
                modifier = Modifier
                    .width(64.dp)
                    .aspectRatio(1f / 1.5f),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
            ) {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = data.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                Spacer(
                    modifier = Modifier.height(4.dp),
                )
                Text(
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    text = data.description,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.tertiary,
            )

            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = data.rating.toString(),
                style = MaterialTheme.typography.titleSmall,
            )

            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Composable
@Preview
fun ImdbDetailsRowPreview() {
    ImdbDetailsRow(
        data = MovieItem.DEFAULT1.toModel(),
    ) {}
}
