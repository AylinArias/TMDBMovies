package com.example.tmdb.api

import retrofit2.http.GET
import retrofit2.http.Query

/***
 * MovieApi proporciona métodos para interactuar con una API de películas. Los métodos definidos permiten obtener una lista de películas  y buscar películas.
 */


interface MovieApi {

    // Se define una constante que almacena la url de la api de películas
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    @GET("movie/now_playing?api_key=8e7c63f8132a379eeed49f3f7ac21e08")
    /* Endpoint de la API. La función suspend obtiene la lista de películas, recibe el parámetro position que indica la pagina de resultados y
       devuelve el objeto movieResponse que encapsula la respuesta de la API */
    suspend fun getMovies(
        @Query("page") position: Int
    ): MovieResponse

    @GET("search/movie?api_key=8e7c63f8132a379eeed49f3f7ac21e08")
    /* La función suspend busca películas según el término de búsqueda (query) y un número de página (page) y
       devuelve el objeto MovieResponse que contendría los resultados de la búsqueda.*/
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse
}