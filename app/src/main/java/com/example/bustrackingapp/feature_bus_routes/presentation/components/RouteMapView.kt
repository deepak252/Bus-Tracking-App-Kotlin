package com.example.bustrackingapp.feature_bus_routes.presentation.components

import android.content.Context
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ZoomControls
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import com.example.bustrackingapp.R
import com.example.bustrackingapp.core.presentation.components.addMarker
import com.example.bustrackingapp.core.presentation.components.createPolyline
import com.example.bustrackingapp.core.presentation.components.initMap
import com.example.bustrackingapp.core.presentation.components.mapEventsOverlay
import com.example.bustrackingapp.core.presentation.components.rememberMapView
import com.example.bustrackingapp.feature_bus_stop.domain.model.BusStopWithDuration
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun RouteMapView(
    modifier: Modifier = Modifier,
    busStops : List<BusStopWithDuration> = emptyList()
) {
    val context = LocalContext.current
    // Initialize osmdroid configuration
    Configuration.getInstance().load(context,context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
    // Create and configure the MapView
    val mapView = rememberMapView(context)

    // Return the MapView as a Composable
    AndroidView(
        factory = { mapView },
        modifier = modifier.fillMaxSize()

    ) { view ->
        view.apply {
            initMap(this)

            val defaultGeoPoint = GeoPoint(28.6798,77.0927)
//            val defaultGeoPoint = GeoPoint(28.6670366,77.0727499)

            val eventsOverlay = mapEventsOverlay(
                context, view,
                onTap = { geoPoint2 ->
//                    addMarker(context, view,geoPoint2)
                }
            )

            drawRoute(context, mapView,busStops)

            view.overlays.add(0, eventsOverlay)
            view.controller.setCenter(defaultGeoPoint)

        }
    }
}

private fun drawRoute( context : Context, mapView : MapView, busStops : List<BusStopWithDuration>){
    if(busStops.isEmpty()){
        return
    }
    var prevRoute = busStops[0]
    busStops.forEach{ currRoute->
        val startPoint = GeoPoint(prevRoute.stop.location.lat, prevRoute.stop.location.lng)
        val endPoint = GeoPoint(currRoute.stop.location.lat, currRoute.stop.location.lng)
        val polyline = createPolyline(mapView,startPoint, endPoint )
        mapView.overlays.add(polyline)
        addMarker(
            context = context,
            mapView = mapView,
            geoPoint = endPoint,
            snippet = "<b>${currRoute.stop.stopNo}<b><br>${currRoute.stop.name}"
        )
        prevRoute = currRoute
    }

}

private fun createZoomButton(context: Context, ): ImageButton {
    return ImageButton(context).apply {
        setImageDrawable(ResourcesCompat.getDrawable(
            resources,
            R.drawable.bus_stop,
            null
        ))
        setBackgroundColor(Color.Transparent.toArgb())
        // Set any additional properties or styling for the button if needed
        // For example, you can set padding, size, margins, etc.
    }
}