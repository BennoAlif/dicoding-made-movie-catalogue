package com.dystopia.moviecatalogueapp.core.utils

import com.dystopia.moviecatalogueapp.core.data.source.local.entity.MovieEntity
import com.dystopia.moviecatalogueapp.core.data.source.remote.response.MovieDetailsResponse
import com.dystopia.moviecatalogueapp.core.data.source.remote.response.MoviesItem
import com.dystopia.moviecatalogueapp.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MoviesItem>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            with(it) {
                val movie =
                    MovieEntity(
                        id,
                        title,
                        posterPath,
                        backdropPath,
                        releaseDate,
                        genres = "",
                        voteAverage,
                        runtime = 0,
                        overview,
                        tagline = "",
                        false
                    )
                movieList.add(movie)
            }
        }
        return movieList
    }

    fun mapResponsesToEntities(input: MovieDetailsResponse): MovieEntity {
        val listGenres = StringBuilder().append("")
        with(input) {
            for (genre in genres.indices) {
                if (genre < genres.size - 1) {
                    listGenres.append("${input.genres[genre].name}, ")
                } else {
                    listGenres.append(input.genres[genre].name)
                }
            }
        }
        with(input) {
            return MovieEntity(
                id,
                title,
                posterPath,
                backdropPath,
                releaseDate,
                listGenres.toString(),
                voteAverage,
                runtime,
                overview,
                tagline,
                false
            )
        }

    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            with(it) {
                Movie(
                    id,
                    title,
                    posterPath,
                    backdropPath,
                    releaseDate,
                    genres,
                    voteAverage,
                    runtime,
                    overview,
                    tagline,
                    isFavorite
                )
            }
        }

    fun mapEntitiesToDomain(input: MovieEntity): Movie =
        with(input) {
            Movie(
                id,
                title,
                posterPath,
                backdropPath,
                releaseDate,
                genres,
                voteAverage,
                runtime,
                overview,
                tagline,
                isFavorite
            )
        }

    fun mapDomainToEntity(input: Movie) =
        with(input) {
            MovieEntity(
                id,
                title,
                posterPath,
                backdropPath,
                releaseDate,
                genres,
                voteAverage,
                runtime,
                overview,
                tagline,
                isFavorite
            )
        }
}