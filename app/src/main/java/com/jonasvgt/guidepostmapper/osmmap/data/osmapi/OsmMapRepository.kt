package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import de.westnordost.osmapi.map.data.BoundingBox
import de.westnordost.osmapi.map.data.Node
import de.westnordost.osmapi.map.data.Relation
import de.westnordost.osmapi.map.data.Way
import de.westnordost.osmapi.map.handler.MapDataHandler
import org.osmdroid.api.IGeoPoint

class OsmMapRepository(
    private val dataSource: OsmApiDataSource,
    private val onNewData: (OsmMapRepository) -> Unit
) {

    private val nodes: MutableMap<Long, Node> = HashMap()
    private val ways: MutableMap<Long, Way> = HashMap()
    private val relations: MutableMap<Long, Relation> = HashMap()

    fun getGuideposts(): List<Node> {
        return nodes.values.filter { node -> node.tags.get("information") == "guidepost" }
    }

    fun downloadMapDataAt(geoPoint: IGeoPoint) {
        val dataHandler = object : MapDataHandler {
            override fun handle(bounds: BoundingBox?) {}

            override fun handle(node: Node?) {
                if (node == null) return
                nodes[node.id] = node
                onNewData(this@OsmMapRepository)
            }

            override fun handle(way: Way?) {
                if (way == null) return
                ways[way.id] = way
                onNewData(this@OsmMapRepository)
            }

            override fun handle(relation: Relation?) {
                if (relation == null) return
                relations[relation.id] = relation
                onNewData(this@OsmMapRepository)
            }
        }

        dataSource.fetchMapData(geoPoint, dataHandler)
    }

}