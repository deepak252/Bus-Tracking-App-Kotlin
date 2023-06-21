package com.example.bustrackingapp.core.domain.repository

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.flow.Flow


interface LocationRepository {
    fun getLocation(
//        fusedLocationClient : FusedLocationProviderClient,
        onSuccess : (Location)->Unit,
        onError : (Exception)->Unit
    )

    fun getCurrentLocation(
        callback: (Location) -> Unit,
        onError : (Exception)->Unit,
        isLive : Boolean,
        updateInterval : Long = 1000
    )

}