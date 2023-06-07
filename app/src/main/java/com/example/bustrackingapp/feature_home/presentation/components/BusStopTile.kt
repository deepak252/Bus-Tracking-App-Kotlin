package com.example.bustrackingapp.feature_home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.R
import com.example.bustrackingapp.core.presentation.components.CustomImage
import com.example.bustrackingapp.core.presentation.components.logo.LogoMiniLight
import com.example.bustrackingapp.ui.theme.Gray100
import com.example.bustrackingapp.ui.theme.NavyBlue500

@Composable
fun BusStopTile(
    modifier : Modifier = Modifier,
    stopNo : String,
    stopName : String,
    onClick : ()->Unit,
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(8.dp)
    ) {
        CustomImage(
            drawableId = R.drawable.bus_stop,
            color = Gray100,
            backgroundColor = NavyBlue500,
            size = 54f,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = stopName,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = stopNo,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview
@Composable
private fun BusStopTilePreview(){
    BusStopTile(
        stopName = "Janak Puri",
        stopNo = "Stop 1",
        onClick = {}
    )
}