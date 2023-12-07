package com.example.bustrackingapp.core.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.bustrackingapp.core.domain.repository.LocationRepository
import com.example.bustrackingapp.core.util.LoggerUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationRepository {
    private val logger = LoggerUtil("LocationRepositoryImpl")

    override fun getLocation(
        onSuccess: (Location) -> Unit,
        onError: (Exception) -> Unit
    ) {
        try {
            if (
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                throw Exception("Fine location permission not granted!")
            } else {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        logger.info("Location : $location", "getLocation")
                        if (location != null) {
                            onSuccess(location)
                        } else {
                            onError(Exception("Couldn't fetch location!"))
                        }
                    }
                    .addOnFailureListener {
                        onError(it)
                    }
            }
        } catch (e: Exception) {
            logger.error("$e", "getLocation")
            onError(e)
        }
    }

    override fun getCurrentLocation(
        callback: (Location) -> Unit,
        onError : (Exception)->Unit,
        isLive : Boolean,
        updateInterval : Long
    ){
        try {
            if (
                ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                throw Exception("Fine location permission not granted!")
            } else {
                val locationRequest = LocationRequest.create().apply {
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    interval = 1000 // Update interval in milliseconds
                    fastestInterval = updateInterval // Fastest update interval in milliseconds, emit updated location
                }
                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val lastLocation = locationResult.lastLocation
                        logger.info("Location : $lastLocation", "getCurrentLocation")
                        if (lastLocation != null) {
                            if(!isLive){
                                // remove listener
                                fusedLocationClient.removeLocationUpdates(this)
                            }
                            callback(lastLocation)
                        }
                    }
                }
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,locationCallback,null
                )

            }
        } catch (e: Exception) {
            logger.error("$e", "getLocation")
            onError(e)
        }
    }



}