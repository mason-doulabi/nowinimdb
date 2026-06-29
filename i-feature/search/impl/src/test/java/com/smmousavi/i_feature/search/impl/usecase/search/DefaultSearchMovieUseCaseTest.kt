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

package com.smmousavi.i_feature.search.impl.usecase.search

import com.smmousavi.domain.usecase.search.DefaultSearchMovieUseCase
import com.smmousavi.i_core.testing.repository.FakeSearchMovieRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class DefaultSearchMovieUseCaseTest {

    private lateinit var repository: FakeSearchMovieRepository
    private lateinit var useCase: DefaultSearchMovieUseCase

    @Before
    fun setup() {
        repository = FakeSearchMovieRepository()
        useCase = DefaultSearchMovieUseCase(
            repository = repository,
        )
    }

    @Test
    fun searchMovie_returnsRepositoryResult() = runTest {
        val result = useCase.searchMovie("Dark").first()

        assertTrue { result.isSuccess }

        assertTrue { result.getOrThrow().size == 2 }
    }

    @Test
    fun autoComplete_returnsRepositoryResult() = runTest {
        val result = useCase.autoComplete("Dark").first()

        assertTrue { result.isSuccess }

        assertTrue { result.getOrThrow().size == 1 }
    }

    @Test
    fun searchMovie_returnsFailure_whenRepositoryFails() = runTest {
        repository.shouldFail = true
        val result = repository.fetchSearchMovieResult("Dark").first()

        assertTrue { result.isFailure }
    }

    @Test
    fun autoComplete_returnsFailure_whenRepositoryFails() = runTest {
        repository.shouldFail = true
        val result = repository.fetchAutoCompleteResult("Dark").first()

        assertTrue { result.isFailure }
    }
}