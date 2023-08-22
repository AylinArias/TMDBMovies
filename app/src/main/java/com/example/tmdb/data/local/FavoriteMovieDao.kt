package com.example.tmdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tmdb.api.MovieResponse
import retrofit2.http.GET

/**
 * FavoriteMovieDao interfaz que define una serie de métodos anotados con @Insert y @Query
 * que permite interactuar con la base de datos local para almacenar y recuperar películas favoritas.
 */

@Dao
interface FavoriteMovieDao {
    @Insert
    suspend fun addToFavorite(favoriteMovie: FavoriteMovie)

    // Selecciona todos los registros de la tabla "favorite_movie"
    @Query("SELECT * FROM favorite_movie")
    fun getFavoriteMovie(): LiveData<List<FavoriteMovie>>

    // Verifica si una película específica ya está en la lista de favoritos.
    @Query("SELECT count(*) FROM favorite_movie WHERE favorite_movie.id_movie = :id")
    suspend fun checkMovie(id: String): Int

    // Elimina los registros de la tabla "favorite_movie"
    @Query("DELETE FROM favorite_movie WHERE favorite_movie.id_movie = :id")
    suspend fun removeFromFavorite(id: String): Int

}