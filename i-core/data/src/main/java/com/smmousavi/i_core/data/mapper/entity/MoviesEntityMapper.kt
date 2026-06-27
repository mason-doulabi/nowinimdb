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

package com.smmousavi.i_core.data.mapper.entity

import com.smmousavi.i_core.database.entity.MovieItemEntity
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.model.movies.movie.Movie

object MoviesEntityMapper {

    fun MovieItemEntity.toModel(): MovieModel = MovieModel(
        id = this.id,
        title = this.title,
        rating = this.rating,
        description = this.description,
        imageUrl = this.imageUrl,
        votes = this.votes,
        year = this.year,
        type = this.type,
        durationMins = this.durationMins,
        interests = this.interests,
        thumbnails = this.thumbnailsUrl,
        favorite = this.favorite.fromDbBool(),
        watchLater = this.watchLater.fromDbBool(),
        recentlySearched = this.recentlySearched.fromDbBool(),
        searchedTime = this.searchedTime,
    )

    fun MovieModel.toEntity(): MovieItemEntity = MovieItemEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        description = this.description,
        imageUrl = this.imageUrl,
        votes = this.votes,
        year = this.year,
        type = this.type,
        durationMins = this.durationMins,
        interests = this.interests,
        thumbnailsUrl = this.thumbnails,
        favorite = this.favorite.toDbBool(),
        watchLater = this.watchLater.toDbBool(),
        recentlySearched = this.recentlySearched.toDbBool(),
        searchedTime = this.searchedTime,
    )

    fun Movie.toEntity(
        favorite: Boolean = false,
        watchLater: Boolean = false,
        recentlySearched: Boolean = false,
        searchedTime: Long = 0,
    ): MovieItemEntity =
        MovieItemEntity(
            id = this.id,
            title = this.originalTitle,
            rating = this.averageRating,
            description = this.description,
            imageUrl = this.primaryImage,
            votes = this.numVotes,
            year = this.startYear,
            type = this.type,
            durationMins = this.runtimeMinutes,
            interests = this.interests,
            thumbnailsUrl = this.thumbnails.map { it.url },
            favorite = favorite.toDbBool(),
            watchLater = watchLater.toDbBool(),
            recentlySearched = recentlySearched.toDbBool(),
            searchedTime = searchedTime,
        )

    fun Boolean.toDbBool(): Int = if (this) 1 else 0

    fun Int.fromDbBool(): Boolean = this == 1
}