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
import com.smmousavi.i_core.model.movies.MovieItemModel
import com.smmousavi.i_core.model.movies.MovieItem

object MoviesEntityMapper {

    fun MovieItemEntity.toModel(): MovieItemModel = MovieItemModel(
        id = this.id,
        title = this.title,
        rating = this.rating,
        description = this.description,
        imageUrl = this.imageUrl,
        thumbnailsUrl = this.thumbnailsUrl,
        favorite = this.favorite.fromDbBool(),
        watchLater = this.watchLater.fromDbBool(),
    )

    fun MovieItemModel.toEntity(): MovieItemEntity = MovieItemEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        description = this.description,
        imageUrl = this.imageUrl,
        thumbnailsUrl = this.thumbnailsUrl,
        favorite = this.favorite.toDbBool(),
        watchLater = this.watchLater.toDbBool(),
    )

    fun MovieItem.toEntity(
        favorite: Boolean = false,
        watchLater: Boolean = false,
    ): MovieItemEntity =
        MovieItemEntity(
            id = this.id,
            title = this.originalTitle,
            rating = this.averageRating,
            description = this.description,
            imageUrl = this.primaryImage,
            thumbnailsUrl = this.thumbnails.map { it.url },
            favorite = favorite.toDbBool(),
            watchLater = watchLater.toDbBool(),
        )

    fun Boolean.toDbBool(): Int = if (this) 1 else 0

    fun Int.fromDbBool(): Boolean = this == 1
}