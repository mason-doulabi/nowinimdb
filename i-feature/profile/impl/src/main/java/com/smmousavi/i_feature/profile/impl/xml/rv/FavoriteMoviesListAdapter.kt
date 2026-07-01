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

package com.smmousavi.i_feature.profile.impl.xml.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.smmousavi.i_core.designsystem.databinding.ItemMovieCardBinding
import com.smmousavi.i_core.model.movies.movie.MovieModel

class FavoriteMoviesListAdapter(
    private val onClick: (MovieModel) -> Unit,
) : ListAdapter<MovieModel, FavoriteMoviesViewHolder>(
    FavoriteMoviesItemDiffCallback,
) {

    init {
        setHasStableIds(true)
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteMoviesViewHolder {
        val binding =
            ItemMovieCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return FavoriteMoviesViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(
        holder: FavoriteMoviesViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}