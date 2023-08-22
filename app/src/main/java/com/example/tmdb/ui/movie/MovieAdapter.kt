package com.example.tmdb.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tmdb.R
import com.example.tmdb.data.Movie
import com.example.tmdb.databinding.ItemMovieBinding

/**
 * MovieAdapter maneja la carga de elementos paginados en un RecyclerView y permite la interacción con los elementos
 * mediante el uso de un callback (OnItemClickListener).
 */


// Se utiliza para cargar y mostrar elementos paginados en un RecyclerView.
// El constructor recibe dos argumentos: el tipo de datos (Movie) y el tipo de ViewHolder (MovieViewHolder)
// Y también se proporciona un objeto comparador (COMPARATOR) para determinar si los elementos son iguales.

class MovieAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(COMPARATOR) {
    // Se llama cuando es necesario enlazar datos a un ViewHolder
    // Toma el elemento actual (currentItem) y llama al método bind del ViewHolder para enlazar los datos a la vista.
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    // Método para crear un nuevo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    // Define el ViewHolder para los elementos del adaptador.
    // El constructor toma un objeto de enlace (ItemMovieBinding) que representa la vista de cada elemento en la lista.
    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            // Establece un clic en el elemento
            // Cuando se hace clic en un elemento, se obtiene la posición y se llama al método onItemClick del callback listener.
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        // Enlaza los datos de una película al ViewHolder utilizando la biblioteca Glide para cargar la imagen del póster en la vista.
        fun bind(movie: Movie) {
            with(binding) {
                Glide.with(itemView)
                    .load("${movie.baseUrl}${movie.poster_path}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivMoviePoster)
            }
        }
    }


    // Interfaz para manejar clics en los elementos del adaptador
    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    companion object {
        // Comparador para verificar si los elementos son iguales
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }

}