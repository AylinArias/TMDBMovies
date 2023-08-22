package com.example.tmdb.ui.movie


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.tmdb.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * MovieViewModel maneja la lógica para obtener y mostrar películas en el fragmento MovieFragment.
 * Utiliza el SavedStateHandle para mantener el estado de la consulta actual y cambia entre la obtención de películas de búsqueda y
 * películas normales según la consulta. La función searchMovies se utiliza para realizar búsquedas y actualizar la consulta actual.
 * */


@HiltViewModel
// Define la clase MovieViewModel que extiende la clase base ViewModel. Se inyectan el repositorio MovieRepository y el SavedStateHandle en el constructor.
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    companion object {
        // Define dos constantes que se utilizan para manejar el estado de la consulta actual.
        private const val CURRENT_QUERY = "current_query"
        private const val EMPTY_QUERY = ""
    }

    // Obtiene el valor actual de la consulta desde SavedStateHandle
    private val currentQuery = state.getLiveData(CURRENT_QUERY, EMPTY_QUERY)

    // Se tiliza el operador switchMap para observar cambios en el LiveData currentQuery.
    // Cuando cambia la consulta actual, se decide si obtener películas de búsqueda o películas normales utilizando el repositorio.
    val movies = currentQuery.switchMap { query ->
        // Si la consulta actual no está vacía, se llama a repository.getSearchMovies(query).cachedIn(viewModelScope).
        // Esto obtiene las películas de búsqueda utilizando el método getSearchMovies del repositorio.
        // cachedIn(viewModelScope) indica que los resultados de la consulta se cachearán en el ámbito del ViewModel.
        if (query.isNotEmpty()) {
            repository.getSearchMovies(query).cachedIn(viewModelScope)
        } else {
            // Si la consulta actual está vacía, se llama a repository.getMovies().cachedIn(viewModelScope) para obtener las películas normales utilizando el método getMovies del repositorio.
            repository.getMovies().cachedIn(viewModelScope)
        }
    }

    //  Este método se utiliza para realizar una búsqueda de películas.
    fun searchMovies(query: String) {
        currentQuery.value = query
    }
}