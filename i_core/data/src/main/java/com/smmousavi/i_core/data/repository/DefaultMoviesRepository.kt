package com.smmousavi.i_core.data.repository

import com.smmousavi.domain.repository.MoviesRepository
import com.smmousavi.i_core.data.datasource.movies.MoviesRemoteDataSource
import com.smmousavi.i_core.data.mapper.MoviesMapper.toDomain
import com.smmousavi.i_core.model.movies.MoviesCountryItemModel
import com.smmousavi.i_core.model.movies.MoviesLanguageItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultMoviesRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
) : MoviesRepository {

    override suspend fun getTypes(): Flow<Result<List<String>>> = flow {
        emit(
            moviesRemoteDataSource.getTypes().fold(
                onSuccess = { Result.success(it) },
                onFailure = { Result.failure(it) },
            ),
        )
    }

    override suspend fun getGenres(): Flow<Result<List<String>>> = flow {
        emit(
            moviesRemoteDataSource.getGenres().fold(
                onSuccess = { Result.success(it) },
                onFailure = { Result.failure(it) },
            ),
        )
    }

    override suspend fun getCountries(): Flow<Result<List<MoviesCountryItemModel>>> = flow {
        emit(
            moviesRemoteDataSource.getCountries().fold(
                onSuccess = { Result.success(it.map { country -> country.toDomain() }) },
                onFailure = { Result.failure(it) },
            ),
        )
    }

    override suspend fun getLanguages(): Flow<Result<List<MoviesLanguageItemModel>>> = flow {
        emit(
            moviesRemoteDataSource.getLanguages().fold(
                onSuccess = { Result.success(it.map { language -> language.toDomain() }) },
                onFailure = { Result.failure(it) },
            ),
        )
    }
}