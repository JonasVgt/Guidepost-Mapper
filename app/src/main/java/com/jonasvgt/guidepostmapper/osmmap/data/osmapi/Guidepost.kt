package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import de.westnordost.osmapi.map.data.Node

data class Guidepost(val node: Node) {

    val elevation = node.tags["ele"]
    val name = node.tags["name"]
}