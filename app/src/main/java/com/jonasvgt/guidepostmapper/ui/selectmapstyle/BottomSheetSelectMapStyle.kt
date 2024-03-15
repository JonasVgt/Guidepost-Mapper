package com.jonasvgt.guidepostmapper.ui.selectmapstyle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jonasvgt.guidepostmapper.ui.osmmap.MapStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetSelectMapStyle(show: Boolean, onDismissRequest : () -> Unit, onMapStyleSelected : (MapStyle) -> Unit) {
    if(show){
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
        ) {
            LazyColumn {
                itemsIndexed(MapStyle.ALL) { _, item ->
                    Text(item.name,
                        modifier = Modifier
                            .padding(20.dp)
                            .clickable { onMapStyleSelected(item) })
                    Divider()
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }

}