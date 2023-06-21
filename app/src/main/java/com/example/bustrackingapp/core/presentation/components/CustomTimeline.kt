package com.example.bustrackingapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bustrackingapp.R
import com.example.bustrackingapp.ui.theme.Gray100
import com.example.bustrackingapp.ui.theme.NavyBlue300
import com.example.bustrackingapp.ui.theme.NavyBlue500
import com.example.bustrackingapp.ui.theme.White

@Composable
fun CustomTimeline(
    modifier: Modifier = Modifier,
    items : List<TimelineItem>
){
    Column(
        modifier = modifier
    ) {
        items.forEachIndexed{ i,item->
            TimelineTile(
                item = item,
                isLast = i == items.size-1,
                index = i
            )
        }
    }

}

@Composable
fun TimelineTile(
    modifier: Modifier = Modifier,
    isFirst : Boolean = false,
    isLast : Boolean = false,
    item : TimelineItem,
    index : Int,
){
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomImage(
                modifier = Modifier.padding(4.dp),
                drawableId = R.drawable.bus_stop,
                color = Gray100,
                backgroundColor = NavyBlue500,
                size = 30f,
                padding = 4f
            )
            if(!isLast)
                Box(
                    modifier = Modifier
                        .background(NavyBlue300)
                        .height(50.dp)
                        .width(3.dp)
                )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                "${index+1}. ${item.title}",
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1
            )
            if(item.description!=null){
                item.description.forEach{ desc->
                    Text(
                        desc,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp
                    )
                }

            }
        }
    }
}

data class TimelineItem(
    val title : String,
    val description : List<String>?= emptyList()
)

@Composable
@Preview
private fun TimelineTilePreview(){
    TimelineTile(
        modifier = Modifier
            .background(White),
        item = TimelineItem(
            title = "Arriving"
        ),
        index = 1
    )
}
