package com.example.tmdb.api

import com.example.tmdb.data.Movie

/**
 *  MovieResponse representa la respuesta de la solicitud de la API de películas.
 *  Su propiedad results almacena una lista de objetos de tipo Movie, que son los resultados obtenidos de la solicitud.
 */

data class MovieResponse(val results: List<Movie>)