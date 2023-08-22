package com.example.tmdb.di

import android.content.Context
import androidx.room.Room
import com.example.tmdb.api.MovieApi
import com.example.tmdb.data.local.FavoriteMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *  AppModule proporcionar instancias de componentes clave como la base de datos, el DAO, el cliente Retrofit y la interfaz de la API de películas a través de Dagger Hilt.
 * */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    // Esta función crea una instancia de la base de datos FavoriteMovieDatabase.
    // Utiliza Room.databaseBuilder para construir la base de datos con el nombre "movie_db".
    fun provideFavMovieDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FavoriteMovieDatabase::class.java,
        "movie_db"
    ).build()

    @Singleton
    @Provides
    // Esta función crea una instancia del DAO FavoriteMovieDao utilizando la instancia de la base de datos FavoriteMovieDatabase.
    fun provideFavMovieDao(db: FavoriteMovieDatabase) = db.getFavoriteMovieDao()

    @Provides
    @Singleton
    //Esta función crea una instancia del cliente Retrofit.
    // Configura Retrofit para usar la URL base de la API de películas y el convertidor JSON GsonConverterFactory.
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MovieApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    // Esta función crea una instancia de la interfaz MovieApi utilizando la instancia del cliente Retrofit.
    fun provideMovieApi(retrofit: Retrofit): MovieApi =
        retrofit.create(MovieApi::class.java)
}