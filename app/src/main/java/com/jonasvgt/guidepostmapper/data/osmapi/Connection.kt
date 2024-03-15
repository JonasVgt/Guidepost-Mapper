package com.jonasvgt.guidepostmapper.data.osmapi

import de.westnordost.osmapi.OsmConnection
import de.westnordost.osmapi.map.MapDataApi
import de.westnordost.osmapi.map.data.BoundingBox
import de.westnordost.osmapi.map.handler.MapDataHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Connection {


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

    companion object {
        val API_URL = "https://master.apis.dev.openstreetmap.org/api/0.6/"
    }
}