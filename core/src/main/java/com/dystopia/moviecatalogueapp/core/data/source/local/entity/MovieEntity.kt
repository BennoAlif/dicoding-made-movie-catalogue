package com.dystopia.moviecatalogueapp.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_entities")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "posterPath")
    var posterPath: String,

    @ColumnInfo(name = "backdropPath")
    var backdropPath: String,

    @ColumnInfo(name = "releaseDate")
    var releaseDate: String,

    @ColumnInfo(name = "genres")
    var genres: String,

    @ColumnInfo(name = "voteAverage")
    var voteAverage: Double,

    @ColumnInfo(name = "runtime")
    var runtime: Int,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "tagline")
    var tagline: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
) : Parcelable