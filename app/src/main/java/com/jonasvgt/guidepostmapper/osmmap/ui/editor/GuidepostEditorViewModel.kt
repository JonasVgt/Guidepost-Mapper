package com.jonasvgt.guidepostmapper.osmmap.ui.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonasvgt.guidepostmapper.osmmap.data.osmapi.Guidepost
import com.jonasvgt.guidepostmapper.osmmap.data.osmapi.GuidepostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class GuidepostEditorViewModel(private val guidepostRepository: GuidepostRepository) : ViewModel() {

    private val _editedNode: MutableStateFlow<Guidepost?> = MutableStateFlow(null)
    val editedNode = _editedNode.asStateFlow()

    fun show(nodeId: Long) {
        viewModelScope.launch {
            guidepostRepository.guideposts.collectLatest {
                _editedNode.emit(it[nodeId])
            }
        }
    }

    fun hide() {
        _editedNode.value = null
    }


}