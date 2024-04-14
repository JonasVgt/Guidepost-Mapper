package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import de.westnordost.osmapi.map.data.BoundingBox
import de.westnordost.osmapi.map.data.Node
import de.westnordost.osmapi.map.data.Relation
import de.westnordost.osmapi.map.data.Way
import de.westnordost.osmapi.map.handler.MapDataHandler
import org.osmdroid.api.IGeoPoint

class OsmMapRepository(
    private val dataSource: OsmApiDataSource
) {

    private val nodes: MutableMap<Long, Node> = mutableMapOf()
    val nodesObservers = mutableListOf<(Map<Long, Node>) -> Unit>()

    private fun notifyNodeObservers() {
        nodesObservers.forEach { it(nodes) }
    }

    fun downloadMapDataAt(geoPoint: IGeoPoint) {
        val dataHandler = object : MapDataHandler {
            override fun handle(bounds: BoundingBox?) {}

            override fun handle(node: Node?) {
                if (node == null) return
                nodes[node.id] = node
                notifyNodeObservers()
                //_nodes.update { nodes -> nodes.toMutableMap().apply { put(node.id, node) } }
            }

            override fun handle(way: Way?) {
                if (way == null) return
                //_ways.update { ways -> ways.toMutableMap().apply { put(way.id, way) } }
            }

            override fun handle(relation: Relation?) {
                if (relation == null) return
                /*_relations.update { relations ->
                    relations.toMutableMap().apply { put(relation.id, relation) }
                }*/
            }
        }

        dataSource.fetchMapData(geoPoint, dataHandler)
    }

}