package com.example.tmdb.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.databinding.MovieLoadStateFooterBinding

/**
 * MovieLoadStateAdapter se utiliza para mostrar el estado de carga en el encabezado o el pie del RecyclerView
 * mientras se cargan más elementos paginados. Los estados de carga son visibles cuando se están cargando datos
 * o cuando ocurrió un error, y el botón de reintento permite volver a cargar los datos en caso de error.
 * */

class MovieLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovieLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            MovieLoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    // Vincula los datos del estado de carga al ViewHolder
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    // Define la clase LoadStateViewHolder que hereda de RecyclerView.ViewHolder.
    // Se maneja el estado de carga y se establece el comportamiento del botón de reintento.
    inner class LoadStateViewHolder(private val binding: MovieLoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Se configurar el botón de reintento
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        // Vincula los datos del estado de carga al ViewHolder.
        // Dependiendo del estado de carga, se muestra o se oculta la barra de progreso, el botón de reintento y el mensaje de error.
        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                tvError.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}