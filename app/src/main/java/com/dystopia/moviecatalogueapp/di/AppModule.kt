package com.dystopia.moviecatalogueapp.di

import com.dystopia.moviecatalogueapp.core.domain.usecase.MovieInteractor
import com.dystopia.moviecatalogueapp.core.domain.usecase.MovieUseCase
import com.dystopia.moviecatalogueapp.ui.detail.DetailsViewModel
import com.dystopia.moviecatalogueapp.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}