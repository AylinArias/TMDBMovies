package com.example.tmdb.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tmdb.R
import com.example.tmdb.data.Movie
import com.example.tmdb.data.local.FavoriteMovie
import com.example.tmdb.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * FavoriteFragment se encarga de mostrar la lista de películas favoritas utilizando el adaptador FavoriteAdapter.
 * Cuando se hace clic en un elemento, se crea un objeto Movie a partir de los datos de FavoriteMovie y
 * se navega a la pantalla de detalles de la película utilizando la arquitectura de navegación.
 * */

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteBinding.bind(view)


        // Crear una instancia del adapter
        val adapter = FavoriteAdapter()

        // Observar los cambios en la lista de películas favoritas
        viewModel.movies.observe(viewLifecycleOwner) {
            // Actualizar la lista de películas en el adapter
            adapter.setMovieList(it)
            binding.apply {
                rvMovie.setHasFixedSize(true)
                rvMovie.adapter = adapter
            }
        }

        // Establece un callback para manejar los clics en los elementos del adapter
        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClick(favoriteMovie: FavoriteMovie) {
                // Crea un objeto de la clase Movie a partir de los datos de FavoriteMovie
                val movie = Movie(
                    favoriteMovie.id_movie,
                    favoriteMovie.overview,
                    favoriteMovie.poster_path,
                    favoriteMovie.title,
                    favoriteMovie.rating
                )
                // Crea una acción de navegación para mostrar los detalles de la película y navega a los detalles de la película
                val action = FavoriteFragmentDirections.actionNavFavoriteToNavDetails(movie)
                findNavController().navigate(action)
            }

        })
    }
}