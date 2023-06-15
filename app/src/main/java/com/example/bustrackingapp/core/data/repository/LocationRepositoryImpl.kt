package com.example.bustrackingapp.core.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.example.bustrackingapp.core.domain.repository.LocationRepository
import com.example.bustrackingapp.core.util.LoggerUtil
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : LocationRepository {
    private val logger = LoggerUtil("LocationRepositoryImpl")

    override fun getLocation(
//        fusedLocationClient: FusedLocationProviderClient,
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
}