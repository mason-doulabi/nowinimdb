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

package com.smmousavi.i_core.data.mapper.dto

import com.smmousavi.i_core.model.movies.movie.MovieCast
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.movie.MoviePoster
import com.smmousavi.i_core.model.movies.movie.MovieProductionCompany
import com.smmousavi.i_core.model.movies.movie.MovieThumbnail
import com.smmousavi.i_core.model.movies.movie.generalinfo.MovieCountry
import com.smmousavi.i_core.model.movies.movie.generalinfo.MovieLanguage
import com.smmousavi.i_core.network.dto.movies.MovieCastDto
import com.smmousavi.i_core.network.dto.movies.MovieDto
import com.smmousavi.i_core.network.dto.movies.MoviePosterDto
import com.smmousavi.i_core.network.dto.movies.MoviesCountryDto
import com.smmousavi.i_core.network.dto.movies.MoviesLanguageDto
import com.smmousavi.i_core.network.dto.movies.MovieProductionCompanyDto
import com.smmousavi.i_core.network.dto.movies.MovieThumbnailDto

object MovieDtoMapper {

    fun MoviesLanguageDto.toDomain(): MovieLanguage = MovieLanguage(
        name = this.name ?: "",
        label = this.label ?: "",
    )

    fun MoviesCountryDto.toDomain(): MovieCountry = MovieCountry(
        name = this.name ?: "",
        label = this.label ?: "",
    )

    fun MovieDto.toDomain(): Movie = Movie(
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

    fun MovieDto.toModel(
        favorite: Boolean = false,
        watchLater: Boolean = false,
        recentlySearched: Boolean = false,
        searchedTime: Long = 0,
    ): MovieModel =
        MovieModel(
            id = this.id ?: "",
            title = this.originalTitle ?: "",
            rating = this.averageRating ?: 0.0,
            description = this.description ?: "",
            imageUrl = this.primaryImage ?: "",
            votes = this.numVotes ?: 0,
            year = this.startYear ?: 0,
            type = this.type ?: "",
            durationMins = this.runtimeMinutes ?: 0,
            interests = this.interests ?: listOf(),
            thumbnails = this.thumbnails?.map { it.url ?: "" } ?: listOf(),
            favorite = favorite,
            watchLater = watchLater,
            recentlySearched = recentlySearched,
            searchedTime = searchedTime,
        )

    fun MovieCastDto.toDomain() = MovieCast(
        id = this.id ?: "",
        fullName = this.fullName ?: "",
        primaryImage = this.primaryImage ?: "",
        job = this.job ?: "",
        url = this.url ?: "",
        characters = this.characters ?: listOf(),
        thumbnails = this.thumbnails?.map { it.toDomain() } ?: listOf(),
    )

    fun MoviePosterDto.toDomain() = MoviePoster(
        id = this.id ?: "",
        poster = this.poster ?: "",
        thumbnails = this.thumbnails?.map { it.toDomain() } ?: listOf(),
        url = this.url ?: "",
    )

    fun MovieProductionCompanyDto.toDomain(): MovieProductionCompany = MovieProductionCompany(
        id = this.id ?: "",
        name = this.name ?: "",
    )

    fun MovieThumbnailDto.toDomain(): MovieThumbnail = MovieThumbnail(
        height = this.height ?: -1,
        url = this.url ?: "",
        width = this.width ?: -1,
    )
}