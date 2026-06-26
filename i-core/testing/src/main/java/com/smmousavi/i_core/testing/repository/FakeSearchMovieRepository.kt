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

package com.smmousavi.i_core.testing.repository

import com.smmousavi.domain.repository.SearchMovieRepository
import com.smmousavi.i_core.model.movies.MovieItem
import com.smmousavi.i_core.model.movies.MovieItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSearchMovieRepository : SearchMovieRepository {

    var shouldFail = false

    override fun searchMovie(query: String): Flow<Result<List<MovieItem>>> {
        return if (shouldFail) {
            flowOf(
                Result.failure(RuntimeException("Network Error")),
            )
        } else {
            flowOf(
                Result.success(
                    listOf(
                        MovieItem.DEFAULT1,
                        MovieItem.DEFAULT2,
                    ),
                ),
            )
        }
    }

    override fun autoComplete(query: String): Flow<Result<List<MovieItem>>> {
        return if (shouldFail) {
            flowOf(Result.failure(RuntimeException("Network Error")))
        } else {
            flowOf(
                Result.success(
                    listOf(
                        MovieItem.DEFAULT1,
                    ),
                ),
            )
        }
    }

    override suspend fun setMovieAsRecentlySearched(
        movie: MovieItemModel,
        recentlySearched: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override fun getRecentlySearchedMovies(): Flow<List<MovieItemModel>> {
        TODO("Not yet implemented")
    }
}