package com.example.bustrackingapp.presentation.screens.auth.sign_in

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.common.InputValidator
import com.example.bustrackingapp.common.NetworkResult
import com.example.bustrackingapp.common.loggerTag
import com.example.bustrackingapp.domain.model.request.SignInUserRequestBody
import com.example.bustrackingapp.domain.repository.AuthRepository
import com.example.bustrackingapp.domain.repository.UserPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPrefsRepository: UserPrefsRepository
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
                    val result = authRepository.signInUser(
                        SignInUserRequestBody(
                            email = uiState.emailInput,
                            password = uiState.passwordInput
                        )
                    )
                    Log.d(loggerTag, "NetworkResult : $result")
                    when(result){
                        is NetworkResult.Success -> {
                            val token = result.data?.token;
                            if(token!=null){
                                userPrefsRepository.updateToken(token)
                                uiState.copy(isSignedIn = true, errorSignIn = null, isLoading = false)
                            }else{
                                uiState.copy(isSignedIn = false, errorSignIn = "Something Went Wrong!", isLoading = false)
                            }
                        }
                        is NetworkResult.Error -> {
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