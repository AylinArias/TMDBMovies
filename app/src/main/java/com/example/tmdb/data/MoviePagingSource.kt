package com.example.tmdb.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tmdb.api.MovieApi
import retrofit2.HttpException
import java.io.IOException


/**
 * MoviePagingSource se utiliza para cargar los datos paginados desde una fuente de datos remota utilizando la biblioteca Paging 3.
 */

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val query: String?
) : PagingSource<Int, Movie>() {
    // Toma un objeto como argumento y devuelve aun resultado de carga
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        // Este método intenta cargar los datos de la página actual utilizando movieApi y dependiendo de si query está presente o no,
        // se llama a searchMovies o getMovies para obtener los resultados.
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response =
                if (query != null) movieApi.searchMovies(query, position) else movieApi.getMovies(
                    position
                )
            val movies = response.results

            // Se pasan los datos de las películas obtenidas, junto con las claves de página previa (prevKey) y siguiente (nextKey).
            // Si la position es igual al índice de página inicial (STARTING_PAGE_INDEX), se establece prevKey en null.
            LoadResult.Page(
                data = movies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val refreshKey = state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey
        }

        return if (refreshKey == null) null else refreshKey + 1
    }
}