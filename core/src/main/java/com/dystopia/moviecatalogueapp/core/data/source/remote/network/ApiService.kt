package com.dystopia.moviecatalogueapp.core.data.source.remote.network

import com.dystopia.moviecatalogueapp.core.data.source.remote.response.MovieDetailsResponse
import com.dystopia.moviecatalogueapp.core.data.source.remote.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): MoviesResponse

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: String,
        @Query("api_key") apiKey: String
    ): MovieDetailsResponse
}