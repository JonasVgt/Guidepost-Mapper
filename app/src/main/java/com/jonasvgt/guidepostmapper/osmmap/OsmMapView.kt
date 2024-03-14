package com.jonasvgt.guidepostmapper.osmmap

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.api.IGeoPoint
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@Composable
fun OsmMapView(mapCenter : IGeoPoint, locationManager: GpsMyLocationProvider, onMapCenterChanged: (IGeoPoint) -> Unit, modifier: Modifier = Modifier) {

    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                overlays.add(MyLocationNewOverlay(locationManager, this).apply { enableMyLocation() })
                controller.setZoom(9.5)
                controller.setCenter(mapCenter)
            }
        },
        update = {
            it.controller.setCenter(mapCenter)
            onMapCenterChanged(it.mapCenter)
        }
    )
}