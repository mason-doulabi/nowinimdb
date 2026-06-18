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

package com.smmousavi.i_core.data.datasource.movies

import com.smmousavi.i_core.network.di.IMDbQualifier
import com.smmousavi.i_core.network.dto.MoviesCountryItemDto
import com.smmousavi.i_core.network.dto.MoviesLanguageItemDto
import com.smmousavi.i_core.network.service.GeneralApiService
import javax.inject.Inject

class DefaultMoviesRemoteDataSource @Inject constructor(
    @IMDbQualifier private val generalApiService: GeneralApiService,
) : MoviesRemoteDataSource {
    override suspend fun getTypes(): Result<List<String>> = runCatching {
        generalApiService.getTypes()
    }

    override suspend fun getGenres(): Result<List<String>> = runCatching {
        generalApiService.getGenres()
    }

    override suspend fun getCountries(): Result<List<MoviesCountryItemDto>> = runCatching {
        generalApiService.getCountries()
    }

    override suspend fun getLanguages(): Result<List<MoviesLanguageItemDto>> = runCatching {
        generalApiService.getLanguages()
    }
}