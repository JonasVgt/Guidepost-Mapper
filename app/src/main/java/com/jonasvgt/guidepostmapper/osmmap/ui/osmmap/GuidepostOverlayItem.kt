package com.jonasvgt.guidepostmapper.osmmap.ui.osmmap

import org.osmdroid.api.IGeoPoint
import org.osmdroid.views.overlay.OverlayItem

class GuidepostOverlayItem(val nodeId: Long, geoPoint: IGeoPoint) :
    OverlayItem(nodeId.toString(), "description", geoPoint)