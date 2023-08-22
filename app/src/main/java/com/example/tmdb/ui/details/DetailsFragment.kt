package com.example.tmdb.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentDetailsBinding
import com.example.tmdb.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * DetailsFragment muestra los detalles de una película, su imagen, título, descripción y calificación.
 * También incluye los metodoss para agregar o quitar la película de la lista de favoritos.
 * */

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    // Utiliza los argumentos pasados al fragmento mediante la navegación
    private val args by navArgs<DetailsFragmentArgs>()

    // Permite que el fragmento obtenga una instancia compartida del ViewModel
    private val viewModel by viewModels<DetailsMovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Enlaza el diseño del fragmento con su clase de enlace correspondiente
        val binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            // Se accede al objeto de la película desde los argumentos de navegación
            val movie = args.movie
            // Se carga la imagen del póster de la película usando Glide
            Glide.with(this@DetailsFragment)
                .load("${movie.baseUrl}${movie.poster_path}")
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        movieDescription.isVisible = true
                        movieTitle.isVisible = true
                        movieRating.isVisible = true
                        return false
                    }

                })
                .into(ivMoviePoster)
            var _isChecked = false
            // CoroutineScope para realizar una consulta de base de datos
            CoroutineScope(Dispatchers.IO).launch {
                // Comprobar si la película ya está en favoritos
                val count = viewModel.checkMovie(movie.id)
                withContext(Dispatchers.Main) {
                    if (count > 0) {
                        toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }

            // Establecer los detalles de la película en la interfaz de usuario
            movieDescription.text = movie.overview
            movieRating.text = movie.rating.toString()
            movieTitle.text = movie.title

            toggleFavorite.setOnClickListener {
                _isChecked = !_isChecked
                if (_isChecked) {
                    viewModel.addToFavorite(movie)
                } else {
                    viewModel.removeFromFavorite(movie.id)
                }
                toggleFavorite.isChecked = _isChecked
            }
        }
    }

}