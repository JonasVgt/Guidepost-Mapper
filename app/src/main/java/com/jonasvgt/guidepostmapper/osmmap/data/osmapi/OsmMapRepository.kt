package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import de.westnordost.osmapi.map.data.BoundingBox
import de.westnordost.osmapi.map.data.Node
import de.westnordost.osmapi.map.data.Relation
import de.westnordost.osmapi.map.data.Way
import de.westnordost.osmapi.map.handler.MapDataHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.osmdroid.api.IGeoPoint

class OsmMapRepository(
    private val dataSource: OsmApiDataSource
) {


    private val _nodes: MutableStateFlow<Map<Long, Node>> = MutableStateFlow(emptyMap())
    val nodes = _nodes.asStateFlow()
    private val _ways: MutableStateFlow<Map<Long, Way>> = MutableStateFlow(emptyMap())
    val ways = _ways.asStateFlow()
    private val _relations: MutableStateFlow<Map<Long, Relation>> = MutableStateFlow(emptyMap())
    val relations = _relations.asStateFlow()

    val guidepostFlow =
        nodes.map { value -> value.filter { entry -> entry.value.tags["information"] == "guidepost" } }

    fun downloadMapDataAt(geoPoint: IGeoPoint) {
        val dataHandler = object : MapDataHandler {
            override fun handle(bounds: BoundingBox?) {}

            override fun handle(node: Node?) {
                if (node == null) return
                _nodes.update { nodes -> nodes.toMutableMap().apply { put(node.id, node) } }
            }

            override fun handle(way: Way?) {
                if (way == null) return
                _ways.update { ways -> ways.toMutableMap().apply { put(way.id, way) } }
            }

            override fun handle(relation: Relation?) {
                if (relation == null) return
                _relations.update { relations ->
                    relations.toMutableMap().apply { put(relation.id, relation) }
                }
            }
        }

        dataSource.fetchMapData(geoPoint, dataHandler)
    }

}