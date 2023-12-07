package com.example.bustrackingapp.core.util

object SocketEvent {
    object User{
        const val joinRoute = "user:joinRoute"
        const val routeJoined = "user:routeJoined"
    }
    object Bus{
        const val locationUpdated  = "bus:locationUpdated"
        const val routeUpdated = "bus:routeUpdated"
    }
    const val error = "error"

}
