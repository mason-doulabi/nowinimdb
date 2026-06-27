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

package com.smmousavi.i_core.data.datasource.movies.remote

import com.smmousavi.i_core.network.dto.movies.MovieCastDto
import com.smmousavi.i_core.network.dto.movies.MovieDto
import com.smmousavi.i_core.network.dto.movies.MoviePosterDto
import com.smmousavi.i_core.network.service.MoviesApiService
import javax.inject.Inject

class DefaultMoviesRemoteDataSource @Inject constructor(
    private val moviesApiService: MoviesApiService,
) : MoviesRemoteDataSource {

    override suspend fun fetchTop250Movies(): Result<List<MovieDto>> = runCatching {
        moviesApiService.fetchTop250Movies()
    }

    override suspend fun fetchMostPopularMovies(): Result<List<MovieDto>> = runCatching {
        moviesApiService.fetchMostPopularMovies()
    }

    override suspend fun fetchMovieDetailsById(id: String): Result<MovieDto> = runCatching {
        moviesApiService.fetchMovieDetailsById(id)
    }

    override suspend fun fetchMovieCastsById(id: String): Result<List<MovieCastDto>> = runCatching {
        moviesApiService.fetchMovieCastsById(id)
    }

    override suspend fun fetchMoviePosterById(id: String): Result<MoviePosterDto> = runCatching {
        moviesApiService.fetchMoviePosterById(id)
    }
}