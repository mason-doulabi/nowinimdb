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

package com.smmousavi.i_core.navigation.desitnation

sealed class ImdbDestination(val route: String) {

    data object Movies : ImdbDestination(route = "movies")
    data object Search : ImdbDestination(route = "search")
    data object Profile : ImdbDestination(route = "profile")

    data object MovieDetails : ImdbDestination(route = "movie_details/{movieId}") {
        const val ARG_MOVIE_ID = "movieId"

        fun createRoute(movieId: String) = "movie_details/$movieId"
    }

    data object Login : ImdbDestination(route = "login")

    companion object {
        fun fromRoute(route: String?): ImdbDestination =
            when {
                route == Movies.route -> Movies
                route == Search.route -> Search
                route == Profile.route -> Profile
                route == Login.route -> Login
                route?.startsWith("movie_details") == true -> MovieDetails
                else -> Movies
            }
    }
}