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

package com.smmousavi.i_feature.search.impl.xml.rv

import androidx.recyclerview.widget.DiffUtil
import com.smmousavi.i_core.model.movies.movie.MovieModel

object SearchDiffCallback : DiffUtil.ItemCallback<MovieModel>() {

    override fun areItemsTheSame(
        p0: MovieModel,
        p1: MovieModel,
    ): Boolean {
        return p0.id == p1.id
    }

    override fun areContentsTheSame(
        p0: MovieModel,
        p1: MovieModel,
    ): Boolean {
        return p0 == p1
    }
}