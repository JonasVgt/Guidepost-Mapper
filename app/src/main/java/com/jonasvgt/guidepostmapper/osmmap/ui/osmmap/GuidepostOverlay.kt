package com.jonasvgt.guidepostmapper.osmmap.ui.osmmap

import android.content.Context
import org.osmdroid.views.overlay.ItemizedIconOverlay

class GuidepostOverlay(context: Context, items: ArrayList<GuidepostOverlayItem> = ArrayList()) :
    ItemizedIconOverlay<GuidepostOverlayItem>(context, items, onItemGestureListener)

private val onItemGestureListener =
    object : ItemizedIconOverlay.OnItemGestureListener<GuidepostOverlayItem> {
        override fun onItemSingleTapUp(index: Int, item: GuidepostOverlayItem): Boolean {
            return false
        }

        override fun onItemLongPress(index: Int, item: GuidepostOverlayItem): Boolean {
            return false
        }
    }