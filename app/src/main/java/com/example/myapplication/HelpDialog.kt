package com.example.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HelpDialog(showHelp: Boolean, onDismiss: () -> Unit) {
    if (showHelp) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("도움말") },
            text = {
                Text(
                    "제목:\n\n" +
                            "방법"
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("확인")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
