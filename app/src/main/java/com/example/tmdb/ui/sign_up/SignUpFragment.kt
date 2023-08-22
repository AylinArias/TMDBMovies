package com.example.tmdb.ui.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tmdb.R
import com.example.tmdb.databinding.FragmentSignUpBinding
import com.example.tmdb.util.Resource
import dagger.hilt.android.AndroidEntryPoint

/**
 * SignUpFragment implementa la funcionalidad de registro de la aplicación. Utiliza ViewModel y LiveData para gestionar el estado y la lógica,
 * y Hilt para la inyección de dependencias
 * */


@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    // Método que se llama después de que la vista se haya creado e inicializar los observadores y los listeners.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when (state) {
                //Si el estado es un Resource.Error el registro fue exitoso.
                is Resource.Success -> {
                    // Se llama al handleLoading para detener la visualización de la carga
                    // y muetra un Toast que indica que el registro fue exitoso.
                    handleLoading(isLoading = false)
                    Toast.makeText(
                        requireContext(),
                        "Sign up success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Si el estado es un Resource.Error, significa que ocurrió un error.
                is Resource.Error -> {
                    // Se llama a handleLoading para detener la visualización de la carga
                    // y muestras un Toast con el mensaje de error obtenido del estado.
                    handleLoading(isLoading = false)
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                // Si el estado es un Resource.Loading, significa que el proceso de registro está en curso.
                // Se llama al handleLoading con isLoading = true para mostrar una barra de progreso
                // y deshabilitar el botón de registro mientras se realiza el proceso.
                is Resource.Loading -> handleLoading(isLoading = true)
                else -> Unit
            }
        }
    }


    // Se encarga de configurar un listener en el botón de registro para que cuando el usuario haga clic en él,
    // se active la función handleSignUp() y se inicie el proceso de registro.
    private fun initListeners() {
        with(binding) {
            bttnSignUp.setOnClickListener {
                handleSignUp()
            }
        }
    }

    // Se encarga de recopilar los datos ingresados por el usuario en los campos de texto y
    // luego llamar al ViewModel para que inicie el proceso de registro utilizando esos datos.
    private fun handleSignUp() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.signUp(email, password)
    }

    // Se encarga de cambiar la interfaz de usuario para mostrar una barra de progreso y deshabilitar el botón de registro durante el proceso de carga.
    private fun handleLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                bttnSignUp.text = ""
                bttnSignUp.isEnabled = false
                pbSignUp.visibility = View.VISIBLE
            } else {
                pbSignUp.visibility = View.GONE
                bttnSignUp.text = getString(R.string.registrarme)
                bttnSignUp.isEnabled = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}