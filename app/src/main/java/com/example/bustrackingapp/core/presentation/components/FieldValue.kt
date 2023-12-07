package com.example.bustrackingapp.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun FieldValue(
    field : String,
    value : String,
    fieldStyle : TextStyle =  MaterialTheme.typography.titleSmall,
    valueStyle : TextStyle =  MaterialTheme.typography.bodyMedium
){
    Row {
        Text(
            text = "$field :  ",
            style = fieldStyle
        )
        Text(
            text = value,
            style = valueStyle
        )
    }
}