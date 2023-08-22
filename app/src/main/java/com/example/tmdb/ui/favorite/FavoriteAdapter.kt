package com.example.tmdb.ui.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tmdb.R
import com.example.tmdb.data.local.FavoriteMovie
import com.example.tmdb.databinding.ItemMovieBinding

/**
 * FavoriteAdapter es un adapter de RecyclerView que se encarga de enlazar datos de películas favoritas a los elementos de la vista.
 * */

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    // Almacena la lista de películas favoritas que se mostrarán en el adapter.
    private lateinit var list: List<FavoriteMovie>

    private var onItemClickCallback: OnItemClickCallback? = null

    // Establece el callback para manejar los clics en los elementos del adapter.
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // Actualiza la lista de películas favoritas en el adapter.
    fun setMovieList(list: List<FavoriteMovie>) {
        this.list = list
        notifyDataSetChanged()
    }


    // Define el ViewHolder para los elementos del adapter.
    // El constructor toma un objeto de enlace (ItemMovieBinding) par representar la vista de cada elemento en la lista.
    inner class FavoriteViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Enlaza los datos de una película favorita a la vista del ViewHolder.
        // Utiliza la biblioteca Glide para cargar la imagen del póster y establece un clic en el elemento para llamar al callback.
        fun bind(favoriteMovie: FavoriteMovie) {
            with(binding) {
                Glide.with(itemView)
                    .load("${favoriteMovie.baseUrl}${favoriteMovie.poster_path}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivMoviePoster)
                binding.root.setOnClickListener { onItemClickCallback?.onItemClick(favoriteMovie) }
            }
        }

    }

    // Crea un nuevo ViewHolder utilizando el LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    // Devuelve la cantidad de elementos en la lista.
    override fun getItemCount(): Int = list.size

    //Enlaza los datos de una película favorita a un ViewHolder existente llamando al método bind en el ViewHolder.
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        Log.e("adapter", " view holder")
        holder.bind(list[position])
    }

    interface OnItemClickCallback {
        // Método que se llamará cuando un elemento se haga clic.
        fun onItemClick(favoriteMovie: FavoriteMovie)
    }
}