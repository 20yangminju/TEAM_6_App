package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NearbyStationsDialog(
    stationList: List<String>,
    onStationSelect: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "근처 충전소 목록") },
        text = {
            LazyColumn {
                items(stationList.size) { index ->
                    val station = stationList[index]
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onStationSelect(station.split("-")[0])
                                onDismiss()
                            }
                            .padding(8.dp)
                    ) {
                        Column {
                            Text(text = station.split("-")[0], fontWeight = FontWeight.Bold)
                            Text(
                                text = station.split("-")[1],
                                fontSize = 12.sp,
                                color = androidx.compose.ui.graphics.Color.Gray
                            )
                            Text(
                                text = station.split("-")[2],
                                fontSize = 12.sp,
                                color = androidx.compose.ui.graphics.Color.Gray
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {}
    )
}
