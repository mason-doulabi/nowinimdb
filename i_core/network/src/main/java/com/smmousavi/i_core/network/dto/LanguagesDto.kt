package com.smmousavi.i_core.network.dto

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable


@InternalSerializationApi
@Serializable
data class LanguagesDto(val languages: List<LanguageItemDto>?)

@InternalSerializationApi
@Serializable
data class LanguageItemDto(
    val name: String?,
    val label: String?,
)