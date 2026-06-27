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

package com.smmousavi.domain.usecase.generalinfo

import com.smmousavi.domain.repository.MoviesGeneralInfoRepository
import com.smmousavi.i_core.model.movies.movie.generalinfo.MovieCountry
import com.smmousavi.i_core.model.movies.movie.generalinfo.MovieLanguage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultMoviesGeneralInfoUseCase @Inject constructor(
    private val generalInfoRepository: MoviesGeneralInfoRepository,
) : MoviesGeneralInfoUseCase {

    override suspend fun getGenres(): Flow<Result<List<String>>> {
        return generalInfoRepository.getGenres()
    }

    override suspend fun getTypes(): Flow<Result<List<String>>> {
        return generalInfoRepository.getTypes()
    }

    override suspend fun getCountries(): Flow<Result<List<MovieCountry>>> {
        return generalInfoRepository.getCountries()
    }

    override suspend fun getLanguages(): Flow<Result<List<MovieLanguage>>> {
        return generalInfoRepository.getLanguages()
    }
}