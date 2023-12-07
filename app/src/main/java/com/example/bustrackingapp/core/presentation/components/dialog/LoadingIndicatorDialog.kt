package com.example.bustrackingapp.core.presentation.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.example.bustrackingapp.core.presentation.components.CustomLoadingIndicator

@Composable
fun LoadingIndicatorDialog(
    onDismiss : (() -> Unit)?=null
) {
    Dialog(
        onDismissRequest = {
            if(onDismiss!=null) onDismiss()
        }
    ) {
        CustomLoadingIndicator()
    }
}