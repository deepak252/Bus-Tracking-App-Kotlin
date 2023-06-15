package com.example.bustrackingapp.core.presentation.components

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen() {
//    val logger  = LoggerUtil("LocationPermissionScreen")
//    val locationPermissionsState = rememberMultiplePermissionsState(
//        permissions = listOf(
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//    )
//
//    val context = LocalContext.current
//
//    if(locationPermissionsState.allPermissionsGranted){
//        Text("Thanks! I can access your exact location :D")
//    }else{
//        val allPermissionsRevoked =
//            locationPermissionsState.permissions.size ==
//                    locationPermissionsState.revokedPermissions.size
//
//        val textToShow = if (!allPermissionsRevoked) {
//            // If not all the permissions are revoked, it's because the user accepted the COARSE
//            // location permission, but not the FINE one.
//            "Yay! Thanks for letting me access your approximate location. " +
//                    "But you know what would be great? If you allow me to know where you " +
//                    "exactly are. Thank you!"
//        } else if (locationPermissionsState.shouldShowRationale) {
//            // Both location permissions have been denied
//            "Getting your exact location is important for this app. " +
//                    "Please grant us fine location. Thank you :D"
//        } else {
//            // First time the user sees this feature or the user doesn't want to be asked again
//            "This feature requires location permission"
//        }
//
//        val buttonText = if (!allPermissionsRevoked) {
//            "Allow precise location"
//        } else {
//            "Request permissions"
//        }
//
//        Column() {
//            Text(text = textToShow)
//            Spacer(modifier = Modifier.height(8.dp))
//            Button(onClick = {
//                locationPermissionsState.launchMultiplePermissionRequest()
//
////                if (!allPermissionsRevoked || locationPermissionsState.shouldShowRationale) {
////                    locationPermissionsState.launchMultiplePermissionRequest()
////                } else {
////                    // Open app settings
////                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
////                    val uri = Uri.fromParts("package", context.packageName, null)
////                    intent.data = uri
////                    context.startActivity(intent)
////                }
//            }) {
//                Text(buttonText)
//            }
//        }
//    }





    val logger  = LoggerUtil("LocationPermissionScreen")

    val context: Context = LocalContext.current

    val settingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == 200)
            logger.info("Accepted : ${activityResult.resultCode}")
        else {
            logger.info("Denied : ${activityResult.resultCode}")
        }
    }

    Column() {
        Spacer(modifier = Modifier.height(40.dp))
        Button(onClick = {
            checkLocationSetting(
                context = context,
                onDisabled = { intentSenderRequest ->
                    settingResultRequest.launch(intentSenderRequest)
                },
                onEnabled = { /* This will call when setting is already enabled */ }
            )
        }) {
            Text(text = "Request permission")


        }
        Spacer(modifier = Modifier.height(20.dp))
        LocationFetcher(onLocationFetched = { lat,lng->

        })
    }


}



@Composable
fun LocationFetcher(onLocationFetched: (lat: Double, lng: Double) -> Unit) {
    val context = LocalContext.current
    val fusedLocationClient by remember{ mutableStateOf(LocationServices.getFusedLocationProviderClient(context)) }
    var locationState by remember{mutableStateOf<Location?>(null)}
    val logger  = LoggerUtil("LocationFetcher")

    LaunchedEffect(Unit) {
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                logger.error("Permission not granted", "LocationFetcher")
            }else{

                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location : Location? ->
                        logger.info("Location : $location", "LocationFetcher")

                        if(location!=null){
                            locationState = location
                        }
                        // Got last known location. In some rare situations this can be null.
                    }
            }

//            val location = fusedLocationClient.lastLocation
//            location?.let {
//                onLocationFetched(it.latitude, it.longitude)
//            }
        } catch (e: Exception) {

            logger.error("$e", "LocationFetcher")
            // Handle location fetching error
        }
    }
    if(locationState==null)
    Text("Fetching Location")
    else Text("${locationState!!.latitude},  ${locationState!!.longitude}")
}


// call this function on button click  // Enable gps
private fun checkLocationSetting(
    context: Context,
    onDisabled: (IntentSenderRequest) -> Unit,
    onEnabled: () -> Unit
) {
    val locationRequest = LocationRequest.Builder(1000)
//        .setIntervalMillis(1000)
        .setMinUpdateIntervalMillis(1000)
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .build()
//    val locationRequest = LocationRequest.create().apply {
//        interval = 1000
//        fastestInterval = 1000
//        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//    }

    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val builder: LocationSettingsRequest.Builder = LocationSettingsRequest
        .Builder()
        .addLocationRequest(locationRequest)

    val gpsSettingTask: Task<LocationSettingsResponse> =
        client.checkLocationSettings(builder.build())

    gpsSettingTask.addOnSuccessListener { onEnabled() }
    gpsSettingTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest
                    .Builder(exception.resolution)
                    .build()
                onDisabled(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
                // ignore here
            }
        }
    }

}



//@Composable
//fun GpsLayout() {
//    val logger  = LoggerUtil("LocationPermissionScreen")
//
//    val context: Context = LocalContext.current
//
//    val settingResultRequest = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartIntentSenderForResult()
//    ) { activityResult ->
//        if (activityResult.resultCode == 200)
//            logger.info("Accepted : ${activityResult.resultCode}")
//        else {
//            logger.info("Denied : ${activityResult.resultCode}")
//        }
//    }
//
//    Button(onClick = {
//        checkLocationSetting(
//            context = context,
//            onDisabled = { intentSenderRequest ->
//                settingResultRequest.launch(intentSenderRequest)
//            },
//            onEnabled = { /* This will call when setting is already enabled */ }
//        )
//    }) {
//        Text(text = "Request permission")
//    }
//
//}




//private fun turnOnLocation(
//    context: Context,
//    onDisabled: (IntentSenderRequest) -> Unit,
//    onEnabled: () -> Unit
//) {
//    val settingsClient = LocationServices.getSettingsClient(context)
//        val locationRequest = LocationRequest.create().apply {
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }
//    LocationRequest.Builder(122).set
////    val locationRequest = LocationRequest.create().apply {
////        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
////    }
//    val builder = LocationSettingsRequest.Builder()
//        .addLocationRequest(locationRequest)
//    val task = settingsClient.checkLocationSettings(builder.build())
//
//    task.addOnSuccessListener {
//        // Location settings are already satisfied
//        Log.d("Location", "Location settings are already satisfied")
//    }
//
//    task.addOnFailureListener { exception ->
//        if (exception is ResolvableApiException) {
//            try {
//                // Location settings are not satisfied, but can be resolved by showing the user a dialog.
////                exception.startResolutionForResult(context, REQUEST_LOCATION_SETTINGS)
//
//                val intentSenderRequest = IntentSenderRequest
//                    .Builder(exception.resolution)
//                    .build()
//                onDisabled(intentSenderRequest)
//
//            } catch (sendEx: IntentSender.SendIntentException) {
//                // Error handling
//                Log.e("Location", "Error resolving location settings: ${sendEx.message}")
//            }
//        } else {
//            // Location settings are not satisfied and cannot be resolved.
//            Log.e("Location", "Location settings are not satisfied.")
//        }
//    }
//}





