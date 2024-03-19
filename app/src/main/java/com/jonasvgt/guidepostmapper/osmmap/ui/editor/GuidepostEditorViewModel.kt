package com.jonasvgt.guidepostmapper.osmmap.ui.editor

import androidx.lifecycle.ViewModel

class GuidepostEditorViewModel : ViewModel() {

    var show = false
        private set

    fun launch(){
        show = true
    }

}