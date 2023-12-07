package com.example.bustrackingapp.feature_home.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.R
import com.example.bustrackingapp.core.presentation.components.CustomImage
import com.example.bustrackingapp.core.presentation.components.FieldValue
import com.example.bustrackingapp.ui.theme.Gray100

@Composable
fun BusTile(
    modifier : Modifier = Modifier,
    routeNo : String,
    routeName : String,
    vehNo : String,
    onClick : ()->Unit,
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .width(130.dp)
                .clickable {
                    onClick()
                }
                .padding(8.dp)
        ) {
            CustomImage(
                drawableId = R.drawable.locate_bus,
                color = Gray100,
                size = 54f,
            )
            Text(
                text = "Veh No:  $vehNo",
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                textAlign = TextAlign.Center,

            )
            Text(
                text = "Route No:  $routeNo",
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
            Text(
                text = routeName,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun BusTilePreview(){
    BusTile(
        routeNo = "223_UP",
        routeName = "Janak Puri",
        vehNo = "DL 1202",
        onClick = {}
    )
}