package com.jonasvgt.guidepostmapper.osmmap

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory

class MapStyle {

    val tileSource: OnlineTileSourceBase = TileSourceFactory.MAPNIK
        get() = field

    companion object {
        val DEFAULT: MapStyle = MapStyle()
    }

}