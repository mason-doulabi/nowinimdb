package com.smmousavi.i_core.data.repository

import com.smmousavi.domain.repository.MoviesGeneralInfoRepository
import com.smmousavi.i_core.data.datasource.movies.remote.generalinfo.MoviesGeneralInfoDataSource
import com.smmousavi.i_core.data.mapper.dto.MovieDtoMapper.toDomain
import com.smmousavi.i_core.data.mapper.ErrorMapper
import com.smmousavi.i_core.model.movies.movie.generalinfo.MovieCountry
import com.smmousavi.i_core.model.movies.movie.generalinfo.MovieLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultMoviesGeneralInfoRepository @Inject constructor(
    private val moviesGeneralInfoDataSource: MoviesGeneralInfoDataSource,
) : MoviesGeneralInfoRepository {

    override fun getTypes(): Flow<Result<List<String>>> = flow {
        emit(
            moviesGeneralInfoDataSource.getTypes().fold(
                onSuccess = { data -> Result.success(data) },
                onFailure = { e -> Result.failure(ErrorMapper.map(e)) },
            ),
        )
    }

    override fun getGenres(): Flow<Result<List<String>>> = flow {
        emit(
            moviesGeneralInfoDataSource.getGenres().fold(
                onSuccess = { data -> Result.success(data) },
                onFailure = { e -> Result.failure(ErrorMapper.map(e)) },
            ),
        )
    }

    override fun getCountries(): Flow<Result<List<MovieCountry>>> = flow {
        emit(
            moviesGeneralInfoDataSource.getCountries().fold(
                onSuccess = { data ->
                    Result.success(
                        data.map { country ->
                            country.toDomain()
                        },
                    )
                },
                onFailure = { e -> Result.failure(ErrorMapper.map(e)) },
            ),
        )
    }

    override fun getLanguages(): Flow<Result<List<MovieLanguage>>> = flow {
        emit(
            moviesGeneralInfoDataSource.getLanguages().fold(
                onSuccess = { data ->
                    Result.success(
                        data.map { language ->
                            language.toDomain()
                        },
                    )
                },
                onFailure = { e -> Result.failure(ErrorMapper.map(e)) },
            ),
        )
    }
}