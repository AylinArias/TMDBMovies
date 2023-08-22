package com.example.tmdb.domain.usecase


import com.example.tmdb.domain.repository.AuthRepository
import com.example.tmdb.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 * FirebaseLoginUseCase encapsula la lógica para el proceso de inicio de sesion en Firebase utilizando coroutines y Flow.
 * */

class FirebaseLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    // La función acepta el correo electrónico y la contraseña como argumentos y devuelve un flujo (Flow) de estados Resource<Boolean>.
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Boolean>> = flow {
        // se emite un estado de carga para indicar que el proceso de inicio de sesión está en curso
        emit(Resource.Loading)
        // Llama al método login en la dependencia authRepository para realizar el proceso de inicio de sesión.
        val loggedSuccessfully = authRepository.login(email, password)
        // Se verifica si el inicio de sesión fue exitoso. Si es exitoso, emite un estado de éxito
        if (loggedSuccessfully) {
            emit(Resource.Success(true))
        } else {
            // Si no es exitoso, emite un estado de error
            emit(Resource.Error("Login error"))
        }
    }
}

