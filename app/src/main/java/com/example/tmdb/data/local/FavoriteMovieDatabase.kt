package com.example.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * FavoriteMovieDatabase esta clase se utiliza para definir la base de datos local usando la biblioteca Room en Android.
 */

@Database(
    entities = [FavoriteMovie::class],
    version = 1
)

// Se define un método abstracto para obtener una instancia de FavoriteMovieDao, que se utiliza para interactuar con la base de datos
abstract class FavoriteMovieDatabase : RoomDatabase() {
    abstract fun getFavoriteMovieDao(): FavoriteMovieDao
}