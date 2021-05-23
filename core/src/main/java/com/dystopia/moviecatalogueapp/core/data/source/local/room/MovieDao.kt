package com.dystopia.moviecatalogueapp.core.data.source.local.room

import androidx.room.*
import com.dystopia.moviecatalogueapp.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie_entities")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entities WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Update
    fun updateFavoriteMovie(movie: MovieEntity)
}