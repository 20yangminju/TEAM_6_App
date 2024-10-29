package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat

// 조건에 맞춰 알림이 뜨도록 함(트리거)
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
