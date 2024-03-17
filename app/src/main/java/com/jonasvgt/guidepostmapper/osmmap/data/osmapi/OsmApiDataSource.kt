package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import de.westnordost.osmapi.map.data.Node
import de.westnordost.osmapi.map.data.Relation
import org.osmdroid.api.IGeoPoint

class OsmApiDataSource(private val osmApi: OsmApi = OsmApi()) {

    fun fetchMapData(
        geoPoint: IGeoPoint,
        handleGuidePost: (Node?) -> Unit,
        handleDestinationSign: (Relation?) -> Unit
    ) {
        osmApi.connect()
        osmApi.fetchMapData(geoPoint, GuidepostHandler(handleGuidePost, handleDestinationSign))
    }
}