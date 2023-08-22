package com.example.tmdb.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.paging.LoadState
import com.example.tmdb.R
import com.example.tmdb.data.Movie
import com.example.tmdb.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * MovieFragment se encarga de mostrar la lista paginada de películas en un RecyclerView.
 * También maneja la funcionalidad de búsqueda a través de un menú en la barra de acciones.
 * */

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnItemClickListener {

    private val viewModel by viewModels<MovieViewModel>()
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Enlaza el diseño del fragmento
        _binding = FragmentMovieBinding.bind(view)


        val adapter = MovieAdapter(this)

        binding.apply {
            rvMovie.setHasFixedSize(true)
            // Configurar el adaptador con LoadStateHeader y LoadStateFooter
            rvMovie.adapter = adapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { adapter.retry() },
                footer = MovieLoadStateAdapter { adapter.retry() }
            )
            btnTryAgain.setOnClickListener {
                adapter.retry()
            }
        }

        // Observa los datos paginados del ViewModel
        viewModel.movies.observe(viewLifecycleOwner) {
            // Envia los datos paginados al adapter
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        // Observa el estado de carga del adapter
        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnTryAgain.isVisible = loadState.source.refresh is LoadState.Error
                tvFailed.isVisible = loadState.source.refresh is LoadState.Error

                //not found
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvMovie.isVisible = false
                    tvNotFound.isVisible = true
                } else {
                    tvNotFound.isVisible = false
                }
            }
        }

        // Habilita las opciones del menú en la barra de acciones
        setHasOptionsMenu(true)
    }

    override fun onItemClick(movie: Movie) {
        val action = MovieFragmentDirections.actionNavMovieToNavDetails(movie)
        findNavController().navigate(action)
    }


    // Crear el menú de búsqueda en la barra de acciones
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // Realiza lla búsqueda y muestra los resultados
                    binding.rvMovie.scrollToPosition(0)
                    viewModel.searchMovies(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
}