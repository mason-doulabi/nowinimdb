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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.smmousavi.i_core.designsystem.databinding.ItemMovieCardBinding
import com.smmousavi.i_core.model.movies.movie.MovieModel

class MoviesListAdapter(
    private val onMovieClick: (MovieModel) -> Unit,
    private val onFavoriteClick: (MovieModel) -> Unit,
) : androidx.recyclerview.widget.ListAdapter<MovieModel, MoviesViewHolder>(MovieDiffCallback) {

    init {
        setHasStableIds(true)
        // prevent scrolling to the wrong position when the data list is empty
        // mostly happens when restoring item position after rotation
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun getItemId(position: Int): Long = getItem(position)
        .id
        .hashCode()
        .toLong()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MoviesViewHolder {
        val binding = ItemMovieCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return MoviesViewHolder(
            binding,
            onMovieClick,
            onFavoriteClick,
        )
    }

    override fun onBindViewHolder(
        holder: MoviesViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    object MovieDiffCallback : DiffUtil.ItemCallback<MovieModel>() {

        override fun areItemsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieModel,
            newItem: MovieModel,
        ): Boolean {
            return oldItem == newItem
        }
    }
}