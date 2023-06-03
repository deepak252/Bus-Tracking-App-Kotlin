package com.example.bustrackingapp.core.presentation.components.dialog

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.example.bustrackingapp.ui.theme.Blue500

@Composable
fun LoadingIndicatorDialog(
    onDismiss : (() -> Unit)?=null
) {
    Dialog(
        onDismissRequest = {
            if(onDismiss!=null) onDismiss()
        }
    ) {
        CircularProgressIndicator(
            color = Blue500
        )
    }
}