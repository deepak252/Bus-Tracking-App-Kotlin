package com.example.bustrackingapp.feature_bus_routes.presentation.components

import androidx.compose.foundation.background
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
import com.example.bustrackingapp.core.presentation.components.logo.LogoMiniLight
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import com.example.bustrackingapp.ui.theme.White

@Composable
fun BusRouteTile(
    modifier : Modifier = Modifier,
    routeNo : String,
    routeName : String,
    totalStops : Int?=null,
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
        LogoMiniLight(
            radius = 70f,
            padding = 0f
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = routeNo,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = routeName,
                style = MaterialTheme.typography.bodySmall
            )
            if(totalStops!=null)
                Text(
                    text = "Total Stops : $totalStops",
                    style = MaterialTheme.typography.bodySmall
                )

        }
    }
}

@Preview
@Composable
private fun BusRouteTilePreview(){
    BusRouteTile(
        routeNo = "111_UP",
        routeName = "Janak Puri",
        onClick = {},
        totalStops = 20,
        modifier = Modifier.background(White)
    )
}