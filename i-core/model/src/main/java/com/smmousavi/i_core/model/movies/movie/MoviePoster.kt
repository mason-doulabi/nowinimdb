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

package com.smmousavi.i_core.model.movies.movie

import androidx.compose.runtime.Immutable

@Immutable
data class MoviePoster(
    val id: String,
    val poster: String,
    val url: String,
    val thumbnails: List<MovieThumbnail>,
) {
    companion object {
        val DEFAULT = MoviePoster(
            id = "tt0816692",
            poster = "https://m.media-amazon.com/images/M/MV5BYzdjMDAxZGItMjI2My00ODA1LTlkNzItOWFjMDU5ZDJlYWY3XkEyXkFqcGc@.jpg",
            url = "https://www.imdb.com/title/tt0816692/",
            thumbnails = listOf(
                MovieThumbnail(
                    url = "https://m.media-amazon.com/images/M/MV5BYzdjMDAxZGItMjI2My00ODA1LTlkNzItOWFjMDU5ZDJlYWY3XkEyXkFqcGc@._V1_QL75_UX100_CR0,0,100,148_.jpg",
                    width = 100,
                    height = 148,
                ),
            ),
        )
    }
}