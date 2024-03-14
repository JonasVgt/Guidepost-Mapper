package com.jonasvgt.guidepostmapper.osmmap

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.views.MapView

@Composable
fun OsmMapView(mapView: MapView, modifier: Modifier = Modifier, style: MapStyle =  MapStyle.DEFAULT) {
    AndroidView(modifier = modifier, factory = { _ -> mapView.apply { setTileSource(style.tileSource) } }, update = {})
}