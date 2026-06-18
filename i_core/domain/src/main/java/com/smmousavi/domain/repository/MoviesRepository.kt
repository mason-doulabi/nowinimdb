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

package com.smmousavi.domain.repository

import com.smmousavi.i_core.model.domain.CountriesModel
import com.smmousavi.i_core.model.domain.GenresModel
import com.smmousavi.i_core.model.domain.LanguagesModel
import com.smmousavi.i_core.model.domain.TypesModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getTypes(): Flow<Result<TypesModel>>

    suspend fun getGenres(): Flow<Result<GenresModel>>

    suspend fun getCountries(): Flow<Result<CountriesModel>>

    suspend fun getLanguages(): Flow<Result<LanguagesModel>>
}