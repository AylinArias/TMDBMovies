package com.example.tmdb.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.tmdb.api.MovieApi
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MovieRepository se utiliza para proporcionar métodos para obtener películas de la API.
 * */

@Singleton
class MovieRepository @Inject constructor(private val movieApi: MovieApi) {

    // Este método obtiene las películas de la API de forma paginada
    fun getMovies() =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi, null) }
        ).liveData

    fun getSearchMovies(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi, query) }
        ).liveData

}