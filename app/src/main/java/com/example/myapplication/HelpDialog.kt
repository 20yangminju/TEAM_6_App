package com.example.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Colors
import androidx.compose.foundation.layout.height

@Composable
fun HelpDialog(showHelp: Boolean, onDismiss: () -> Unit, isToHot: Boolean) {
    if (showHelp) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = "이상온도가 미치는 영향",
                    color = Colors.Title,
                    fontSize = 25.sp,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Card(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(15.dp)
                    ) {
                        if (isToHot) {
                            Column {
                                Text(
                                    text = "배터리 내부 화학 반응이 과도해져 배터리 성능이 빨리 떨어지고, 심하면 손상이 생길 수 있어요. 마치 스마트폰을 햇빛 아래 두면 배터리가 빨리 닳는 것과 비슷해요.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "주행 중이라면 차량의 냉각 시스템에 문제가 있을 가능성도 있어요. 이런 경우에는 배터리에 더 큰 손상이 생기기 전에 정비소에 방문해서 점검을 받아보는 게 좋아요!",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "주차 중이라면, 차량을 직사광선이 닿지 않는 시원한 곳으로 이동시켜주세요!",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 16.sp
                                )
                            }
                        } else {
                            Column {
                                Text(
                                    text = "배터리 내부 화학 반응이 느려져 에너지를 잘 못 쓰게 되고, 충전도 제대로 안 되는 경우가 많아요.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "겨울에 전기차 주행 거리가 짧아지는 이유 중 하나예요.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "주차할 때는 실내 주차장에 해주시고, 주행을 시작하기 전 배터리 예열 시스템을 켜서 배터리를 따뜻하게 해주는 게 좋아요.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("확인")
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .width(400.dp) // 가로 크기를 넓게 설정
        )
    }
}
