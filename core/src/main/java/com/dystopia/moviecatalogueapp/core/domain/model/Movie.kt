package com.dystopia.moviecatalogueapp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val genres: String,
    val voteAverage: Double,
    val runtime: Int,
    val overview: String,
    val tagline: String,
    val isFavorite: Boolean
) : Parcelable

