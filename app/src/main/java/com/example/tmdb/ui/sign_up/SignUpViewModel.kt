package com.example.tmdb.ui.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdb.domain.usecase.FirebaseSignUpUseCase
import com.example.tmdb.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * FirebaseSignUpUseCase se utiliza para llevar a cabo el proceso de registro utilizando Firebase.
 * */

@HiltViewModel
class SignUpViewModel @Inject constructor(
    // Se declara signUpUseCase que contiene una instancia de FirebaseSignUpUseCase para manejar la lógica del proceso de registro en la aplicación.
    private val signUpUseCase: FirebaseSignUpUseCase
) : ViewModel() {

    // Se declara _signUpState es un LiveData que almacenará el estado del proceso de registro.
    private val _signUpState: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    // signUpState  expondrá el estado del proceso de registro al Observador
    val signUpState: LiveData<Resource<Boolean>>
        get() = _signUpState

    // La función signUp se llama desde la interfaz de usuario para iniciar el proceso de registro.
    fun signUp(email: String, password: String) {
        // viewModelScope para se cancele automáticamente cuando el ViewModel se desactive o se borre.
        viewModelScope.launch {
            // signUpUseCase realiza el proceso de registro utilizando el correo electrónico y la contraseña
            // proporcionados. El resultado es un flujo de datos (Flow) de objetos de estado Resource<Boolean>.
            signUpUseCase(email, password).onEach { state ->
                // Se actualiza el valor del LiveData _signUpState con el estado actual.
                _signUpState.value = state
                // launchIn se encarga de lanzar el flujo en el ámbito viewModelScope y manejar la recolección
                // de resultados en el mismo ámbito.
            }.launchIn(viewModelScope)
        }
    }

}