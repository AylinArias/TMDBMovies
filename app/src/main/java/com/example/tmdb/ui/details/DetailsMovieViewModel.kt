package com.example.tmdb.ui.details

import androidx.lifecycle.ViewModel
import com.example.tmdb.data.Movie
import com.example.tmdb.data.local.FavoriteMovie
import com.example.tmdb.data.local.FavoriteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * DetailsMovieViewModel maneja la lógica relacionada con la visualización de detalles de películas y su interacción con la lista de favoritos.
 * Utiliza el repositorio FavoriteMovieRepository para realizar operaciones en la base de datos.
 * Las funciones suspendidas y los CoroutineScope se utilizan para realizar operaciones en segundo plano de manera adecuada y segura.
 * */

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(
    private val repository: FavoriteMovieRepository
) : ViewModel() {

    // Este método se encarga de agregar una película a la lista de favoritos.
    fun addToFavorite(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteMovie(
                    movie.id,
                    movie.title,
                    movie.overview,
                    movie.poster_path,
                    movie.rating
                )
            )
        }
    }

    // Este método verifica si una película con el ID proporcionado ya está en la lista de favoritos.
    suspend fun checkMovie(id: String) = repository.checkMovie(id)

    // Este método se encarga de quitar una película de la lista de favoritos.
    fun removeFromFavorite(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }

}