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

package com.smmousavi.i_core.data.datasource.search.remote

import com.smmousavi.i_core.network.dto.movies.MovieItemDto
import com.smmousavi.i_core.network.dto.movies.SearchMovieItemDto
import com.smmousavi.i_core.network.service.SearchApiService
import javax.inject.Inject

class DefaultSearchMoviesRemoteDataSource @Inject constructor(
    private val apiService: SearchApiService,
) : SearchMoviesRemoteDataSource {

    override suspend fun searchMovie(query: String): Result<SearchMovieItemDto> = runCatching {
        apiService.searchMovie(query)
    }

    override suspend fun autoComplete(query: String): Result<List<MovieItemDto>> = runCatching {
        apiService.autoComplete(query)
    }
}