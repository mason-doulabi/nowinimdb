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

package com.smmousavi.i_core.testing.usecase

import com.smmousavi.domain.usecase.search.SearchMovieUseCase
import com.smmousavi.i_core.model.movies.movie.Movie
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeSearchMovieUseCase : SearchMovieUseCase {

    var callCount = 0
    var lastQuery = ""
    var result: Result<List<Movie>> =
        Result.success(
            listOf(Movie.DEFAULT1),
        )

    override fun searchMovie(query: String): Flow<Result<List<Movie>>> = flow {
        callCount++
        lastQuery = query

        delay(100)
        emit(result)
    }

    override fun autoComplete(query: String): Flow<Result<List<Movie>>> {
        return flowOf(result)
    }
}