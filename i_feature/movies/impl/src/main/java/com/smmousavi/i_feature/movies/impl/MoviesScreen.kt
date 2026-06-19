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
    generalInfo: MoviesGeneralInfoModel,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // genres row
        generalInfo.genres?.let { genres ->
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = genres,
                    key = { it },
                ) { genre ->
                    ImdbFilterChip(label = genre) {
                        // onClick actions
                    }
                }
            }
        }

        // types row
        generalInfo.types?.let { types ->
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = types,
                    key = { it },
                ) { type ->
                    ImdbFilterChip(label = type) {
                        // onClick actions
                    }
                }
            }
        }

        // languages row
        generalInfo.languages?.let { languages ->
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = languages,
                    key = { it.label },
                ) { language ->
                    ImdbFilterChip(label = language.name) {
                        // onClick actions
                    }
                }
            }
        }

        generalInfo.countries?.let { countries ->
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = countries,
                    key = { it.label },
                ) { country ->
                    ImdbFilterChip(label = country.name) {
                        // onClick actions
                    }
                }
            }
        }
    }
}

