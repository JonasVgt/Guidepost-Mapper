package com.jonasvgt.guidepostmapper.osmmap

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.TileSourceFactory

class MapStyle(val name: String, val tileSource: OnlineTileSourceBase = TileSourceFactory.MAPNIK) {

    companion object {
        val DEFAULT: MapStyle = MapStyle(name="Default", TileSourceFactory.MAPNIK)
        val HIKING: MapStyle = MapStyle(name="Hiking",tileSource = TileSourceFactory.HIKEBIKEMAP)
        val ALL = arrayOf(this.DEFAULT, this.HIKING)
    }

}