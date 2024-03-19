package com.jonasvgt.guidepostmapper.osmmap.ui.osmmap

import android.content.Context
import org.osmdroid.views.overlay.ItemizedIconOverlay

class GuidepostOverlay(
    context: Context,
    items: ArrayList<GuidepostOverlayItem> = ArrayList(),
    private val onOpenEditor: (Long) -> Unit
) : ItemizedIconOverlay<GuidepostOverlayItem>(
    context,
    items,
    object : OnItemGestureListener<GuidepostOverlayItem> {
        override fun onItemSingleTapUp(index: Int, item: GuidepostOverlayItem): Boolean {
            onOpenEditor(item.nodeId)
            return false
        }

        override fun onItemLongPress(index: Int, item: GuidepostOverlayItem): Boolean {
            return false
        }
    })