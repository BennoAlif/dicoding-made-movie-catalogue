package com.dystopia.moviecatalogueapp.core.domain.usecase

import com.dystopia.moviecatalogueapp.core.data.Resource
import com.dystopia.moviecatalogueapp.core.domain.model.Movie
import com.dystopia.moviecatalogueapp.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {
    override fun getAllMovie(): Flow<Resource<List<Movie>>> = movieRepository.getAllMovies()

    override fun getMovieById(id: Int): Flow<Resource<Movie>> = movieRepository.getMovieById(id)

    override fun getFavoriteMovies(): Flow<List<Movie>> = movieRepository.getFavoriteMovies()

    override fun setFavoriteMovie(movie: Movie, state: Boolean) = movieRepository.setFavoriteMovie(movie, state)
}