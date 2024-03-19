package com.jonasvgt.guidepostmapper.osmmap.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jonasvgt.guidepostmapper.osmmap.data.osmapi.OsmMapRepository

class GuidepostEditorViewModelFactory(
    private val mParam: OsmMapRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GuidepostEditorViewModel(mParam) as T
    }
}