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

package com.smmousavi.i_core.network.dto.movies

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
@InternalSerializationApi
data class MovieItemDto(
    val averageRating: Double?,
    val budget: Long?,
    val contentRating: String?,
    val countriesOfOrigin: List<String>?,
    val description: String?,
    val startYear: Int?,
    val endYear: Int?,
    val externalLinks: List<String>?,
    val filmingLocations: List<String>?,
    val genres: List<String>?,
    val grossWorldwide: Long?,
    val id: String?,
    val interests: List<String>?,
    val isAdult: Boolean?,
    val metascore: Int?,
    val numVotes: Int?,
    val originalTitle: String?,
    val primaryImage: String?,
    val primaryTitle: String?,
    val productionCompanies: List<ProductionCompanyDto>?,
    val releaseDate: String?,
    val runtimeMinutes: Int?,
    val spokenLanguages: List<String>?,
    val thumbnails: List<ThumbnailDto>?,
    val trailer: String?,
    val type: String?,
    val url: String?,
)

@Serializable
@InternalSerializationApi
data class ProductionCompanyDto(
    val id: String?,
    val name: String?,
)

@Serializable
@InternalSerializationApi
data class ThumbnailDto(
    val height: Int?,
    val url: String?,
    val width: Int?,
)