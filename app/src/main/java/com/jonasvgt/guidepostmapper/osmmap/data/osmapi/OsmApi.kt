package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import android.location.Location
import de.westnordost.osmapi.OsmConnection
import de.westnordost.osmapi.map.MapDataApi
import de.westnordost.osmapi.map.data.BoundingBox
import de.westnordost.osmapi.map.data.Element
import de.westnordost.osmapi.map.handler.MapDataHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.api.IGeoPoint
import org.osmdroid.util.GeoPoint
import java.util.Locale


class OsmApi {


    private var connectionDown: OsmConnection? = null
    private var connectionUp: OsmConnection? = null

    private var mapApiDown: MapDataApi? = null
    private var mapApiUp: MapDataApi? = null


    fun connect() {
        if (connectionDown == null){
            connectionDown = OsmConnection(
                API_URL_DOWN, "GuidepostMapper", null
            )
        }

        if (connectionUp == null){
            connectionUp = OsmConnection(
                API_URL_UP, "GuidepostMapper", null
            )
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchMapData(boundingBox: BoundingBox, dataHandler: MapDataHandler) {
        if (connectionDown == null) return

        if (mapApiDown == null) mapApiDown = MapDataApi(connectionDown)

        GlobalScope.launch(Dispatchers.IO) {
            mapApiDown?.getMap(boundingBox, dataHandler)
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

    suspend fun openChangeset() : Long = withContext(Dispatchers.IO) {
        if (mapApiUp == null) mapApiUp = MapDataApi(connectionUp)

        mapApiUp!!.openChangeset(
            mapOf(
                "comment" to "test comment",
                "created_by" to "GuidepostMapper",
                "locale" to Locale.getDefault().toLanguageTag(),
                "source" to "none:)"
            )
        )
    }

    fun uploadChanges(changesetId : Long, elements : Iterable<Element>){
        if (mapApiUp == null) mapApiUp = MapDataApi(connectionUp)
        GlobalScope.launch(Dispatchers.IO) {
            mapApiUp!!.uploadChanges(changesetId, elements) {}
        }
    }

    companion object {
        val API_URL_DOWN = "https://openstreetmap.org/api/0.6/"
        val API_URL_UP = "https://master.apis.dev.openstreetmap.org/api/0.6/"
    }
}