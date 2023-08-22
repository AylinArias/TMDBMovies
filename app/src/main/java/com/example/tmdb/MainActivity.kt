package com.example.tmdb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tmdb.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity configura la navegación entre fragmentos utilizando el controlador de navegación y la vista de navegación inferior.
 * */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        bottomNavigationView = findViewById(R.id.nav_bottom)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_movie, R.id.nav_favorite
        ).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        // Agregar un listener para controlar la visibilidad del BottomNavigationView
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_movie, R.id.nav_favorite -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }

                else -> {
                    bottomNavigationView.visibility = View.GONE
                }
            }
        }

        // Maneja la navegación entre los fragmentos utilizando los elementos de menú en la vista de navegación inferior.
        binding.apply {
            navBottom.setupWithNavController(navController)
        }

    }

    // Sobrescribe el método onSupportNavigateUp para manejar la navegación hacia atrás.
    // Devuelve true si la navegación fue manejada por el controlador de navegación, de lo contrario, llama al método de la superclase onSupportNavigateUp.
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}