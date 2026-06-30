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

import com.smmousavi.domain.repository.MoviesRepository
import com.smmousavi.i_core.data.datasource.movies.local.MoviesLocalDataSource
import com.smmousavi.i_core.data.datasource.movies.remote.MoviesRemoteDataSource
import com.smmousavi.i_core.data.mapper.dto.MovieDtoMapper.toDomain
import com.smmousavi.i_core.data.mapper.entity.MoviesEntityMapper.toEntity
import com.smmousavi.i_core.data.mapper.entity.MoviesEntityMapper.toModel
import com.smmousavi.i_core.data.mapper.ErrorMapper
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.model.movies.mapper.MovieModelMapper.toModel
import com.smmousavi.i_core.model.movies.movie.MovieCast
import com.smmousavi.i_core.model.movies.movie.MoviePoster
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultMoviesRepository @Inject constructor(
    val moviesRemoteDataSource: MoviesRemoteDataSource,
    val moviesLocalDataSource: MoviesLocalDataSource,
) : MoviesRepository {

    override fun fetchTop250Movies(): Flow<Result<List<Movie>>> = flow {
        emit(
            moviesRemoteDataSource.fetchTop250Movies().map { movies ->
                movies.map { it.toDomain() }
            },
        )
    }

    override fun fetchMostPopularMovies(): Flow<Result<List<Movie>>> = flow {
        emit(
            moviesRemoteDataSource.fetchMostPopularMovies().map { movies ->
                movies.map { it.toDomain() }
            },
        )
    }

    override fun getTop250Movies(): Flow<Result<List<MovieModel>>> = combine(
        fetchTop250Movies(),
        moviesLocalDataSource.getFavoriteMovies(),
    ) { moviesResult, favorites ->
        moviesResult.fold(
            onSuccess = { movies ->
                // reduce the search time complexity from O(N) to O(1)
                val favoritesIds = favorites.map { it.id }.toHashSet()
                Result.success(
                    movies.map { movie ->
                        // search the with O(N) in O(1) set
                        movie.toModel(movie.id in favoritesIds)
                    },
                )
            },
            onFailure = { e -> Result.failure(ErrorMapper.map(e)) },
        )
    }

    override fun getMostPopularMovies(): Flow<Result<List<MovieModel>>> = combine(
        fetchMostPopularMovies(),
        moviesLocalDataSource.getFavoriteMovies(),
    ) { popularMovies, favorites ->
        popularMovies.fold(
            onSuccess = { movies ->
                val favoriteIds = favorites.map { it.id }.toHashSet()
                Result.success(
                    movies.map { movie ->
                        movie.toModel(favorite = movie.id in favoriteIds)
                    },
                )
            },
            onFailure = { e -> Result.failure(ErrorMapper.map(e)) },
        )
    }

    override fun fetchMovieDetailsById(id: String): Flow<Result<Movie>> = flow {
        emit(
            moviesRemoteDataSource.fetchMovieDetailsById(id).map { it.toDomain() },
        )
    }

    override fun getMovieDetailsById(id: String): Flow<Result<MovieModel>> = combine(
        fetchMovieDetailsById(id),
        moviesLocalDataSource.getFavoriteMovies(),
    ) { movie, favorites ->
        val favoritesId = favorites.map { it.id }.toHashSet()
        movie.fold(
            onSuccess = { movie ->
                Result.success(movie.toModel(favorite = movie.id in favoritesId))
            },
            onFailure = { e -> Result.failure(ErrorMapper.map(e)) },
        )
    }

    override fun getMovieCastsById(id: String): Flow<Result<List<MovieCast>>> = flow {
        emit(
            moviesRemoteDataSource.fetchMovieCastsById(id).fold(
                onSuccess = { casts ->
                    Result.success(casts.map { it.toDomain() })
                },
                onFailure = { e -> Result.failure(ErrorMapper.map(e)) },
            ),
        )
    }

    override fun getMoviePosterById(id: String): Flow<Result<MoviePoster>> = flow {
        emit(
            moviesRemoteDataSource.fetchMoviePosterById(id).fold(
                onSuccess = { poster -> Result.success(poster.toDomain()) },
                onFailure = { e -> Result.failure(ErrorMapper.map(e)) },
            ),
        )
    }

    override suspend fun upsertMovie(movie: MovieModel) {
        moviesLocalDataSource.upsertMovie(movie.toEntity())
    }

    override fun getFavoriteMovies(): Flow<List<MovieModel>> =
        moviesLocalDataSource.getFavoriteMovies().map { movies ->
            movies.map { it.toModel() }
        }
}