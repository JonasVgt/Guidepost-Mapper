package com.jonasvgt.guidepostmapper.osmmap.ui.osmmap

import android.content.Context
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.OverlayItem

class GuidepostOverlay(context: Context, items: ArrayList<OverlayItem> = ArrayList()) :
    ItemizedIconOverlay<OverlayItem>(context, items, onItemGestureListener)

private val onItemGestureListener =
    object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
        override fun onItemSingleTapUp(index: Int, item: OverlayItem): Boolean {
            return false
        }

        override fun onItemLongPress(index: Int, item: OverlayItem): Boolean {
            return false
        }
    }