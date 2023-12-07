package com.example.bustrackingapp.feature_bus_routes.presentation.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.bustrackingapp.R
import com.example.bustrackingapp.core.domain.models.Bus
import com.example.bustrackingapp.core.presentation.components.addMarker
import com.example.bustrackingapp.core.presentation.components.createPolyline
import com.example.bustrackingapp.core.presentation.components.initMap
import com.example.bustrackingapp.core.presentation.components.mapEventsOverlay
import com.example.bustrackingapp.core.presentation.components.rememberMapView
import com.example.bustrackingapp.core.util.Constants
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithDuration
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun RouteMapView(
    modifier: Modifier = Modifier,
    busStops : List<BusStopWithDuration> = emptyList(),
    liveBuses : List<Bus> = emptyList()
) {
    val context = LocalContext.current
    // Initialize osmdroid configuration
    Configuration.getInstance().load(context,context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
    // Create and configure the MapView
    val mapView = rememberMapView(context)

    val liveBusMarkers = remember{
        mutableStateListOf<Marker>()
    }

    // Return the MapView as a Composable
    AndroidView(
        factory = { mapView },
        modifier = modifier.fillMaxSize()

    ) { view ->
        view.apply {
            initMap(this)


            val defaultGeoPoint =
                if(busStops.isNotEmpty())
                    GeoPoint(
                        busStops.first().stop.location.lat,
                        busStops.first().stop.location.lng
                    )
                    else GeoPoint(28.7041,77.1025)
//            val defaultGeoPoint = GeoPoint(28.6670366,77.0727499)
            val eventsOverlay = mapEventsOverlay(
                context, view,
                onTap = { geoPoint2 ->
//                    addMarker(context, view,geoPoint2)
                }
            )

            drawRoute(context, mapView,busStops)
            // Add/update markers for the buses in route
            liveBuses.forEach { bus->
                val location = bus.location
                if(location!=null){
                    val i = liveBusMarkers.indexOfFirst { it.id == bus._id }
                    Log.d("liveBus", "i = $i, liveBusMarkers = $liveBusMarkers, bus = $bus")
                    if(i<0){
                        val marker = addMarker(
                            mapView = mapView,
                            geoPoint = GeoPoint(location.lat, location.lng),
                            title = "BUS DETAILS",
                            snippet = "<b>Vehicle No :</b> ${bus.vehNo}"
                                    + "<br><b>Status :</b> ${Constants.busStatus[bus.status]?:"NA"}"
                                    + "<br><b>Type :</b> ${bus.info.busType}",
                            icon = iconBusLocation(
                                context = context,
                                busType = bus.info.busType
                            )
                        )
                        marker.id = bus._id
//                        marker.position = GeoPoint(28.6730335,77.0933930)
                        liveBusMarkers.add(marker)
                    }else{
                        liveBusMarkers[i].position = GeoPoint(location.lat, location.lng)
                    }
                }
            }

            liveBusMarkers.removeAll{ marker ->
                val i = liveBuses.indexOfFirst { it._id == marker.id }
                if(i<0){
                    mapView.overlays.remove(marker)
                    marker.remove(view)
                    true
                }else{
                    false
                }
            }

            view.overlays.add(0, eventsOverlay)
            view.controller.setCenter(defaultGeoPoint)

        }
    }
}


private fun drawRoute( context : Context, mapView : MapView, busStops : List<BusStopWithDuration>){
    if(busStops.isEmpty()){
        return
    }
    // Draw Route
    var prevStop = busStops[0]
    busStops.forEach{ currStop->
        val startPoint = GeoPoint(prevStop.stop.location.lat, prevStop.stop.location.lng)
        val endPoint = GeoPoint(currStop.stop.location.lat, currStop.stop.location.lng)
        val polyline = createPolyline(mapView,startPoint, endPoint )
        mapView.overlays.add(polyline)
        prevStop = currStop
    }
    // Add Bus Stops
    busStops.forEach{
        addMarker(
            mapView = mapView,
            geoPoint = GeoPoint(it.stop.location.lat, it.stop.location.lng),
            title = "BUS STOP DETAILS",
            snippet = "<b>Stop No. : </b>${it.stop.stopNo}"
            +"<br><b>Stop Name : </b>${it.stop.name}",
            icon = iconBusStop(context)
        )
    }

}

private fun iconBusLocation(context : Context, size : Int = 125, busType : String = "blue") : Drawable{
    val customIcon = context.getDrawable(Constants.busIcon[busType]?:R.drawable.bus_location_blue)
    val resizedIcon = resizeDrawable(customIcon, newWidth = size, newHeight = size)

    return  BitmapDrawable(context.resources, resizedIcon)
}

private fun iconBusStop(context : Context,  ) : Drawable{
    val customIcon = context.getDrawable(R.drawable.bus_stop)
    val resizedIcon = resizeDrawable(customIcon, newWidth = 65, newHeight = 65)
    return  BitmapDrawable(context.resources, resizedIcon)
}

private fun resizeDrawable(drawable: Drawable?, newWidth: Int, newHeight: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable?.setBounds(0, 0, canvas.width, canvas.height)
    drawable?.draw(canvas)
    return bitmap
}

private fun tintDrawable(context : Context ,drawable: Drawable?, colorRes: Int): Drawable? {
    if (drawable == null) return null
    val wrappedDrawable = DrawableCompat.wrap(drawable).mutate()
    val color = ContextCompat.getColor(context, colorRes)
    DrawableCompat.setTint(wrappedDrawable, color)
    DrawableCompat.setTintMode(wrappedDrawable, PorterDuff.Mode.SRC_IN)
    return wrappedDrawable
}

