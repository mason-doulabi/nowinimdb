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

package com.smmousavi.domain.usecase.movies.details

import com.smmousavi.domain.repository.MoviesRepository
import com.smmousavi.i_core.model.movies.movie.MovieCast
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.model.movies.movie.MoviePoster
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultMovieDetailsUseCase @Inject constructor(private val repository: MoviesRepository) :
    MovieDetailsUseCase {
    override suspend fun getMovieDetailsById(id: String): Flow<Result<MovieModel>> {
        return repository.getMovieDetailsById(id)
    }

    override suspend fun getMovieCastsById(id: String): Flow<Result<List<MovieCast>>> {
        return repository.getMovieCastsById(id)
    }

    override suspend fun getMoviePosterById(id: String): Flow<Result<MoviePoster>> {
        return repository.getMoviePosterById(id)
    }
}