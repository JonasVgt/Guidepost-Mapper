package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import de.westnordost.osmapi.map.data.Element
import de.westnordost.osmapi.map.handler.MapDataHandler
import kotlinx.coroutines.runBlocking
import org.osmdroid.api.IGeoPoint

class OsmApiDataSource(private val osmApi: OsmApi = OsmApi()) {

    fun fetchMapData(
        geoPoint: IGeoPoint,
        mapDataHandler: MapDataHandler,

        ) {
        osmApi.connect()
        osmApi.fetchMapData(geoPoint, mapDataHandler)
    }


    fun openChangeset() : Long{
        var id: Long
        runBlocking {
            id =  osmApi.openChangeset()
        }
        return id
    }

    fun uploadChangeset(changesetId : Long, elements : Iterable<Element>){
        return osmApi.uploadChanges(changesetId, elements)
    }
}