package com.dystopia.moviecatalogueapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MoviesItem(
    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("backdrop_path")
    val backdropPath: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("id")
    val id: Int,

    @SerializedName("overview")
    val overview: String,
)
