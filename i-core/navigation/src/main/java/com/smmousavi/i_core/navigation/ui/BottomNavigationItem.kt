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

package com.smmousavi.i_core.navigation.ui

import androidx.compose.ui.graphics.vector.ImageVector
import com.google.samples.apps.nowinandroid.core.designsystem.icon.NiaIcons
import com.smmousavi.i_core.navigation.desitnation.ImdbDestination

data class BottomNavigationItem(
    val destination: ImdbDestination,
    val label: String,
    val icon: ImageVector,
) {
    companion object {
        val ITEMS = listOf(
            BottomNavigationItem(
                ImdbDestination.Movies,
                label = "Movies",
                icon = NiaIcons.List,
            ),

            BottomNavigationItem(
                ImdbDestination.Search,
                label = "Search",
                icon = NiaIcons.Search,
            ),

            BottomNavigationItem(
                ImdbDestination.Profile,
                label = "Profile",
                icon = NiaIcons.Person,
            ),
        )
    }
}