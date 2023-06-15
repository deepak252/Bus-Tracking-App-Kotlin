package com.example.bustrackingapp.core.presentation.components

import android.Manifest
import android.content.Context
import android.content.IntentSender
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bustrackingapp.core.util.LoggerUtil
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LocationPermissionWrapper(
    content: @Composable () -> Unit
) {
    val logger  = LoggerUtil(c = "LocationPermissionWrapper")
    val context = LocalContext.current

    var isLocationEnabled by rememberSaveable{ mutableStateOf(true) }

    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == 0){
            logger.info("Denied : ${activityResult.resultCode}")
            isLocationEnabled = false
        } else {
            logger.info("Accepted : ${activityResult.resultCode}")
            isLocationEnabled = true
        }
    }

    LaunchedEffect(key1 = Unit){
        if(!locationPermissionsState.allPermissionsGranted){
            // Request grant Location Permission
            locationPermissionsState.launchMultiplePermissionRequest()
        }else{
            // Enable location
            checkLocationSetting(
                context = context,
                onEnabled = {
                    logger.info("Location Enabled")
                    isLocationEnabled = true
                },
                onDisabled = {
                    logger.info("Location Disabled")
                    isLocationEnabled = false
                    settingResultRequest.launch(it)
                }
            )
        }
    }

    if(locationPermissionsState.allPermissionsGranted && isLocationEnabled){
        content()
    }else {
        val allPermissionsRevoked =
            locationPermissionsState.permissions.size == locationPermissionsState.revokedPermissions.size

        val textToShow =  if(locationPermissionsState.allPermissionsGranted){
            "To proceed, please enable GPS (Location Services) on your device."
        } else if (!allPermissionsRevoked) {
            // If not all the permissions are revoked, it's because the user accepted the COARSE
            // location permission, but not the FINE one.
            "Thanks for letting access your approximate location. Please grant us fine location."
        } else if (locationPermissionsState.shouldShowRationale) {
            // Both location permissions have been denied
            "Getting your exact location is important for this app. Please grant us location permission."
        } else {
            // First time the user sees this feature or the user doesn't want to be asked again
            "This application requires location permission"
        }

        val btnText = if(locationPermissionsState.allPermissionsGranted){
            "Turn On Location"
        }else{
            "Request Access"
        }

        AlertDialog(onDismissRequest = { /*TODO*/ }) {
            Surface(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                shape = MaterialTheme.shapes.large
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(text = textToShow)
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomElevatedButton(
                        onClick = {
                            if(locationPermissionsState.allPermissionsGranted){
                                checkLocationSetting(
                                    context = context,
                                    onEnabled = {
                                        isLocationEnabled = true
                                    },
                                    onDisabled = {
                                        isLocationEnabled = false
                                        settingResultRequest.launch(it)
                                    }
                                )
                            }else{
                                locationPermissionsState.launchMultiplePermissionRequest()
                            }
                         },
                        text = btnText,
                        borderRadius = 100.0
                    )
                }
            }
        }
    }

}



// call this function on button click  // Enable gps
private fun checkLocationSetting(
    context: Context,
    onDisabled: (IntentSenderRequest) -> Unit,
    onEnabled: () -> Unit
) {
    val logger  = LoggerUtil()
    val locationRequest = LocationRequest.Builder(1000)
        .setMinUpdateIntervalMillis(1000)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .build()

    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val builder: LocationSettingsRequest.Builder = LocationSettingsRequest
        .Builder()
        .addLocationRequest(locationRequest)

    val gpsSettingTask: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


    gpsSettingTask.addOnSuccessListener { onEnabled() }
    gpsSettingTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest
                    .Builder(exception.resolution)
                    .build()
                onDisabled(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                logger.error(sendEx,"checkLocationSetting")
            }
        }
    }

}


