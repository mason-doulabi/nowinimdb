package com.smmousavi.i_core.data.repository

import com.smmousavi.domain.repository.MoviesRepository
import com.smmousavi.i_core.data.datasource.movies.MoviesRemoteDataSource
import com.smmousavi.i_core.data.mapper.MoviesMapper.toDomain
import com.smmousavi.i_core.model.domain.CountriesModel
import com.smmousavi.i_core.model.domain.GenresModel
import com.smmousavi.i_core.model.domain.LanguagesModel
import com.smmousavi.i_core.model.domain.TypesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultMoviesRepository @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
) : MoviesRepository {

    override suspend fun getTypes(): Flow<Result<TypesModel>> = flow {
        moviesRemoteDataSource.getTypes().fold(
            onSuccess = { types -> Result.success(types.toDomain()) },
            onFailure = { error -> Result.failure<Exception>(error) },
        )
    }

    override suspend fun getGenres(): Flow<Result<GenresModel>> = flow {
        moviesRemoteDataSource.getGenres().fold(
            onSuccess = { genres -> Result.success(genres.toDomain()) },
            onFailure = { error -> Result.failure<Exception>(error) },
        )
    }

    override suspend fun getCountries(): Flow<Result<CountriesModel>> = flow {
        moviesRemoteDataSource.getCountries().fold(
            onSuccess = { countries -> Result.success(countries.toDomain()) },
            onFailure = { error -> Result.failure<Exception>(error) },
        )
    }

    override suspend fun getLanguages(): Flow<Result<LanguagesModel>> = flow {
        moviesRemoteDataSource.getLanguages().fold(
            onSuccess = { languages -> Result.success(languages.toDomain()) },
            onFailure = { error -> Result.failure<Exception>(error) },
        )
    }
}