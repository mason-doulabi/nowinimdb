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

package com.smmousavi.i_core.model.movies.movie

import androidx.compose.runtime.Immutable

@Immutable
data class Movie(
    val id: String,
    val averageRating: Double,
    val budget: Long,
    val contentRating: String,
    val countriesOfOrigin: List<String>,
    val description: String,
    val startYear: Int,
    val endYear: Int,
    val externalLinks: List<String>,
    val filmingLocations: List<String>,
    val genres: List<String>,
    val grossWorldwide: Long,
    val interests: List<String>,
    val isAdult: Boolean,
    val metascore: Int,
    val numVotes: Int,
    val originalTitle: String,
    val primaryImage: String,
    val primaryTitle: String,
    val productionCompanies: List<MovieProductionCompany>,
    val releaseDate: String,
    val runtimeMinutes: Int,
    val spokenLanguages: List<String>,
    val thumbnails: List<MovieThumbnail>,
    val trailer: String,
    val type: String,
    val url: String,
) {
    companion object {
        val DEFAULT1 = Movie(
            id = "tt0068646",
            averageRating = 9.2,
            budget = 6000000,
            contentRating = "R",
            countriesOfOrigin = listOf("US"),
            description = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
            startYear = 1972,
            endYear = 0, // JSON value is null
            externalLinks = listOf(
                "https://www.facebook.com/thegodfather/",
                "https://www.instagram.com/thegodfathermovie/"
            ),
            filmingLocations = listOf(
                "Forza d'Agrò, Messina, Sicily, Italy"
            ),
            genres = listOf(
                "Crime",
                "Drama"
            ),
            grossWorldwide = 250342198,
            interests = listOf(
                "Epic",
                "Gangster",
                "Tragedy",
                "Crime",
                "Drama"
            ),
            isAdult = false,
            metascore = 100,
            numVotes = 2133570,
            originalTitle = "The Godfather",
            primaryImage = "https://m.media-amazon.com/images/M/MV5BNGEwYjgwOGQtYjg5ZS00Njc1LTk2ZGEtM2QwZWQ2NjdhZTE5XkEyXkFqcGc@.jpg",
            primaryTitle = "The Godfather",
            productionCompanies = listOf(
                MovieProductionCompany(
                    id = "co0023400",
                    name = "Paramount Pictures",
                ),
                MovieProductionCompany(
                    id = "co0921482",
                    name = "Albert S. Ruddy Productions",
                ),
                MovieProductionCompany(
                    id = "co0255097",
                    name = "Alfran Productions",
                ),
            ),
            releaseDate = "1972-03-24",
            runtimeMinutes = 175,
            spokenLanguages = listOf(
                "en",
                "it",
                "la",
            ),
            thumbnails = listOf(
                MovieThumbnail(
                    height = 148,
                    width = 100,
                    url = "https://m.media-amazon.com/images/M/MV5BNGEwYjgwOGQtYjg5ZS00Njc1LTk2ZGEtM2QwZWQ2NjdhZTE5XkEyXkFqcGc@._V1_QL75_UX100_CR0,0,100,148_.jpg",
                ),
                MovieThumbnail(
                    height = 414,
                    width = 280,
                    url = "https://m.media-amazon.com/images/M/MV5BNGEwYjgwOGQtYjg5ZS00Njc1LTk2ZGEtM2QwZWQ2NjdhZTE5XkEyXkFqcGc@._V1_QL75_UX280_CR0,0,280,414_.jpg",
                ),
            ),
            trailer = "https://www.youtube.com/watch?v=8V2k2YQEQJ4",
            type = "movie",
            url = "https://www.imdb.com/title/tt0068646/",
        )

        val DEFAULT2 = Movie(
            id = "tt0468569",
            averageRating = 9.0,
            budget = 185000000,
            contentRating = "PG-13",
            countriesOfOrigin = listOf(
                "US",
                "GB",
            ),
            description = "When a menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman, James Gordon and Harvey Dent must work together to put an end to the madness.",
            startYear = 2008,
            endYear = 0, // JSON value is null
            externalLinks = listOf(
                "https://www.facebook.com/darkknighttrilogy/",
                "https://www.warnerbros.co.uk/movies/dark-knight",
            ),
            filmingLocations = listOf(
                "Chicago, Illinois, USA",
            ),
            genres = listOf(
                "Action",
                "Crime",
                "Drama",
            ),
            grossWorldwide = 1012558979,
            interests = listOf(
                "Action Epic",
                "Epic",
                "Superhero",
                "Tragedy",
                "Action",
                "Crime",
                "Drama",
                "Thriller",
            ),
            isAdult = false,
            metascore = 84,
            numVotes = 3031290,
            originalTitle = "The Dark Knight",
            primaryImage = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@.jpg",
            primaryTitle = "The Dark Knight",
            productionCompanies = listOf(
                MovieProductionCompany(
                    id = "co0002663",
                    name = "Warner Bros.",
                ),
                MovieProductionCompany(
                    id = "co0159111",
                    name = "Legendary Entertainment",
                ),
                MovieProductionCompany(
                    id = "co0147954",
                    name = "Syncopy",
                ),
            ),
            releaseDate = "2008-07-18",
            runtimeMinutes = 152,
            spokenLanguages = listOf("en"),
            thumbnails = listOf(
                MovieThumbnail(
                    height = 148,
                    width = 100,
                    url = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_QL75_UX100_CR0,0,100,148_.jpg",
                ),
                MovieThumbnail(
                    height = 414,
                    width = 280,
                    url = "https://m.media-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1_QL75_UX280_CR0,0,280,414_.jpg",
                ),
            ),
            trailer = "https://www.youtube.com/watch?v=kmJLuwP3MbY",
            type = "movie",
            url = "https://www.imdb.com/title/tt0468569/",
        )

        val DEFAULT3 = Movie(
            id = "tt0111161",
            averageRating = 9.3,
            budget = 25000000,
            contentRating = "R",
            countriesOfOrigin = listOf("US"),
            description = "A banker convicted of uxoricide forms a friendship over a quarter century with a hardened convict, while maintaining his innocence and trying to remain hopeful through simple compassion.",
            startYear = 1994,
            endYear = -1,
            externalLinks = listOf(
                "https://www.facebook.com/shawshankredemptionfilm/",
                "https://www.warnerbros.com/movies/shawshank-redemption",
            ),
            filmingLocations = listOf(
                "Mansfield Reformatory - 100 Reformatory Road, Mansfield, Ohio, USA",
            ),
            genres = listOf("Drama"),
            grossWorldwide = 29333735,
            interests = listOf(
                "Epic",
                "Period Drama",
                "Prison Drama",
                "Drama",
            ),
            isAdult = false,
            metascore = 82,
            numVotes = 3056217,
            originalTitle = "The Shawshank Redemption",
            primaryImage = "https://m.media-amazon.com/images/M/MV5BMDAyY2FhYjctNDc5OS00MDNlLThiMGUtY2UxYWVkNGY2ZjljXkEyXkFqcGc@.jpg",
            primaryTitle = "The Shawshank Redemption",
            productionCompanies = listOf(
                MovieProductionCompany(
                    id = "co0040620",
                    name = "Castle Rock Entertainment",
                ),
            ),
            releaseDate = "1994-10-14",
            runtimeMinutes = 142,
            spokenLanguages = listOf("en"),
            thumbnails = listOf(
                MovieThumbnail(
                    url = "https://m.media-amazon.com/images/M/MV5BMDAyY2FhYjctNDc5OS00MDNlLThiMGUtY2UxYWVkNGY2ZjljXkEyXkFqcGc@._V1_QL75_UX100_CR0,0,100,148_.jpg",
                    width = 100,
                    height = 148,
                ),
                MovieThumbnail(
                    url = "https://m.media-amazon.com/images/M/MV5BMDAyY2FhYjctNDc5OS00MDNlLThiMGUtY2UxYWVkNGY2ZjljXkEyXkFqcGc@._V1_QL75_UX280_CR0,0,280,414_.jpg",
                    width = 280,
                    height = 414,
                ),
            ),
            trailer = "https://www.youtube.com/watch?v=xyXX8LXiNJ4",
            type = "movie",
            url = "https://www.imdb.com/title/tt0111161/",
        )
    }
}

@Immutable
data class MovieProductionCompany(
    val id: String,
    val name: String,
)

@Immutable
data class MovieThumbnail(
    val height: Int,
    val url: String,
    val width: Int,
)