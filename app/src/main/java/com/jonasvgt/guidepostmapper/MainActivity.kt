package com.jonasvgt.guidepostmapper

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.jonasvgt.guidepostmapper.osmmap.data.osmapi.OsmApiDataSource
import com.jonasvgt.guidepostmapper.osmmap.data.osmapi.OsmMapRepository
import com.jonasvgt.guidepostmapper.osmmap.ui.downloadosmdata.FabDownloadOsmData
import com.jonasvgt.guidepostmapper.osmmap.ui.editor.BottomSheetGuidepostEditor
import com.jonasvgt.guidepostmapper.osmmap.ui.editor.GuidepostEditorViewModel
import com.jonasvgt.guidepostmapper.osmmap.ui.osmmap.GuidepostOverlay
import com.jonasvgt.guidepostmapper.osmmap.ui.osmmap.MapStyle
import com.jonasvgt.guidepostmapper.osmmap.ui.osmmap.OsmMapView
import com.jonasvgt.guidepostmapper.osmmap.ui.selectmapstyle.BottomSheetSelectMapStyle
import com.jonasvgt.guidepostmapper.osmmap.ui.selectmapstyle.FabMapStyle
import com.jonasvgt.guidepostmapper.ui.theme.GuidepostMapperTheme
import com.jonasvgt.guidepostmapper.osmmap.ui.tomyposition.FabToMyPosition
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.OverlayItem
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MainActivity : ComponentActivity() {

    private val guidepostEditorViewModel : GuidepostEditorViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        org.osmdroid.config.Configuration.getInstance().userAgentValue =
            applicationContext.packageName
        val locationManager = GpsMyLocationProvider(this)
        val mapView = MapView(this).apply {
            setMultiTouchControls(true)
            overlays.add(MyLocationNewOverlay(locationManager, this).apply { enableMyLocation() })
            controller.setZoom(9.5)
            controller.setCenter(GeoPoint(48.8583, 2.2944))
        }
        val overlay = GuidepostOverlay(this)
        mapView.overlays.add(overlay)
        val osmMapRepository = OsmMapRepository(OsmApiDataSource())

        GlobalScope.launch {
            osmMapRepository.guidepostFlow.collectLatest { it ->
                overlay.removeAllItems()
                overlay.addItems(it.values.map { node ->
                    OverlayItem(
                        "node", "desc", GeoPoint(
                            node.position.latitude, node.position.longitude
                        )
                    )
                })
            }
        }

        setContent {
            GuidepostMapperTheme {
                var showBottomSheet by remember { mutableStateOf(false) }
                var mapStyle by remember { mutableStateOf(MapStyle.DEFAULT) }

                Scaffold(floatingActionButtonPosition = FabPosition.End, floatingActionButton = {
                    Column {
                        FabDownloadOsmData(onClick = {
                            val location = mapView.mapCenter
                            if (location != null) {
                                osmMapRepository.downloadMapDataAt(location)
                            }

                        })
                        Spacer(modifier = Modifier.height(20.dp))
                        FabMapStyle(onClick = { showBottomSheet = true })
                        Spacer(modifier = Modifier.height(20.dp))
                        FabToMyPosition(onClick = {
                            val loc = locationManager.lastKnownLocation
                            if (loc == null) {
                                //Toast.makeText(this, "No Last Location", Toast.LENGTH_SHORT).show()
                            } else {
                                mapView.controller.animateTo(GeoPoint(loc))
                            }

                        })
                    }

                }) { innerPadding ->
                    OsmMapView(mapView, style = mapStyle, modifier = Modifier.padding(innerPadding))
                    BottomSheetSelectMapStyle(show = showBottomSheet,
                        onDismissRequest = { showBottomSheet = false },
                        onMapStyleSelected = { newStyle ->
                            mapStyle = newStyle; showBottomSheet = false
                        })
                    BottomSheetGuidepostEditor(viewModel = guidepostEditorViewModel)
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




