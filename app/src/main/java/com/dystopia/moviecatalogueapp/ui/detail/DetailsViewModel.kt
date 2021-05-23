package com.dystopia.moviecatalogueapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dystopia.moviecatalogueapp.core.data.Resource
import com.dystopia.moviecatalogueapp.core.domain.model.Movie
import com.dystopia.moviecatalogueapp.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.Flow

class DetailsViewModel(private val movieUseCase: MovieUseCase) : ViewModel() {

    private lateinit var movieDetails: Flow<Resource<Movie>>

    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) = movieUseCase.setFavoriteMovie(movie, newStatus)

    fun setMovieDetails(id: String) {
        movieDetails = movieUseCase.getMovieById(id.toInt())
    }

    fun getMovieDetails() = movieDetails.asLiveData()
}