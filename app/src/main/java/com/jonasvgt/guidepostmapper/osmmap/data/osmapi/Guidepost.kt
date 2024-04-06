package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import de.westnordost.osmapi.map.data.LatLon
import de.westnordost.osmapi.map.data.Node

data class Guidepost(
    val id: Long,
    val name: String?,
    val elevation: String?,
    val position: LatLon
) {

    constructor(node: Node) : this(node.id, node.tags["name"], node.tags["ele"], node.position)
}