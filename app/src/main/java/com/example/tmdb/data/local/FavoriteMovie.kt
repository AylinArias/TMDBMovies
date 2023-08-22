package com.example.tmdb.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


import java.io.Serializable

/**
 * FavoriteMovie representar detalles de películas favoritas.
 * La clase está diseñada para almacenar datos en una base de datos y pasar diferentes componentes con la implementación de Parcelable.
 * */

// Tabla llamada "favorite_movie" para almacenar y recuperar de una base de datos.
@Entity(tableName = "favorite_movie")
@Parcelize
data class FavoriteMovie(
    var id_movie: String,
    val title: String,
    val overview: String?,
    val poster_path: String,
    @SerializedName("vote_average")
    val rating: Double

) : Serializable, Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"

}