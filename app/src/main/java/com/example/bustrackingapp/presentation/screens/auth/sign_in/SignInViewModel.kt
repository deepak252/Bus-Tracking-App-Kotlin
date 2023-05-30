package com.example.bustrackingapp.presentation.screens.auth.sign_in

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.common.InputValidator
import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.domain.model.request.SignInUserRequestBody
import com.example.bustrackingapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel(){

    var uiState by mutableStateOf(SignInUiState())
        private set

    fun onEmailInputChange(newVal : String){
        uiState = uiState.copy(
            emailInput = newVal,
            errorEmailInput = InputValidator.validateEmail(newVal)
        )
    }

    fun onPasswordInputChange(newVal : String){
        val trimmedVal = newVal.filter { it!=' '}
        uiState = uiState.copy(
            passwordInput = trimmedVal,
            errorPasswordInput = InputValidator.validatePassword(trimmedVal)
        )
    }

    fun onTogglePasswordVisibility(){
        uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
    }

    fun onSignInClick(){
        if(validateSignIn()){
            uiState = uiState.copy(isLoading = true, errorSignIn = null)
            viewModelScope.launch {
                uiState = try{
                    delay(1000)
                    val result = authRepository.signInUser(
                        SignInUserRequestBody(
                            email = uiState.emailInput,
                            password = uiState.passwordInput
                        )
                    )
                    when(result){
                        is NetworkResult.Success -> {
                            Log.d("Logger", "NetworkResult.Success : $result")
                            uiState.copy(isSignedIn = true, errorSignIn = null, isLoading = false)
                        }
                        is NetworkResult.Error -> {
                            Log.d("Logger", "NetworkResult.Error : $result")
                            uiState.copy(errorSignIn = result.message, isLoading = false)
                        }
                    }

                }catch (e : Exception){
                    uiState.copy(errorSignIn = e.message, isLoading = false)
                }
            }
        }
    }

    private fun validateSignIn() : Boolean{
        uiState = uiState.copy(
            errorEmailInput = InputValidator.validateEmail(uiState.emailInput),
            errorPasswordInput = InputValidator.validatePassword(uiState.passwordInput),
        )
        return uiState.errorEmailInput==null && uiState.errorPasswordInput==null

    }

}