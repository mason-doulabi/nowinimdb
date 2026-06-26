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

package com.smmousavi.i_core.network.di

import com.smmousavi.i_core.network.service.GeneralApiService
import com.smmousavi.i_core.network.service.ImbdApiService
import com.smmousavi.i_core.network.service.SearchApiService
import com.smmousavi.i_core.network.service.MoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesGeneralApiService(@IMDbQualifier retrofit: Retrofit): GeneralApiService {
        return retrofit.create(GeneralApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesMoviesApiService(@IMDbQualifier retrofit: Retrofit): MoviesApiService {
        return retrofit.create(MoviesApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesSearchMoviesApiService(@IMDbQualifier retrofit: Retrofit): SearchApiService {
        return retrofit.create(SearchApiService::class.java)
    }
}