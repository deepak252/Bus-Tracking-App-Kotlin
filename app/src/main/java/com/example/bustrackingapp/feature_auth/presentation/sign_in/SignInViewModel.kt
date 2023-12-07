package com.example.bustrackingapp.feature_auth.presentation.sign_in

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bustrackingapp.core.util.ValidationUtil
import com.example.bustrackingapp.core.util.Resource
import com.example.bustrackingapp.feature_auth.domain.use_case.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel(){

    var uiState by mutableStateOf(SignInUiState())
        private set

    fun onEmailInputChange(newVal : String){
        uiState = uiState.copy(
            emailInput = newVal,
            errorEmailInput = ValidationUtil.validateEmail(newVal)
        )
    }

    fun onPasswordInputChange(newVal : String){
        val trimmedVal = newVal.filter { it!=' '}
        uiState = uiState.copy(
            passwordInput = trimmedVal,
            errorPasswordInput = ValidationUtil.validatePassword(trimmedVal)
        )
    }

    fun onTogglePasswordVisibility(){
        uiState = uiState.copy(isPasswordVisible = !uiState.isPasswordVisible)
    }

    fun onSignInClick(){
        if(validateSignIn()){
            signInUseCase(
                email = uiState.emailInput,
                password = uiState.passwordInput
            ).onEach {result->
                uiState = when(result){
                    is Resource.Success ->{
                        uiState.copy(isSignedIn = true, errorSignIn = null, isLoading = false)
                    }
                    is Resource.Error ->{
                        uiState.copy(errorSignIn = result.message, isLoading = false)
                    }
                    is Resource.Loading ->{
                        uiState.copy(isLoading = true, errorSignIn = null)
                    }
                }
            }
                .launchIn(viewModelScope)
        }
//        if(validateSignIn()){
//            uiState = uiState.copy(isLoading = true, errorSignIn = null)
//            viewModelScope.launch {
//                uiState = try{
//                    val result = authRepository.signInUser(
//                        SignInUserRequest(
//                            email = uiState.emailInput,
//                            password = uiState.passwordInput
//                        )
//                    )
//                    when(result){
//                        is NetworkResult.Success -> {
//                            val token = result.data?.token;
//                            if(token!=null){
//                                userPrefsRepository.updateToken(token)
//                                uiState.copy(isSignedIn = true, errorSignIn = null, isLoading = false)
//                            }else{
//                                uiState.copy(isSignedIn = false, errorSignIn = "Something Went Wrong!", isLoading = false)
//                            }
//                        }
//                        is NetworkResult.Error -> {
//                            uiState.copy(errorSignIn = result.message, isLoading = false)
//                        }
//                    }
//
//                }catch (e : Exception){
//                    uiState.copy(errorSignIn = e.message, isLoading = false)
//                }
//            }
//        }
    }

    private fun validateSignIn() : Boolean{
        uiState = uiState.copy(
            errorEmailInput = ValidationUtil.validateEmail(uiState.emailInput),
            errorPasswordInput = ValidationUtil.validatePassword(uiState.passwordInput),
        )
        return uiState.errorEmailInput==null && uiState.errorPasswordInput==null

    }

}