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

package com.smmousavi.i_feature.movies.impl.xml.rv

import android.util.Log
import coil3.load
import coil3.request.allowHardware
import coil3.request.crossfade
import coil3.size.Scale
import com.smmousavi.i_core.designsystem.databinding.ItemMovieCardBinding
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.presentation.xml.BaseViewHolder

class MoviesViewHolder(
    private val binding: ItemMovieCardBinding,
    private val onMovieClick: (MovieModel) -> Unit,
    private val onFavoriteClick: (MovieModel) -> Unit,
) : BaseViewHolder<MovieModel>(binding.root) {

    override fun bind(item: MovieModel): Unit = with(binding) {
        root.setOnClickListener {
            onMovieClick(item)
        }

        imgFavoriteIcon.setOnClickListener {
            onFavoriteClick(item)
        }

        txtMovieTitle.text = item.title
        txtMovieDescription.text = item.description
        txtRating.text = item.rating.toString()
        imgMovieCover.load(
            if (item.thumbnails.isNotEmpty()) {
                item.thumbnails.first()
            } else {
                item.imageUrl
            },
        )
    }
}