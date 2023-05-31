package com.example.bustrackingapp.presentation.screens.auth.sign_up

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
import com.example.bustrackingapp.domain.model.request.SignUpUserRequestBody
import com.example.bustrackingapp.domain.repository.AuthRepository
import com.example.bustrackingapp.domain.repository.UserPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPrefsRepository: UserPrefsRepository
) : ViewModel(){

    var uiState by mutableStateOf(SignUpUiState())
        private set

    fun onNameInputChange(newVal : String){
        uiState = uiState.copy(
            nameInput = newVal,
            errorNameInput = InputValidator.validateName(newVal)
        )
    }

    fun onEmailInputChange(newVal : String){
        uiState = uiState.copy(
            emailInput = newVal,
            errorEmailInput = InputValidator.validateEmail(newVal)
        )
    }
    fun onPhoneInputChange(newVal : String){
        uiState = uiState.copy(
            phoneInput = newVal,
            errorPhoneInput = InputValidator.validatePhone(newVal)
        )
    }

    fun onPasswordInputChange(newVal : String){
        val trimmedVal = newVal.filter { it!=' '}
        uiState = uiState.copy(
            passwordInput = trimmedVal,
            errorPasswordInput = InputValidator.validatePassword(trimmedVal),
        )
    }

    fun onCfPasswordInputChange(newVal : String){
        val trimmedVal = newVal.filter { it!=' '}
        uiState = uiState.copy(
            cfPasswordInput = trimmedVal,
            errorCfPasswordInput = InputValidator.validateConfirmPassword( uiState.passwordInput ,trimmedVal)
        )
    }

    fun onTogglePasswordVisibility(){
        uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
    }

    fun onToggleCfPasswordVisibility(){
        uiState = uiState.copy(isCfPasswordVisible = !uiState.isCfPasswordVisible)
    }
    fun onSignUpClick(){
        if(validateSignUp()){
            uiState = uiState.copy(isLoading = true, errorSignUp = null)
            viewModelScope.launch {
                uiState = try{
                    delay(1000)
                    val result = authRepository.signUpUser(
                        SignUpUserRequestBody(
                            name = uiState.nameInput,
                            email = uiState.emailInput,
                            phone = uiState.phoneInput,
                            password = uiState.passwordInput,
                        )
                    )
                    Log.d(loggerTag, "NetworkResult : $result")
                    when(result){
                        is NetworkResult.Success -> {
                            val token = result.data?.token;
                            if(token!=null){
                                userPrefsRepository.updateToken(token)
                                uiState.copy(isSignedUp = true, errorSignUp = null, isLoading = false)
                            }else{
                                uiState.copy(isSignedUp = false, errorSignUp = "Something Went Wrong!", isLoading = false)
                            }
                        }
                        is NetworkResult.Error -> {
                            uiState.copy(errorSignUp = result.message, isLoading = false)
                        }
                    }

                }catch (e : Exception){
                    uiState.copy(errorSignUp = e.message, isLoading = false)
                }
            }
        }
    }

    private fun validateSignUp() : Boolean{
        uiState = uiState.copy(
            errorNameInput = InputValidator.validateName(uiState.nameInput),
            errorEmailInput = InputValidator.validateEmail(uiState.emailInput),
            errorPhoneInput = InputValidator.validatePhone(uiState.phoneInput),
            errorPasswordInput = InputValidator.validatePassword(uiState.passwordInput),
            errorCfPasswordInput = InputValidator.validateConfirmPassword(uiState.passwordInput,uiState.cfPasswordInput),
        )

        return uiState.errorNameInput==null
                && uiState.errorEmailInput==null
                && uiState.errorPhoneInput==null
                && uiState.errorPasswordInput==null
                && uiState.errorCfPasswordInput==null
    }

}