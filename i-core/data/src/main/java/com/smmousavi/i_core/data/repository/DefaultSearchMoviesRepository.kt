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

import com.smmousavi.domain.repository.SearchMovieRepository
import com.smmousavi.i_core.data.datasource.movies.local.MoviesLocalDataSource
import com.smmousavi.i_core.data.datasource.search.local.SearchMoviesLocalDataSource
import com.smmousavi.i_core.data.datasource.search.remote.SearchMoviesRemoteDataSource
import com.smmousavi.i_core.data.mapper.dto.MovieDtoMapper.toDomain
import com.smmousavi.i_core.data.mapper.entity.MoviesEntityMapper.toEntity
import com.smmousavi.i_core.data.mapper.entity.MoviesEntityMapper.toModel
import com.smmousavi.i_core.model.movies.mapper.MovieModelMapper.toModel
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.movie.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultSearchMoviesRepository @Inject constructor(
    private val remoteDataSource: SearchMoviesRemoteDataSource,
    private val localDataSource: SearchMoviesLocalDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource,
) : SearchMovieRepository {

    override fun fetchSearchMovieResult(query: String): Flow<Result<List<Movie>>> = flow {
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

    override fun getSearchMovieResult(query: String): Flow<Result<List<MovieModel>>> = combine(
        fetchSearchMovieResult(query),
        moviesLocalDataSource.getFavoriteMovies(),
    ) { searchResult, favorites ->
        val favoritesId = favorites.map { it.id }.toHashSet()
        searchResult.fold(
            onSuccess = { data -> Result.success(data.map { it.toModel(favorite = it.id in favoritesId) }) },
            onFailure = { e -> Result.failure(e) },
        )
    }

    override fun fetchAutoCompleteResult(query: String): Flow<Result<List<Movie>>> = flow {
        emit(
            remoteDataSource.autoComplete(query).fold(
                onSuccess = { data ->
                    Result.success(data.map { it.toDomain() })
                },
                onFailure = { error ->
                    Result.failure(error)
                },
            ),
        )
    }

    override fun getAutoCompleteResult(query: String): Flow<Result<List<MovieModel>>> = combine(
        fetchAutoCompleteResult(query),
        moviesLocalDataSource.getFavoriteMovies(),
    ) { autoCompleteResult, favorites ->
        val favoritesId = favorites.map { it.id }.toHashSet()
        autoCompleteResult.fold(
            onSuccess = { data ->
                Result.success(
                    data.map { movie ->
                        movie.toModel(favorite = movie.id in favoritesId)
                    }.take(5),
                )
            },
            onFailure = { e -> Result.failure(e) },
        )
    }

    override suspend fun setMovieAsRecentlySearched(
        movie: MovieModel,
        recentlySearched: Boolean,
    ) {
        localDataSource.upsertMovieAsRecentlySearched(
            movie.copy(
                recentlySearched = recentlySearched,
                searchedTime = System.currentTimeMillis(),
            ).toEntity(),
        )
    }

    override fun getRecentlySearchedMovies(): Flow<List<MovieModel>> =
        localDataSource.getRecentlySearchMovies().map { movies ->
            movies.map { it.toModel() }
        }
}