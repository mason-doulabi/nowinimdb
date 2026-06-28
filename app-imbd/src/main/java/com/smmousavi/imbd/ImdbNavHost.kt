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

package com.smmousavi.imbd

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.smmousavi.i_core.navigation.desitnation.ImdbDestination
import com.smmousavi.i_feature.details.MovieDetailsScreen
import com.smmousavi.i_feature.movies.impl.MoviesScreen
import com.smmousavi.i_feature.profile.impl.ProfilScreen
import com.smmousavi.i_feature.search.impl.SearchScreen

@Composable
fun ImdbNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ImdbDestination.Movies.route,
    ) {
        moviesGraph(
            onMovieClick = { movieId ->
                navController.navigate(ImdbDestination.MovieDetails.createRoute(movieId))
            },
            onBackClick = {
                navController.popBackStack()
            }
        )

        searchGraph(
            onMovieClicked = { movieId ->
                navController.navigate(ImdbDestination.MovieDetails.createRoute(movieId))
            },
            onBackClick = {
                navController.popBackStack()
            }
        )

        profileGraph()
    }
}

fun NavGraphBuilder.moviesGraph(
    onMovieClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(ImdbDestination.Movies.route) {
        MoviesScreen(
            onMovieClicked = onMovieClick,
        )
    }

    composable(
        route = ImdbDestination.MovieDetails.route,
        arguments = listOf(
            navArgument(ImdbDestination.MovieDetails.ARG_MOVIE_ID) {
                type = NavType.StringType
            },
        ),
    ) { backStackEntry ->
        val movieId =
            backStackEntry.arguments!!.getString(ImdbDestination.MovieDetails.ARG_MOVIE_ID)!!
        MovieDetailsScreen(
            movieId = movieId,
            onBackClick = onBackClick
        )
    }
}

fun NavGraphBuilder.searchGraph(
    onMovieClicked: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    composable(ImdbDestination.Search.route) {
        SearchScreen(onMovieClicked = onMovieClicked)
    }

    composable(
        route = ImdbDestination.MovieDetails.route,
        arguments = listOf(
            navArgument(ImdbDestination.MovieDetails.ARG_MOVIE_ID) {
                type = NavType.StringType
            },
        ),
    ) { backStackEntry ->
        val movieId =
            backStackEntry.arguments!!.getString(ImdbDestination.MovieDetails.ARG_MOVIE_ID)!!
        MovieDetailsScreen(
            movieId = movieId,
        ) {
            onBackClick()
        }
    }
}

fun NavGraphBuilder.profileGraph() {
    composable(ImdbDestination.Profile.route) {
        ProfilScreen()
    }
}