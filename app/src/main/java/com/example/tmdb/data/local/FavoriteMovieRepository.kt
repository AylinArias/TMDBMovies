package com.example.tmdb.data.local

import javax.inject.Inject

/**
 * FavoriteMovieRepository esat clase actúa como intermediario con la base de datos.
 * */

class FavoriteMovieRepository @Inject constructor(
    // Se declara una propiedad que permite que el repositorio tenga acceso a los métodos definidos
    private val favoriteMovieDao: FavoriteMovieDao
) {
    // Este método agrega una película a la lista de favoritos en la base de datos
    suspend fun addToFavorite(favoriteMovie: FavoriteMovie) =
        favoriteMovieDao.addToFavorite(favoriteMovie)

    // Método para obtener la lista de películas favoritas de la base de datos
    fun getFavoriteMovies() = favoriteMovieDao.getFavoriteMovie()

    // Este método verificar si una película ya está en la lista de favoritos en la base de datos
    suspend fun checkMovie(id: String) = favoriteMovieDao.checkMovie(id)

    // Este método elimina una película de la lista de favoritos en la base de datos
    suspend fun removeFromFavorite(id: String) {
        favoriteMovieDao.removeFromFavorite(id)
    }

}