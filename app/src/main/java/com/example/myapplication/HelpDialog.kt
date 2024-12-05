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
                    "배터리 과열이 발생했을 때:\n\n" +
                            "1. 차량을 즉시 멈추고, 시동을 끕니다.\n" +
                            "2. 차량의 주변 온도가 높은 경우, 서늘한 곳으로 이동하십시오.\n" +
                            "3. 배터리 모듈을 점검하고 필요시 전문가의 도움을 요청하십시오."
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
