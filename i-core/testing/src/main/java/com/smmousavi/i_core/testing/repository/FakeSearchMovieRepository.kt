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
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.movie.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeSearchMovieRepository : SearchMovieRepository {

    var shouldFail = false

    override fun fetchSearchMovieResult(query: String): Flow<Result<List<Movie>>> {
        return if (shouldFail) {
            flowOf(
                Result.failure(RuntimeException("Network Error")),
            )
        } else {
            flowOf(
                Result.success(
                    listOf(
                        Movie.DEFAULT1,
                        Movie.DEFAULT2,
                    ),
                ),
            )
        }
    }

    override fun getSearchMovieResult(query: String): Flow<Result<List<MovieModel>>> {
        TODO("Not yet implemented")
    }

    override fun fetchAutoCompleteResult(query: String): Flow<Result<List<Movie>>> {
        return if (shouldFail) {
            flowOf(Result.failure(RuntimeException("Network Error")))
        } else {
            flowOf(
                Result.success(
                    listOf(
                        Movie.DEFAULT1,
                    ),
                ),
            )
        }
    }

    override fun getAutoCompleteResult(query: String): Flow<Result<List<MovieModel>>> {
        TODO("Not yet implemented")
    }

    override suspend fun setMovieAsRecentlySearched(
        movie: MovieModel,
        recentlySearched: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override fun getRecentlySearchedMovies(): Flow<List<MovieModel>> {
        TODO("Not yet implemented")
    }
}