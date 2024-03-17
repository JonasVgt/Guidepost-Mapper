package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import android.location.Location
import de.westnordost.osmapi.OsmConnection
import de.westnordost.osmapi.map.MapDataApi
import de.westnordost.osmapi.map.data.BoundingBox
import de.westnordost.osmapi.map.handler.MapDataHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.osmdroid.api.IGeoPoint
import org.osmdroid.util.GeoPoint


class OsmApi {


    private var connection: OsmConnection? = null
    private var mapApi: MapDataApi? = null


    fun connect() {
        if (connection != null) return

        connection = OsmConnection(
            API_URL, "GuidepostMapper", null
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchMapData(boundingBox: BoundingBox, dataHandler: MapDataHandler) {
        if (connection == null) return

        if (mapApi == null) mapApi = MapDataApi(connection)

        GlobalScope.launch(Dispatchers.IO) {
            mapApi?.getMap(boundingBox, dataHandler)
        }
    }

    fun fetchMapData(location: Location, dataHandler: MapDataHandler, size: Double = 0.01) {
        fetchMapData(geoPoint = GeoPoint(location), dataHandler, size)
    }

    fun fetchMapData(geoPoint: IGeoPoint, dataHandler: MapDataHandler, size: Double = 0.01) {
        val boundingBox = BoundingBox(
            geoPoint.latitude - size * 0.5,
            geoPoint.longitude - size * 0.5,
            geoPoint.latitude + size * 0.5,
            geoPoint.longitude + size * 0.5
        )
        fetchMapData(boundingBox, dataHandler)
    }

    companion object {
        val API_URL = "https://master.apis.dev.openstreetmap.org/api/0.6/"
    }
}