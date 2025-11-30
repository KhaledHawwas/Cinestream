package com.cinestream.data

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("backdrop_path") val backdropPath: String? = null,
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("genres") val genres: List<Genre> = emptyList()
)

data class Genre(
    val id: Int,
    val name: String
)



