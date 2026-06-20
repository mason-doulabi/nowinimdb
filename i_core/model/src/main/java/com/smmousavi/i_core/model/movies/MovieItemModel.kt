package com.smmousavi.i_core.model.movies

data class MovieItemModel(
    val id: String,
    val title: String,
    val rating: Double,
    val description: String,
    val thumbnailUrl: String,
    val favorite: Boolean,
    val watchLater: Boolean,
)