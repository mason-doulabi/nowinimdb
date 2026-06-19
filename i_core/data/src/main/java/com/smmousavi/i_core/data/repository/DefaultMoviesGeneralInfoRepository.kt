package com.smmousavi.i_core.data.repository

import com.smmousavi.domain.repository.MoviesGeneralInfoRepository
import com.smmousavi.i_core.data.datasource.generalinfo.MoviesGeneralInfoDataSource
import com.smmousavi.i_core.data.mapper.MoviesMapper.toDomain
import com.smmousavi.i_core.model.movies.generalinfo.MoviesCountryItemModel
import com.smmousavi.i_core.model.movies.generalinfo.MoviesLanguageItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultMoviesGeneralInfoRepository @Inject constructor(
    private val moviesGeneralInfoDataSource: MoviesGeneralInfoDataSource,
) : MoviesGeneralInfoRepository {

    override suspend fun getTypes(): Flow<Result<List<String>>> = flow {
        emit(
            moviesGeneralInfoDataSource.getTypes().fold(
                onSuccess = { Result.success(it) },
                onFailure = { Result.failure(it) },
            ),
        )
    }

    override suspend fun getGenres(): Flow<Result<List<String>>> = flow {
        emit(
            moviesGeneralInfoDataSource.getGenres().fold(
                onSuccess = { Result.success(it) },
                onFailure = { Result.failure(it) },
            ),
        )
    }

    override suspend fun getCountries(): Flow<Result<List<MoviesCountryItemModel>>> = flow {
        emit(
            moviesGeneralInfoDataSource.getCountries().fold(
                onSuccess = { Result.success(it.map { country -> country.toDomain() }) },
                onFailure = { Result.failure(it) },
            ),
        )
    }

    override suspend fun getLanguages(): Flow<Result<List<MoviesLanguageItemModel>>> = flow {
        emit(
            moviesGeneralInfoDataSource.getLanguages().fold(
                onSuccess = { Result.success(it.map { language -> language.toDomain() }) },
                onFailure = { Result.failure(it) },
            ),
        )
    }
}