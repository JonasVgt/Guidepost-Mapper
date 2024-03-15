package com.jonasvgt.guidepostmapper.data.osmapi

import de.westnordost.osmapi.OsmConnection


class Connection {


    private var connection : OsmConnection? = null

    fun connect() {
        if (connection != null)
            return

        connection = OsmConnection(
            API_URL, "GuidepostMapper", null
        )
    }

    companion object {
        val API_URL = "https://master.apis.dev.openstreetmap.org/api/0.6/"
    }
}