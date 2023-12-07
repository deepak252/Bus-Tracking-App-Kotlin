package com.example.bustrackingapp.feature_bus_routes.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.R
import com.example.bustrackingapp.core.domain.models.RouteSchedule
import com.example.bustrackingapp.core.presentation.components.CustomOutlinedButton
import com.example.bustrackingapp.core.presentation.components.CustomTimeline
import com.example.bustrackingapp.core.presentation.components.FieldValue
import com.example.bustrackingapp.core.presentation.components.TimelineItem
import com.example.bustrackingapp.core.util.BusRouteUtil
import com.example.bustrackingapp.core.util.Constants
import com.example.bustrackingapp.core.util.DateTimeUtil
import com.example.bustrackingapp.feature_bus_routes.domain.models.BusRouteWithStops
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithDuration
import com.example.bustrackingapp.ui.theme.Blue500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteScheduleBottomSheet(
    onDismissRequest : ()->Unit,
    route : ()->BusRouteWithStops
) {
    val currentDay = DateTimeUtil.getCurrentDay()
    var selectedDay by rememberSaveable{
        mutableStateOf(currentDay)
    }
    var isDropdownVisible by rememberSaveable{
        mutableStateOf(false)
    }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = {
            false
        }
    )

    val departureTimings = route().schedule.firstOrNull{
        it.day==selectedDay // for selected day
    }?.departureTime

    val scheduleForDay by remember(key1 = selectedDay){
        mutableStateOf<List<List<String>>>(BusRouteUtil.getScheduleForDay(
            route(),
            selectedDay
        ))
    }


    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
        modifier = Modifier.padding(
            horizontal = 6.dp
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth()

                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            FieldValue(
                field = "Route No :  ",
                value = route().routeNo
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                
            ) {
                Text(
                    text = "Day : ",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.width(24.dp))
                CustomOutlinedButton(
                    onClick = {
                        isDropdownVisible = !isDropdownVisible
                    },
                ){
                    Text(
                        text = selectedDay.uppercase(),
                        color = Blue500,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.sharp_keyboard_arrow_down_24),
                        contentDescription = null,
                        tint = Blue500
                    )
                    DropdownMenu(
                        expanded = isDropdownVisible,
                        onDismissRequest = {
                            isDropdownVisible = false
                        }
                    ) {
                        Constants.days.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = option.uppercase())
                                },
                                onClick = {
                                    selectedDay = option
                                    isDropdownVisible = false
                                },
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            CustomTimeline(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState()),
                items = route().stops.mapIndexed{i,item->
                    TimelineItem(
                        item.stop.name,
                        listOf(
                            "Stop No.:   ${item.stop.stopNo}",
                            "Timings: ${scheduleForDay[i].joinToString(", ")}"
                        )
                    )
                },

            )
            Spacer(modifier = Modifier.height(12.dp))
        }

    }


}

