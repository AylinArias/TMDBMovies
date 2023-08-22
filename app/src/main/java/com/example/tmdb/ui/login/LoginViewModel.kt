package com.example.tmdb.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.usecase.FirebaseLoginUseCase
import com.example.tmdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * LoginViewModel se utiliza para gestionar el estado y la comunicación entre la lógica de la aplicación y la interfaz de usuario.
 * */

@HiltViewModel
class LoginViewModel @Inject constructor(
    // loginUseCase que contiene una instancia de FirebaseLoginUseCase para manejar la lógica del proceso de registro en la aplicación.
    private val loginUseCase: FirebaseLoginUseCase
) :  ViewModel() {

    // Se declara _loginState es un LiveData que almacenará el estado del proceso de inicio de sesison.
    private val _loginState: MutableLiveData<Resource<Boolean>> = MutableLiveData()
    // loginState  expondrá el estado del proceso de registro al Observador
    val loginState: LiveData<Resource<Boolean>>
        get() = _loginState

    // La fun login se llama desde la interfaz de usuario para iniciar el proceso de inicio de sesión
    fun login(email: String, password: String) {
       //Se llama a loginUseCase con el correo electrónico y la contraseña proporcionados.
        viewModelScope.launch {
            loginUseCase(email, password).onEach { state ->
                _loginState.value = state
            }.launchIn(viewModelScope)
        }
    }

}