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

package com.smmousavi.i_feature.movies.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smmousavi.i_core.designsystem.movies.ImdbFilterChip
import com.smmousavi.i_core.model.movies.MoviesGeneralInfoModel

@Composable
fun MoviesScreen(
    modifier: Modifier = Modifier,
    data: MoviesGeneralInfoModel,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // genres row
        data.genres?.let { genres ->
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = genres,
                    key = { it },
                ) { genre ->
                    ImdbFilterChip(label = genre) {
                    }
                }
            }
        }

        // types row
        data.types?.let { types ->
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = types,
                    key = { it },
                ) { type ->
                    ImdbFilterChip(label = type) {
                    }
                }
            }
        }

        // languages row
        data.languages?.let { languages ->
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = languages,
                    key = { it.label },
                ) { language ->
                    ImdbFilterChip(label = language.name) {
                    }
                }
            }
        }

        data.countries?.let { countries ->
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = countries,
                    key = { it.label },
                ) { country ->
                    ImdbFilterChip(label = country.name) {
                    }
                }
            }
        }
    }
}

