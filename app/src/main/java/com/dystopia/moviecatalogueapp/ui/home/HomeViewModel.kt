package com.dystopia.moviecatalogueapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dystopia.moviecatalogueapp.core.domain.usecase.MovieUseCase

class HomeViewModel(movieUseCase: MovieUseCase) : ViewModel() {
    val movies = movieUseCase.getAllMovie().asLiveData()
}