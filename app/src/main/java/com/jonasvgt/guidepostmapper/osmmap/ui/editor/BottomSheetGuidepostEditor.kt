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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetGuidepostEditor(viewModel: GuidepostEditorViewModel) {
    if (viewModel.show) {
        ModalBottomSheet(
            onDismissRequest = {},
        ) {
            Column {
                NameTextField(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                )
                ElevationTextField(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                )

            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

}

@Composable
private fun NameTextField(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(modifier = modifier,
        value = text,
        onValueChange = { text = it },
        label = { Text("Name") })
}

@Composable
private fun ElevationTextField(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(modifier = modifier,
        value = text,
        onValueChange = { text = it },
        label = { Text("Elevation") })
}