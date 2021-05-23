package com.dystopia.moviecatalogueapp.core.domain.repository

import com.dystopia.moviecatalogueapp.core.data.Resource
import com.dystopia.moviecatalogueapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getMovieById(id: Int): Flow<Resource<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun setFavoriteMovie(movie: Movie, state: Boolean)
}