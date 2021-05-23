package com.dystopia.moviecatalogueapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dystopia.moviecatalogueapp.core.domain.usecase.MovieUseCase

class FavoriteViewModel(movieUseCase: MovieUseCase): ViewModel() {
    val favoriteMovies = movieUseCase.getFavoriteMovies().asLiveData()
}