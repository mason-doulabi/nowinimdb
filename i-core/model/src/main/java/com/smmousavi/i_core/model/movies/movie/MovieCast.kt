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
import kotlin.String

@Immutable
data class MovieCast(
    val id: String,
    val fullName: String,
    val primaryImage: String,
    val job: String,
    val url: String,
    val characters: List<String>,
    val thumbnails: List<MovieThumbnail>,
) {
    companion object {
        val DEFAULT1 = MovieCast(
            id = "nm0000190",
            fullName = "Matthew McConaughey",
            primaryImage = "https://m.media-amazon.com/images/M/MV5BMTg0MDc3ODUwOV5BMl5BanBnXkFtZTcwMTk2NjY4Nw@@.jpg",
            job = "actor",
            url = "https://www.imdb.com/name/nm0000190/",
            characters = listOf("Cooper"),
            thumbnails = listOf(
                MovieThumbnail(
                    url = "https://m.media-amazon.com/images/M/MV5BMTg0MDc3ODUwOV5BMl5BanBnXkFtZTcwMTk2NjY4Nw@@._V1_QL75_UX140_CR0,20,140,140_.jpg",
                    width = 140,
                    height = 140,
                ),
            ),
        )
    }
}