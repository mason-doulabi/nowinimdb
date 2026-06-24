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

package com.smmousavi.i_core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.smmousavi.i_core.database.entity.MovieItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Upsert
    suspend fun upsertMovie(movie: MovieItemEntity)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: String): MovieItemEntity?

    @Query("SELECT * FROM movies")
    fun observeAllMovies(): Flow<List<MovieItemEntity>>

    @Query("SELECT * FROM movies WHERE favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieItemEntity>>

    @Query("SELECT * FROM movies WHERE watchLater = 1")
    fun getWatchLaterMovies(): Flow<List<MovieItemEntity>>

    @Query("SELECT * FROM movies WHERE recentlySearched = 1 ORDER BY searchedTime DESC LIMIT 10")
    fun getRecentlySearchedMovies(): Flow<List<MovieItemEntity>>
}