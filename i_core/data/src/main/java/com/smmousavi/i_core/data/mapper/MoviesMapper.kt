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

package com.smmousavi.i_core.data.mapper

import com.smmousavi.i_core.model.domain.CountriesModel
import com.smmousavi.i_core.model.domain.CountryItemModel
import com.smmousavi.i_core.model.domain.GenresModel
import com.smmousavi.i_core.model.domain.LanguageItemModel
import com.smmousavi.i_core.model.domain.LanguagesModel
import com.smmousavi.i_core.model.domain.TypesModel
import com.smmousavi.i_core.network.dto.CountriesDto
import com.smmousavi.i_core.network.dto.CountryItemDto
import com.smmousavi.i_core.network.dto.GenresDto
import com.smmousavi.i_core.network.dto.LanguageItemDto
import com.smmousavi.i_core.network.dto.LanguagesDto
import com.smmousavi.i_core.network.dto.TypesDto

object MoviesMapper {
    fun LanguagesDto.toDomain(): LanguagesModel = LanguagesModel(
        languages?.map { it.toDomain() } ?: listOf(),
    )

    fun LanguageItemDto.toDomain(): LanguageItemModel = LanguageItemModel(
        name = this.name ?: "",
        label = this.label ?: "",
    )

    fun CountriesDto.toDomain(): CountriesModel = CountriesModel(
        countries?.map { it.toDomain() } ?: listOf(),
    )

    fun CountryItemDto.toDomain(): CountryItemModel = CountryItemModel(
        name = this.name ?: "",
        label = this.label ?: "",
    )

    fun GenresDto.toDomain(): GenresModel = GenresModel(this.genres ?: listOf())

    fun TypesDto.toDomain(): TypesModel = TypesModel(this.types ?: listOf())
}