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

package com.smmousavi.i_core.network.service

import com.smmousavi.i_core.network.dto.movies.MovieCastDto
import com.smmousavi.i_core.network.dto.movies.MovieDto
import com.smmousavi.i_core.network.dto.movies.MoviePosterDto
import kotlinx.serialization.InternalSerializationApi
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApiService {

    @GET("top250-movies")
    @OptIn(InternalSerializationApi::class)
    suspend fun fetchTop250Movies(): List<MovieDto>

    @GET("most-popular-movies")
    @OptIn(InternalSerializationApi::class)
    suspend fun fetchMostPopularMovies(): List<MovieDto>

    @GET("{movieId}")
    @OptIn(InternalSerializationApi::class)
    suspend fun fetchMovieDetailsById(
        @Path("movieId") id: String,
    ): MovieDto

    @GET("{movieId}/cast")
    @OptIn(InternalSerializationApi::class)
    suspend fun fetchMovieCastsById(
        @Path("movieId") id: String,
    ): List<MovieCastDto>

    @GET("{movieId}/poster")
    @OptIn(InternalSerializationApi::class)
    suspend fun fetchMoviePosterById(
        @Path("movieId") id: String,
    ): MoviePosterDto
}