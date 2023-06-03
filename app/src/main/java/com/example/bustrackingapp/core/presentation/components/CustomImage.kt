package com.example.bustrackingapp.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.ui.theme.Gray300
import com.example.bustrackingapp.ui.theme.Gray600

@Composable
fun CustomImage(
    modifier: Modifier = Modifier,
    imageVector: ImageVector?=null,
    drawableId: Int?=null,
    color: Color?=null,
    size : Float = 80f,
    borderRadius : Float = 100f,
    backgroundColor : Color=Color.Transparent,
    padding : Float = 8f
) {
    if(imageVector!=null){
        Image(
            imageVector = imageVector,
            contentDescription = null,
            colorFilter = if(color!=null) ColorFilter.tint(color) else null,
            modifier = modifier
                .height(size.dp)
                .width(size.dp)
                .background(
                    backgroundColor,
                    RoundedCornerShape(borderRadius.dp)
                )
                .padding(padding.dp)

        )
    }else if(drawableId!=null){
        Image(
            painter = painterResource(drawableId),
            contentDescription = null,
            colorFilter = if(color!=null) ColorFilter.tint(color) else null,
            modifier = modifier
                .height(size.dp)
                .width(size.dp)
                .background(backgroundColor, RoundedCornerShape(borderRadius.dp))
                .padding(padding.dp)

        )
    }

}