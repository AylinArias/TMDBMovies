package com.example.tmdb.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * Movie representa los detalles de una película.
 * La anotación @Parcelize permite que los objetos de esta clase comunicar con activities o fragments.
 */

@Parcelize
data class Movie(
    val id: String,
    val overview: String?,
    val poster_path: String,
    val title: String,
    @SerializedName("vote_average")
    val rating: Double
) : Parcelable {
    val baseUrl get() = "https://image.tmdb.org/t/p/w500"

}