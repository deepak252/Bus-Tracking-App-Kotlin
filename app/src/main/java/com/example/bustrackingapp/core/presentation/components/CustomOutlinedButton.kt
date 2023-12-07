package com.example.bustrackingapp.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bustrackingapp.ui.theme.NavyBlue300

@Composable
fun CustomOutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String?=null,
    textSize: TextUnit = 16.sp,
    color: Color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f),

//    width: Float = 1f,
    contentPadding: PaddingValues = PaddingValues(
        start = 14.dp, top = 15.dp, end = 14.dp, bottom = 11.dp,
    ),
    borderRadius : Double = 10.0,
    content: (@Composable RowScope.() -> Unit)?=null

) {
    OutlinedButton(
        onClick,
//        modifier = modifier.fillMaxWidth(width),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = color,
        ),
        contentPadding = contentPadding,
        shape = RoundedCornerShape(borderRadius.dp),
        border = BorderStroke(1.dp, NavyBlue300),
    ) {
        if(content!=null)
            content()
        else
            Text(
                text?:"",
                fontSize = textSize,
    //            fontFamily = Poppins,
            )
    }
}