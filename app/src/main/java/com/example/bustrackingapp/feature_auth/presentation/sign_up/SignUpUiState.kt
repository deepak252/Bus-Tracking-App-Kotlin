package com.example.bustrackingapp.feature_auth.presentation.sign_up

data class SignUpUiState(
    val nameInput : String = "",
    val emailInput : String = "",
    val phoneInput : String = "",
    val passwordInput : String  = "",
    val cfPasswordInput : String  = "",
    val errorNameInput : String?=null,
    val errorEmailInput : String?=null,
    val errorPhoneInput : String?=null,
    val errorPasswordInput : String?=null,
    val errorCfPasswordInput : String?=null,
    val isPasswordVisible : Boolean = false,
    val isCfPasswordVisible : Boolean = false,
    val hideKeyboard : Boolean = false,
    val errorSignUp : String?=null,
    val isSignedUp : Boolean = false,
    val isLoading : Boolean = false
)