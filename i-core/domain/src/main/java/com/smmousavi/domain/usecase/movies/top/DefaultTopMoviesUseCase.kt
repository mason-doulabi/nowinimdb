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

package com.smmousavi.domain.usecase.movies.top

import com.smmousavi.domain.repository.MoviesRepository
import com.smmousavi.i_core.model.movies.movie.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultTopMoviesUseCase @Inject constructor(
    val repository: MoviesRepository,
) : TopMoviesUseCase {

    override suspend fun top250Movies(): Flow<Result<List<MovieModel>>> {
        return repository.getTop250Movies()
    }

    override suspend fun mostPopularMovies(): Flow<Result<List<MovieModel>>> {
        return repository.getMostPopularMovies()
    }
}