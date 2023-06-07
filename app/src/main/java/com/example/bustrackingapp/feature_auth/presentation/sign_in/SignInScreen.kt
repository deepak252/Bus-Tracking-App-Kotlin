package com.example.bustrackingapp.feature_auth.presentation.sign_in

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bustrackingapp.R
import com.example.bustrackingapp.core.presentation.components.CustomElevatedButton
import com.example.bustrackingapp.core.presentation.components.CustomTextField
import com.example.bustrackingapp.core.presentation.components.dialog.LoadingIndicatorDialog
import com.example.bustrackingapp.core.presentation.components.logo.LogoCircularDark
import com.example.bustrackingapp.core.presentation.components.logo.LogoCircularLight
import com.example.bustrackingapp.core.presentation.components.logo.LogoWhiteNoBg
import com.example.bustrackingapp.ui.theme.Blue500
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.Red500
import com.example.bustrackingapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onRegisterClick : ()->Unit,
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    }
) {
    //    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = signInViewModel.uiState.errorSignIn, key2 = signInViewModel.uiState.isSignedIn){
        if(signInViewModel.uiState.errorSignIn!=null){
            snackbarState.showSnackbar(signInViewModel.uiState.errorSignIn!!)
        }else if(signInViewModel.uiState.isSignedIn){
            snackbarState.showSnackbar("Signed In Successfully")
        }
    }

    if(signInViewModel.uiState.isLoading){
        LoadingIndicatorDialog()
    }

    Scaffold(
        modifier = Modifier
            .clickable(
                // Remove Ripple Effect
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
//            keyboardController?.hide()
            },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
            ){
                if(signInViewModel.uiState.errorSignIn!=null){
                    Snackbar(
                        snackbarData = it,
                        containerColor = Red400,
                        contentColor = White
                    )
                }else{
                    Snackbar(snackbarData = it)
                }
            }
        },

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            SignInContainer(
                email = { signInViewModel.uiState.emailInput },
                password = { signInViewModel.uiState.passwordInput },
                onEmailChange = signInViewModel::onEmailInputChange,
                onPasswordChange = signInViewModel::onPasswordInputChange,
                isPasswordVisible = { signInViewModel.uiState.isPasswordVisible },
                onTogglePasswordVisibility = signInViewModel::onTogglePasswordVisibility,
                errorEmail = { signInViewModel.uiState.errorEmailInput },
                errorPassword = { signInViewModel.uiState.errorPasswordInput },
                onClearFocus = { focusManager.clearFocus() },
                onSignInClick = signInViewModel::onSignInClick,
                onRegisterClick = onRegisterClick
            )

        }
    }
}

@Composable
private fun SignInContainer(
    email: () -> String,
    password: () -> String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: () -> Boolean,
    onTogglePasswordVisibility: () -> Unit,
    errorEmail: () -> String?,
    errorPassword: () -> String?,
    onClearFocus: () -> Unit,
    onSignInClick: ()-> Unit,
    onRegisterClick: () -> Unit

){
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LogoCircularLight()
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Sign In",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = { email() },
            onValueChange = onEmailChange,
            hintText = "xyz@gmail.com",
            labelText = "Enter Email",
            onClearFocus = onClearFocus,
            errorMessage = { errorEmail() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = { password() },
            onValueChange = onPasswordChange,
            hintText = "****",
            labelText = "Enter Password",
            onClearFocus = onClearFocus,
            trailingIcon = if (isPasswordVisible())
                ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
            else ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24),
            onTrailingIconClick = onTogglePasswordVisibility,
            visualTransformation = if (isPasswordVisible()) {
                VisualTransformation.None
            } else PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            errorMessage = { errorPassword() }
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomElevatedButton(
            onClick = onSignInClick,
            text = "Sign In"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account? "
            )
            Text(
                text = " Create Account ",
                color = Blue500,
                modifier = Modifier.clickable {
                    onRegisterClick()
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

    }
}