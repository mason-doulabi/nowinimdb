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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smmousavi.i_core.navigation.desitnation.ImdbDestination
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
        moviesGraph()

        searchGraph()

        profileGraph()
    }
}

fun NavGraphBuilder.moviesGraph() {
    composable(ImdbDestination.Movies.route) {
        MoviesScreen()
    }
}

fun NavGraphBuilder.searchGraph() {
    composable(ImdbDestination.Search.route) {
        SearchScreen()
    }
}

fun NavGraphBuilder.profileGraph() {
    composable(ImdbDestination.Profile.route) {
        ProfilScreen()
    }
}