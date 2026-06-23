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

package com.smmousavi.i_core.data.repository

import android.util.Log
import com.smmousavi.domain.repository.SearchMovieRepository
import com.smmousavi.i_core.data.datasource.search.SearchMovieRemoteDataSource
import com.smmousavi.i_core.data.mapper.dto.MoviesDtoMapper.toDomain
import com.smmousavi.i_core.model.movies.MovieItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject



class DefaultSearchMoviesRepository @Inject constructor(
    private val remoteDataSource: SearchMovieRemoteDataSource,
) : SearchMovieRepository {

    override fun searchMovie(query: String): Flow<Result<List<MovieItem>>> = flow {
        emit(
            remoteDataSource.searchMovie(query).fold(
                onSuccess = { data ->

                    Result.success(data.results.map { it.toDomain() })
                },
                onFailure = { error ->
                    Result.failure(error)
                },
            ),
        )
    }

    override fun autoComplete(query: String): Flow<Result<List<MovieItem>>> = flow {
        emit(
            remoteDataSource.autoComplete(query).fold(
                onSuccess = { data ->
                    Result.success(data.results.map { it.toDomain() })
                },
                onFailure = { error ->
                    Result.failure(error)
                },
            ),
        )
    }
}