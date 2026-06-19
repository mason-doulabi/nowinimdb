package com.smmousavi.i_core.network.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable

@InternalSerializationApi
@Serializable
data class MoviesLanguageItemDto(
    val name: String?,
    val iso_639_1: String?,
)