package com.jonasvgt.guidepostmapper.data.osmapi

import de.westnordost.osmapi.map.data.BoundingBox
import de.westnordost.osmapi.map.data.Node
import de.westnordost.osmapi.map.data.Relation
import de.westnordost.osmapi.map.data.Way
import de.westnordost.osmapi.map.handler.MapDataHandler

class GuidepostHandler(private val handleGuidePost: (Node?) -> Unit, private val handleDestinationSign: (Relation?) -> Unit) : MapDataHandler {

    override fun handle(bounds: BoundingBox?) {}

    override fun handle(node: Node?) {
        if (node?.tags?.get("information") == "guidepost") {
            handleGuidePost(node)
        }
    }

    override fun handle(way: Way?) {}

    override fun handle(relation: Relation?) {
        if (relation?.tags?.get("type") == "destination_sign"){
            handleDestinationSign(relation)
        }
    }


}