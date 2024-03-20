package com.jonasvgt.guidepostmapper.osmmap.ui.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetGuidepostEditor(viewModel: GuidepostEditorViewModel) {
    val editedNode by viewModel.editedNode.collectAsState()

    if (editedNode == null) {
        return
    }

    ModalBottomSheet(
        onDismissRequest = { viewModel.hide() },
    ) {
        Column {
            NameTextField(editedNode!!.name.orEmpty(),
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                onNameChange = {})
            ElevationTextField(editedNode!!.elevation.orEmpty(),
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                onElevationChange = {})

        }

        Spacer(modifier = Modifier.height(40.dp))
    }

}

@Composable
private fun NameTextField(
    name: String, modifier: Modifier = Modifier, onNameChange: (String) -> Unit
) {

    OutlinedTextField(modifier = modifier,
        value = name,
        onValueChange = onNameChange,
        label = { Text("Name") })
}

@Composable
private fun ElevationTextField(
    elevation: String, modifier: Modifier = Modifier, onElevationChange: (String) -> Unit
) {

    OutlinedTextField(modifier = modifier,
        value = elevation,
        onValueChange = onElevationChange,
        label = { Text("Elevation") })
}