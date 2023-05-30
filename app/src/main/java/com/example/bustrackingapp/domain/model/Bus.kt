package com.example.bustrackingapp.domain.model

import com.google.gson.annotations.SerializedName

data class Bus(
    val id : String?,
    val vehNo : String?,
    val status : Boolean?,
    val locationId : String?,
    val busType : String?
)
