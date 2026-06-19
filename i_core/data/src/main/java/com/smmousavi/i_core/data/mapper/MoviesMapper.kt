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

import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.ProductionCompanyModel
import com.smmousavi.i_core.model.movies.ThumbnailModel
import com.smmousavi.i_core.model.movies.generalinfo.MoviesCountryItemModel
import com.smmousavi.i_core.model.movies.generalinfo.MoviesLanguageItemModel
import com.smmousavi.i_core.network.dto.movies.MovieItemDto
import com.smmousavi.i_core.network.dto.movies.MoviesCountryItemDto
import com.smmousavi.i_core.network.dto.movies.MoviesLanguageItemDto
import com.smmousavi.i_core.network.dto.movies.ProductionCompanyDto
import com.smmousavi.i_core.network.dto.movies.ThumbnailDto
import kotlin.Double
import kotlin.Int
import kotlin.String

object MoviesMapper {

    fun MoviesLanguageItemDto.toDomain(): MoviesLanguageItemModel = MoviesLanguageItemModel(
        name = this.name ?: "",
        label = this.iso_639_1 ?: "",
    )

    fun MoviesCountryItemDto.toDomain(): MoviesCountryItemModel = MoviesCountryItemModel(
        name = this.name ?: "",
        label = this.iso_3166_1 ?: "",
    )

    fun MovieItemDto.toDomain(): MovieItemModel = MovieItemModel(
        id = this.id ?: "",
        averageRating = this.averageRating ?: -1.0,
        budget = this.budget ?: -1L,
        contentRating = this.contentRating ?: "",
        countriesOfOrigin = this.countriesOfOrigin ?: listOf(),
        description = this.description ?: "",
        startYear = this.startYear ?: -1,
        endYear = this.endYear ?: -1,
        releaseDate = this.releaseDate ?: "",
        runtimeMinutes = this.runtimeMinutes ?: -1,
        externalLinks = this.externalLinks ?: listOf(),
        filmingLocations = this.filmingLocations ?: listOf(),
        genres = this.genres ?: listOf(),
        grossWorldwide = this.grossWorldwide ?: -1L,
        interests = this.interests ?: listOf(),
        isAdult = this.isAdult ?: false,
        metascore = this.metascore ?: -1,
        numVotes = this.numVotes ?: -1,
        originalTitle = this.originalTitle ?: "",
        primaryImage = this.primaryImage ?: "",
        primaryTitle = this.primaryTitle ?: "",
        productionCompanies = this.productionCompanies?.map { it.toDomain() } ?: listOf(),
        spokenLanguages = this.spokenLanguages ?: listOf(),
        thumbnails = this.thumbnails?.map { it.toDomain() } ?: listOf(),
        trailer = this.trailer ?: "",
        type = this.type ?: "",
        url = this.url ?: "",
    )

    fun ProductionCompanyDto.toDomain(): ProductionCompanyModel = ProductionCompanyModel(
        id = this.id ?: "",
        name = this.name ?: "",
    )

    fun ThumbnailDto.toDomain(): ThumbnailModel = ThumbnailModel(
        height = this.height ?: -1,
        url = this.url ?: "",
        width = this.width ?: -1,
    )
}