package com.dystopia.moviecatalogueapp.core.data

import com.dystopia.moviecatalogueapp.core.data.source.local.LocalDataSource
import com.dystopia.moviecatalogueapp.core.data.source.remote.RemoteDataSource
import com.dystopia.moviecatalogueapp.core.data.source.remote.network.ApiResponse
import com.dystopia.moviecatalogueapp.core.data.source.remote.response.MovieDetailsResponse
import com.dystopia.moviecatalogueapp.core.data.source.remote.response.MoviesItem
import com.dystopia.moviecatalogueapp.core.domain.model.Movie
import com.dystopia.moviecatalogueapp.core.domain.repository.IMovieRepository
import com.dystopia.moviecatalogueapp.core.utils.AppExecutors
import com.dystopia.moviecatalogueapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {
    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MoviesItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<MoviesItem>>> =
                remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: List<MoviesItem>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()


    override fun getMovieById(id: Int): Flow<Resource<Movie>> =
        object : NetworkBoundResource<Movie, MovieDetailsResponse>() {
            override fun loadFromDB(): Flow<Movie> {
                return localDataSource.getMovieById(id).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: Movie?): Boolean = data == null || data.genres == ""

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailsResponse>> =
                remoteDataSource.getMovieDetails(id.toString())

            override suspend fun saveCallResult(data: MovieDetailsResponse) {
                val movie = DataMapper.mapResponsesToEntities(data)
                localDataSource.updateMovie(movie, false)
            }

        }.asFlow()

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteMovie(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movieEntity, state) }
    }
}