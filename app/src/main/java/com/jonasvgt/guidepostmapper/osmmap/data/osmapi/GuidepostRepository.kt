package com.jonasvgt.guidepostmapper.osmmap.data.osmapi

import de.westnordost.osmapi.map.data.Node
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GuidepostRepository(osmMapRepository: OsmMapRepository) {

    private val _guideposts: MutableStateFlow<Map<Long, Guidepost>> = MutableStateFlow(emptyMap())
    val guideposts = _guideposts.asStateFlow()

    private fun onNodesChanged(nodes: Map<Long, Node>) {
        _guideposts.value = nodes.filter { entry -> entry.value.tags["information"] == "guidepost" }
            .mapValues { entry ->
                Guidepost(entry.value)
            }
    }

    init {
        osmMapRepository.nodesObservers.add { onNodesChanged(it) }
    }

}