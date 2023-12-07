package com.example.bustrackingapp.feature_auth.presentation.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.util.ValidationUtil
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_auth.domain.use_case.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel(){

    var uiState by mutableStateOf(SignUpUiState())
        private set

    fun onNameInputChange(newVal : String){
        uiState = uiState.copy(
            nameInput = newVal,
            errorNameInput = ValidationUtil.validateName(newVal)
        )
    }

    fun onEmailInputChange(newVal : String){
        uiState = uiState.copy(
            emailInput = newVal,
            errorEmailInput = ValidationUtil.validateEmail(newVal)
        )
    }
    fun onPhoneInputChange(newVal : String){
        uiState = uiState.copy(
            phoneInput = newVal,
            errorPhoneInput = ValidationUtil.validatePhone(newVal)
        )
    }

    fun onPasswordInputChange(newVal : String){
        val trimmedVal = newVal.filter { it!=' '}
        uiState = uiState.copy(
            passwordInput = trimmedVal,
            errorPasswordInput = ValidationUtil.validatePassword(trimmedVal),
            errorCfPasswordInput = if(uiState.cfPasswordInput.isNotEmpty())
                ValidationUtil.validateConfirmPassword( trimmedVal ,uiState.cfPasswordInput)
            else null
        )
    }

    fun onCfPasswordInputChange(newVal : String){
        val trimmedVal = newVal.filter { it!=' '}
        uiState = uiState.copy(
            cfPasswordInput = trimmedVal,
            errorCfPasswordInput = ValidationUtil.validateConfirmPassword( uiState.passwordInput ,trimmedVal)
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
            signUpUseCase(
                name = uiState.nameInput,
                email = uiState.emailInput,
                phone = uiState.phoneInput,
                password = uiState.passwordInput
            ).onEach { result ->
                uiState = when(result){
                    is Resource.Success -> {
                        uiState.copy(isSignedUp = true, errorSignUp = null, isLoading = false)
                    }
                    is Resource.Error -> {
                        uiState.copy(errorSignUp = result.message, isLoading = false)
                    }
                    is Resource.Loading -> {
                        uiState.copy(isLoading = true, errorSignUp = null)
                    }
                }
            }
                .launchIn(viewModelScope)
        }

    }

    private fun validateSignUp() : Boolean{
        uiState = uiState.copy(
            errorNameInput = ValidationUtil.validateName(uiState.nameInput),
            errorEmailInput = ValidationUtil.validateEmail(uiState.emailInput),
            errorPhoneInput = ValidationUtil.validatePhone(uiState.phoneInput),
            errorPasswordInput = ValidationUtil.validatePassword(uiState.passwordInput),
            errorCfPasswordInput = ValidationUtil.validateConfirmPassword(uiState.passwordInput,uiState.cfPasswordInput),
        )

        return uiState.errorNameInput==null
                && uiState.errorEmailInput==null
                && uiState.errorPhoneInput==null
                && uiState.errorPasswordInput==null
                && uiState.errorCfPasswordInput==null
    }

}