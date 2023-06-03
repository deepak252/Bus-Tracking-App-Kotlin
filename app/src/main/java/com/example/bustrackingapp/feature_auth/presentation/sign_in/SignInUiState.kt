package com.example.bustrackingapp.feature_auth.presentation.sign_in

data class SignInUiState(
    val emailInput : String = "",
    val passwordInput : String  = "",
    val errorEmailInput : String?=null,
    val errorPasswordInput : String?=null,
    val isPasswordVisible : Boolean = false,
    val hideKeyboard : Boolean = false,
    val errorSignIn : String?=null,
    val isSignedIn : Boolean = false,
    val isLoading : Boolean = false
)