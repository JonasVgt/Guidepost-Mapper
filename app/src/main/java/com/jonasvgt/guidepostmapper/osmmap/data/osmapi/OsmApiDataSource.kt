package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import de.westnordost.osmapi.map.handler.MapDataHandler
import org.osmdroid.api.IGeoPoint

class OsmApiDataSource(private val osmApi: OsmApi = OsmApi()) {

    fun fetchMapData(
        geoPoint: IGeoPoint,
        mapDataHandler: MapDataHandler,

        ) {
        osmApi.connect()
        osmApi.fetchMapData(geoPoint, mapDataHandler)
    }
}