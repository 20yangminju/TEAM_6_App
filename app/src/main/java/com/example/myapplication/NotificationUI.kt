package com.example.myapplication

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 화면에 뜨는 알림 UI
@Composable
fun ShowAlertDialog(showDialog: Boolean, onDismiss: () -> Unit) {
    if(showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text(text = "배터리 과열로 인한 충전 종료", style = MaterialTheme.typography.headlineSmall)
            },
            text = {
                Text(
                    text = "배터리 과열로 인해 충전이 비정상적으로 종료되었습니다. 차량을 확인해주세요.",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = {onDismiss()}) {
                    Text("닫기")
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
    }
}

// 조건에 맞춰 알림이 뜨도록 함(트리거) → 상태 표시줄에 적용
fun createNotification(context: Context) {
    val channelId = "channel_id"
    val channelName = "Default Channel"
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val channel =
            // 헤드업 알림이 뜨게끔 중요도 설정
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
                enableVibration(true)
            }
        notificationManager.createNotificationChannel(channel)
    }

    val notificationId = 1

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground) // 알림 앱 아이콘
        .setContentTitle("EV-PrepareCareFully") // 앱 이름 설정
        .setContentText("배터리 온도가 너무 높습니다.") // 임시 텍스트
        .setPriority(NotificationCompat.PRIORITY_HIGH) // 헤드업 알림이 뜨게끔 설정
        .setAutoCancel(true)
        .build()

    notificationManager.notify(notificationId, notification)
}
