package com.dystopia.moviecatalogueapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @field:SerializedName("backdrop_path")
    val backdropPath: String,

    @field:SerializedName("genres")
    val genres: List<GenresItem>,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("runtime")
    val runtime: Int,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    @field:SerializedName("tagline")
    val tagline: String,
)

