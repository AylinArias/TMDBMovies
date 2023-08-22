package com.example.tmdb.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentLoginBinding
import com.example.tmdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint


/**
 * LoginFragment maneja la interfaz de usuario relacionada con la función de inicio de sesión en la aplicación.
 * */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Método que se llama después de que la vista se haya creado e inicializar los observadores y los listeners.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                // Si el estado es un Resource.Success, significa que el proceso de inicio de sesión fue exitoso
                is Resource.Success -> {
                    // se llama al handleLoading para detener la visualización de la carga y luego navegar al nav_movie
                    handleLoading(isLoading = false)
                    findNavController().navigate(R.id.action_loginFragment_to_nav_movie)
                }
                // Si el estado es un Resource.Error, significa que ocurrió un error durante el proceso de inicio de sesión.
                is Resource.Error -> {
                    // Se llama al handleLoading para detener la visualización de la carga
                    // y muestras un Toast con el mensaje de error obtenido del estado.
                    handleLoading(isLoading = false)
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // Si el estado es un Resource.Loading, significa que el proceso de inicio de sesión está en curso.
                // Se llama al handleLoading con isLoading = true para mostrar una barra de progreso y deshabilitar el botón de inicio de sesión mientras se realiza el proceso.
                is Resource.Loading -> handleLoading(isLoading = true)
                else -> Unit
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            // Cuando se presione el boton login, se llama a la función handleLogin().
            btnLogin.setOnClickListener { handleLogin() }
            // Cuando se presione el boton signUp, se navega al signUpFragment
            btnSignUp.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_signUpFragment) }
        }
    }


    // Se encarga de recopilar el correo electrónico y la contraseña ingresados por el usuario en los campos de texto
    // y luego llamar al método login del ViewModel para iniciar el proceso de inicio de sesión.
    private fun handleLogin() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.login(email, password)

    }

    // Se encarga de cambiar la interfaz de usuario para mostrar una barra de progreso y deshabilitar el botón de lgin durante el proceso de carga.
    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                btnLogin.text = ""
                btnLogin.isEnabled = false
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
                btnLogin.text = getString(R.string.iniciar_sesion)
                btnLogin.isEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
