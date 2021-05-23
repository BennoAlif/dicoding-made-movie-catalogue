package com.dystopia.moviecatalogueapp.core.data.source.remote

import android.util.Log
import com.dystopia.moviecatalogueapp.core.data.source.remote.network.ApiInfo.API_KEY
import com.dystopia.moviecatalogueapp.core.data.source.remote.network.ApiResponse
import com.dystopia.moviecatalogueapp.core.data.source.remote.network.ApiService
import com.dystopia.moviecatalogueapp.core.data.source.remote.response.MovieDetailsResponse
import com.dystopia.moviecatalogueapp.core.data.source.remote.response.MoviesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovies(): Flow<ApiResponse<List<MoviesItem>>> {
        return flow {
            try {
                val res = apiService.getMovies(API_KEY)
                val dataArray = res.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(res.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetails(id: String): Flow<ApiResponse<MovieDetailsResponse>> {
        return flow {
            try {
                val res = apiService.getMovieDetails(id, API_KEY)
                emit(ApiResponse.Success(res))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}