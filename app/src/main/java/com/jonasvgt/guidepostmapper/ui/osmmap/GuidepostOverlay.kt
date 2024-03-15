package com.jonasvgt.guidepostmapper.ui.osmmap

import android.content.Context
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem

class GuidepostOverlay(context: Context, items: ArrayList<OverlayItem> = ArrayList()) :
    ItemizedIconOverlay<OverlayItem>(context, items, object : OnItemGestureListener<OverlayItem> {
        override fun onItemSingleTapUp(index: Int, item: OverlayItem): Boolean {
            return false
        }

        override fun onItemLongPress(index: Int, item: OverlayItem): Boolean {
            return false
        }
    })