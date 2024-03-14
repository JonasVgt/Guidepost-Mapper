package com.jonasvgt.guidepostmapper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.jonasvgt.guidepostmapper.ui.theme.GuidepostMapperTheme
import org.osmdroid.api.IGeoPoint
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        org.osmdroid.config.Configuration.getInstance().userAgentValue  = applicationContext.packageName
        val locationManager = GpsMyLocationProvider(this)
        setContent {
            var mapCenter by remember { mutableStateOf<IGeoPoint>(GeoPoint(48.8583, 2.2944)) }
            GuidepostMapperTheme {
                Scaffold (
                    floatingActionButton = {
                        FabToMyLocation(onClick = {
                            val loc = locationManager.lastKnownLocation
                            if(loc == null){
                                Toast.makeText(this, "No Last Location", Toast.LENGTH_SHORT).show()
                            }else{
                                mapCenter = GeoPoint(loc)
                            }

                        })
                    }
                )
                {
                    innerPadding -> OsmMapView(mapCenter, locationManager, onMapCenterChanged = {newMapCenter -> mapCenter = newMapCenter }, modifier = Modifier.padding(innerPadding))
                }
            }
        }

        val requestLocationPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(this, "Location permission was granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Location permission was denied", Toast.LENGTH_SHORT).show()
                }
            }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
        }else{
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }


    }

}

@Composable
fun OsmMapView(mapCenter : IGeoPoint, locationManager: GpsMyLocationProvider , onMapCenterChanged: (IGeoPoint) -> Unit, modifier: Modifier = Modifier) {

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

@Composable
fun FabToMyLocation(onClick : () -> Unit ){
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
    ) {
        Icon(Icons.Filled.LocationOn, "Return to my location.")
    }
}