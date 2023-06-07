package com.example.bustrackingapp.core.presentation.components.logo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.R

@Composable
fun LogoMiniDark(
    radius : Float = 100f,
    padding: Float  = 8f
) {
    Image(
        painter = painterResource(id = R.drawable.logo_dark_mini),
        contentDescription = "logo_dark_mini",
        modifier = Modifier
            .height(radius.dp)
            .width(radius.dp)
            .padding(padding.dp)
    )
}