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

package com.smmousavi.i_feature.search.impl.viewmodel

import com.smmousavi.i_core.testing.usecase.FakeSearchMovieUseCase
import com.smmousavi.i_feature.search.impl.SearchScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchScreenViewModelTest {

    private lateinit var useCase: FakeSearchMovieUseCase
    private lateinit var viewModel: SearchScreenViewModel

    private fun TestScope.startCollection() = launch {
        viewModel.searchMovieState.collect()
    }

    @Before
    fun setup() {
        Dispatchers.setMain(
            StandardTestDispatcher(),
        )
        useCase = FakeSearchMovieUseCase()
        viewModel = SearchScreenViewModel(
            searchMovieUseCase = useCase,
        )
    }

    @Test
    fun search_is_triggered_after_debounce() = runTest {
        // Arrange
        val collector = startCollection()

        // Act
        viewModel.onQueryChange("Dark")
        advanceTimeBy(300)
        runCurrent()

        // Assert
        assertTrue { useCase.searchMovieCallCount == 1 }
        assertTrue { useCase.lastQuery == "Dark" }

        collector.cancel()
    }

    @Test
    fun rapid_typing_only_riggers_single_search_request() = runTest {
        val collector = startCollection()

        viewModel.onQueryChange("D")
        advanceTimeBy(100)

        viewModel.onQueryChange("Da")
        advanceTimeBy(100)

        viewModel.onQueryChange("Dar")
        advanceTimeBy(100)

        viewModel.onQueryChange("Dark")
        advanceTimeBy(300)
        runCurrent()

        assertTrue { useCase.searchMovieCallCount == 1 }
        assertTrue { useCase.lastQuery == "Dark" }

        collector.cancel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}