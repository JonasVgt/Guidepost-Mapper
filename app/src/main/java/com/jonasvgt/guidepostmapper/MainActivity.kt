package com.jonasvgt.guidepostmapper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.jonasvgt.guidepostmapper.osmmap.OsmMapView
import com.jonasvgt.guidepostmapper.ui.theme.GuidepostMapperTheme
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        org.osmdroid.config.Configuration.getInstance().userAgentValue =
            applicationContext.packageName
        val locationManager = GpsMyLocationProvider(this)
        val mapView = MapView(this).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            overlays.add(MyLocationNewOverlay(locationManager, this).apply { enableMyLocation() })
            controller.setZoom(9.5)
            controller.setCenter(GeoPoint(48.8583, 2.2944))
        }
        setContent {
            GuidepostMapperTheme {
                Scaffold(floatingActionButtonPosition = FabPosition.End, floatingActionButton = {
                    Column {
                        FabToMapSource {

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        FabToMyLocation(onClick = {
                            val loc = locationManager.lastKnownLocation
                            if (loc == null) {
                                //Toast.makeText(this, "No Last Location", Toast.LENGTH_SHORT).show()
                            } else {
                                mapView.controller.animateTo(GeoPoint(loc))
                            }

                        })
                    }

                }) { innerPadding ->
                    OsmMapView(mapView, modifier = Modifier.padding(innerPadding))
                }
            }
        }

        val requestLocationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Location permission was granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Location permission was denied", Toast.LENGTH_SHORT).show()
            }
        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show()
        } else {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }


    }

}


@Composable
fun FabToMyLocation(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
    ) {
        Icon(Icons.Filled.LocationOn, "Return to my location.")
    }
}

@Composable
fun FabToMapSource(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
    ) {
        Icon(Icons.Filled.List, "Select map source.")
    }
}