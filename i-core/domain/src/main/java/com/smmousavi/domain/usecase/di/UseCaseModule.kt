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

package com.smmousavi.domain.usecase.di

import com.smmousavi.domain.usecase.generalinfo.DefaultMoviesGeneralInfoUseCase
import com.smmousavi.domain.usecase.generalinfo.MoviesGeneralInfoUseCase
import com.smmousavi.domain.usecase.movies.details.DefaultMovieDetailsUseCase
import com.smmousavi.domain.usecase.movies.details.MovieDetailsUseCase
import com.smmousavi.domain.usecase.movies.favorite.DefaultFavoriteMoviesUseCase
import com.smmousavi.domain.usecase.movies.favorite.FavoriteMoviesUseCase
import com.smmousavi.domain.usecase.movies.top.DefaultTopMoviesUseCase
import com.smmousavi.domain.usecase.movies.top.TopMoviesUseCase
import com.smmousavi.domain.usecase.search.DefaultSearchMovieUseCase
import com.smmousavi.domain.usecase.search.SearchMovieUseCase
import com.smmousavi.domain.usecase.search.recent.DefaultRecentSearchesUseCase
import com.smmousavi.domain.usecase.search.recent.RecentSearchesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindsMovieDetailsUseCase(
        useCase: DefaultMovieDetailsUseCase,
    ): MovieDetailsUseCase

    @Binds
    abstract fun bindsMoviesGeneralInfoUseCase(
        useCase: DefaultMoviesGeneralInfoUseCase,
    ): MoviesGeneralInfoUseCase

    @Binds
    abstract fun bindsTop250MoviesUseCase(
        useCase: DefaultTopMoviesUseCase,
    ): TopMoviesUseCase

    @Binds
    abstract fun bindsFavoriteMoviesUseCase(
        useCase: DefaultFavoriteMoviesUseCase,
    ): FavoriteMoviesUseCase

    @Binds
    abstract fun bindsSearchMoviesUseCase(
        useCase: DefaultSearchMovieUseCase,
    ): SearchMovieUseCase

    @Binds
    abstract fun bindsRecentSearchesUseCase(
        useCase: DefaultRecentSearchesUseCase,
    ): RecentSearchesUseCase
}