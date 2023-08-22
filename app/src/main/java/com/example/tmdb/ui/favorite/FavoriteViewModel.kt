package com.example.tmdb.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.tmdb.data.local.FavoriteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * FavoriteViewModel se utiliza para proporcionar acceso a la lista de películas favoritas a la vista
 * */

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    // Se inyecta una instancia del repositorio FavoriteMovieRepository en el ViewModel a través del constructor.
    private val repository: FavoriteMovieRepository,
) : ViewModel() {
    // Crea un LiveData llamado movies que contiene la lista de películas favoritas.
    val movies = repository.getFavoriteMovies()
}