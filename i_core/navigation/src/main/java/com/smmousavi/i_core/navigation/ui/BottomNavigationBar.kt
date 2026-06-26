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

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smmousavi.i_core.navigation.desitnation.ImdbDestination

@Composable
fun ImdbBottomNavigationBar(
    modifier: Modifier = Modifier,
    navItems: List<BottomNavigationItem>,
    currentDestination: ImdbDestination,
    onDestinationClick: (ImdbDestination) -> Unit,
) {
    NavigationBar(modifier = modifier) {
        navItems.forEach { item ->
            key(item.label) {
                NavigationBarItem(
                    selected = currentDestination == item.destination,
                    onClick = {
                        onDestinationClick(item.destination)
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(text = item.label)
                    },
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ImdbBottomNavigationBarPreview() {
    ImdbBottomNavigationBar(
        navItems = BottomNavigationItem.DEFAULTS,
        currentDestination = ImdbDestination.Movies,
    ) { }
}
