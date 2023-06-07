package com.example.bustrackingapp.feature_auth.presentation.sign_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import com.example.bustrackingapp.core.presentation.components.logo.LogoCircularLight
import com.example.bustrackingapp.ui.theme.Blue500
import com.example.bustrackingapp.ui.theme.Red400
import com.example.bustrackingapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    onSignInClick : ()->Unit,
    snackbarState : SnackbarHostState = remember {
        SnackbarHostState()
    }
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = signUpViewModel.uiState.errorSignUp, key2 = signUpViewModel.uiState.isSignedUp){
        if(signUpViewModel.uiState.errorSignUp!=null){
            snackbarState.showSnackbar(signUpViewModel.uiState.errorSignUp!!)
        }else if(signUpViewModel.uiState.isSignedUp){
            snackbarState.showSnackbar("Account Created Successfully")
        }
    }

    if(signUpViewModel.uiState.isLoading){
        LoadingIndicatorDialog()
    }

    Scaffold(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarState,
            ){
                if(signUpViewModel.uiState.errorSignUp!=null){
                    Snackbar(
                        snackbarData = it,
                        containerColor = Red400,
                        contentColor = White
                    )
                }else{
                    Snackbar(snackbarData = it)
                }
            }
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            SignUpContainer(
                name = { signUpViewModel.uiState.nameInput },
                email = { signUpViewModel.uiState.emailInput },
                phone = { signUpViewModel.uiState.phoneInput },
                password = { signUpViewModel.uiState.passwordInput },
                cfPassword = { signUpViewModel.uiState.cfPasswordInput },
                onNameChange = signUpViewModel::onNameInputChange,
                onEmailChange = signUpViewModel::onEmailInputChange,
                onPhoneChange = signUpViewModel::onPhoneInputChange,
                onPasswordChange = signUpViewModel::onPasswordInputChange,
                onCfPasswordChange = signUpViewModel::onCfPasswordInputChange,
                isPasswordVisible = { signUpViewModel.uiState.isPasswordVisible },
                isCfPasswordVisible = { signUpViewModel.uiState.isCfPasswordVisible },
                onTogglePasswordVisibility = signUpViewModel::onTogglePasswordVisibility,
                onToggleCfPasswordVisibility = signUpViewModel::onToggleCfPasswordVisibility,
                errorName = { signUpViewModel.uiState.errorNameInput },
                errorEmail = { signUpViewModel.uiState.errorEmailInput },
                errorPhone = { signUpViewModel.uiState.errorPhoneInput },
                errorPassword = { signUpViewModel.uiState.errorPasswordInput },
                errorCfPassword = { signUpViewModel.uiState.errorCfPasswordInput },
                onClearFocus = { focusManager.clearFocus() },
                onSignUpClick = signUpViewModel::onSignUpClick,
                onSignInClick = onSignInClick,
            )
        }
    }
}

@Composable
private fun SignUpContainer(
    name: () -> String,
    email: () -> String,
    phone: () -> String,
    password: () -> String,
    cfPassword: () -> String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCfPasswordChange: (String) -> Unit,
    isPasswordVisible: () -> Boolean,
    isCfPasswordVisible: () -> Boolean,
    onTogglePasswordVisibility: () -> Unit,
    onToggleCfPasswordVisibility: () -> Unit,
    errorName: () -> String?,
    errorEmail: () -> String?,
    errorPhone: () -> String?,
    errorPassword: () -> String?,
    errorCfPassword: () -> String?,
    onClearFocus: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
) {
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
                "Create Account",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(
            value = { name() },
            onValueChange = onNameChange,
            hintText = "XYZ",
            labelText = "Enter Name",
            onClearFocus = onClearFocus,
            errorMessage = { errorName() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = { email() },
            onValueChange = onEmailChange,
            hintText = "xyz@gmail.com",
            labelText = "Enter Email",
            onClearFocus = onClearFocus,
            errorMessage = { errorEmail() },
            keyboardType = KeyboardType.Email,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = { phone() },
            onValueChange = onPhoneChange,
            hintText = "+919876543210",
            labelText = "Enter Phone",
            onClearFocus = onClearFocus,
            errorMessage = { errorPhone() },
            keyboardType = KeyboardType.Phone,
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
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextField(
            value = { cfPassword() },
            onValueChange = onCfPasswordChange,
            hintText = "****",
            labelText = "Confirm Password",
            onClearFocus = onClearFocus,
            trailingIcon = if (isCfPasswordVisible())
                ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
            else ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24),
            onTrailingIconClick = onToggleCfPasswordVisibility,
            visualTransformation = if (isCfPasswordVisible()) {
                VisualTransformation.None
            } else PasswordVisualTransformation(),
            keyboardType = KeyboardType.Password,
            errorMessage = { errorCfPassword() }
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomElevatedButton(
            onClick = onSignUpClick,
            text = "Sign Up"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account? "
            )
            Text(
                text = " Sign In ",
                color = Blue500,
                modifier = Modifier.clickable {
                    onSignInClick()
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

    }
}