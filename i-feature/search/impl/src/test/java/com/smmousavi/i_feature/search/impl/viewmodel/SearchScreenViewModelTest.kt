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

import app.cash.turbine.test
import com.smmousavi.domain.usecase.search.recent.RecentSearchesUseCase
import com.smmousavi.i_core.model.movies.movie.Movie
import com.smmousavi.i_core.model.movies.movie.MovieModel
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_core.testing.usecase.FakeRecentSearchUseCase
import com.smmousavi.i_core.testing.usecase.FakeSearchMovieSharedUseCase
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
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class SearchScreenViewModelTest {

    private lateinit var searchUseCase: FakeSearchMovieUseCase
    private lateinit var recentSearchesUseCase: RecentSearchesUseCase
    private lateinit var viewModel: SearchScreenViewModel

    private fun TestScope.startCollection() = launch {
        viewModel.searchMovieState.collect()
    }

    @Before
    fun setup() {
        Dispatchers.setMain(
            StandardTestDispatcher(),
        )
        searchUseCase = FakeSearchMovieUseCase()
        recentSearchesUseCase = FakeRecentSearchUseCase()
        viewModel = SearchScreenViewModel(
            searchMovieUseCase = searchUseCase,
            recentSearchesUseCase = recentSearchesUseCase,
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
        assertTrue { searchUseCase.callCount == 1 }
        assertTrue { searchUseCase.lastQuery == "Dark" }

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

        assertTrue { searchUseCase.callCount == 1 }
        assertTrue { searchUseCase.lastQuery == "Dark" }

        collector.cancel()
    }

    @Test
    fun search_emits_idle_then_loading_then_success() = runTest {
        // Arrange
        val emittedStates = mutableListOf<UiState<List<MovieModel>>>()
        val collector = launch {
            viewModel.searchMovieState
                .collect {
                    emittedStates.add(it)
                }
        }
        // Act
        viewModel.onQueryChange("Dark")
        advanceTimeBy(300)
        runCurrent()
        advanceTimeBy(100)
        runCurrent()

        // Assert
        assertTrue { emittedStates[0] is UiState.Idle }
        assertTrue { emittedStates[1] is UiState.Loading }
        assertTrue { emittedStates[2] is UiState.Success }

        collector.cancel()
    }

    @Test
    fun search_emits_single_item() = runTest {
        viewModel.searchMovieState.test {
            assertIs<UiState.Idle>(awaitItem())

            viewModel.onQueryChange(("Dark"))
            advanceTimeBy(300)
            runCurrent()

            assertIs<UiState.Loading>(awaitItem())

            advanceTimeBy(100)
            runCurrent()

            val success = awaitItem()

            assertIs<UiState.Success<List<MovieModel>>>(success)

            assertEquals(
                expected = Movie.DEFAULT1.originalTitle,
                actual = success.data.first().title,
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun search_uses_only_latest_query_with_flatMapLatest() = runTest {
        val searchUseCase = FakeSearchMovieSharedUseCase()
        val recentUseCase = FakeRecentSearchUseCase()
        val viewModel = SearchScreenViewModel(
            searchMovieUseCase = searchUseCase,
            recentSearchesUseCase = recentUseCase,
        )

        viewModel.searchMovieState.test {

            assertIs<UiState.Idle>(awaitItem())

            viewModel.onQueryChange("D")
            viewModel.onQueryChange("Da")
            viewModel.onQueryChange("Dar")
            viewModel.onQueryChange("Dark")

            advanceTimeBy(300)
            runCurrent()

            assertIs<UiState.Loading>(awaitItem())

            searchUseCase.emitSuccess(listOf(Movie.DEFAULT1))
            runCurrent()

            val success = awaitItem()

            assertIs<UiState.Success<List<MovieModel>>>(success)

            assertEquals(
                expected = "Dark",
                actual = searchUseCase.lastQuery,
            )

            assertEquals(
                expected = 1,
                actual = searchUseCase.callCount,
            )

            assertEquals(
                expected = Movie.DEFAULT1.originalTitle,
                actual = success.data.first().title,
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}