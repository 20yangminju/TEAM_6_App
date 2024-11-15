package com.example.myapplication.resource

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.Colors


// 시간당 배터리 현황 막대 그래프
// x축을 시간, y축을 배터리 현황으로 표시
@Composable
fun BatteryBarChart(data: List<Pair<String, Int>>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(color = Colors.BarChartBackground, shape = RoundedCornerShape(16.dp)) // 흰색 배경과 둥근 모서리
            .padding(8.dp) // 여백 추가
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            val maxBattery = 100 // 배터리 최대 값 (퍼센트)
            val barWidth = size.width / (data.size * 2)
            val spaceBetweenBars = barWidth
            val textPadding =  40f

            data.forEachIndexed { index, entry ->
                val x = index * (barWidth + spaceBetweenBars) // 막대의 X 좌표 계산
                val barHeight = (entry.second / maxBattery.toFloat()) * size.height // 배터리 퍼센트에 따른 높이

                drawRoundRect(
                    color = Colors.BarChart,
                    topLeft = androidx.compose.ui.geometry.Offset(x, size.height - barHeight - textPadding),
                    size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
                )

                // 시간 텍스트 표시 (3시간 간격만 표시)
                if (index % 3 == 0) {
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            entry.first, // 시간
                            x + barWidth / 4,
                            size.height - 5f,
                            android.graphics.Paint().apply {
                                color = android.graphics.Color.DKGRAY
                                textSize = 30f
                                textAlign = android.graphics.Paint.Align.LEFT
                                typeface = android.graphics.Typeface.create(
                                    android.graphics.Typeface.DEFAULT,
                                    android.graphics.Typeface.BOLD // 굵은 폰트 설정
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}