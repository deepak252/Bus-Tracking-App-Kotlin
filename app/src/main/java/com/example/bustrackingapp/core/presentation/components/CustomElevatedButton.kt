package com.example.bustrackingapp.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomElevatedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textSize: TextUnit = 16.sp,
    defaultElevation: Dp = 6.dp,
    color: Color = MaterialTheme.colorScheme.secondaryContainer,
    width: Float = 1f,
    contentPadding: PaddingValues = PaddingValues(
        start = 14.dp, top = 15.dp, end = 14.dp, bottom = 11.dp,
    ),
    borderRadius : Double = 10.0


) {
    ElevatedButton(
        onClick,
        modifier = modifier.fillMaxWidth(width),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = defaultElevation
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        contentPadding = contentPadding,
        shape = RoundedCornerShape(borderRadius.dp)
    ) {
        Text(
            text,
            fontSize = textSize,
//            fontFamily = Poppins,
        )
    }
}