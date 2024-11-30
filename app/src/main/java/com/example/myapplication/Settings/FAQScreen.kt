package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Colors // 사용자 정의 Colors import

@Composable
fun FAQScreen(onNavigateBack: () -> Unit) {
    val faqList = listOf(
        "배터리 온도는 어떻게 측정되나요?" to "앱에 차량과 연동된 데이터를 통해 실시간으로 배터리 온도를 확인할 수 있습니다.",
        "셀 밸런스 측정은 무엇인가요?" to "셀 밸런스는 배터리 셀 간 전압 차이를 측정하여 효율성을 보장합니다.",
        "충전 현황 알림은 어떻게 작동하나요?" to "충전기가 차량에 연결되면 실시간으로 충전 상태를 앱에 표시합니다."
    )

    Scaffold(
        backgroundColor = Colors.Background // 배경색 설정
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Title 및 아이콘 영역
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp) // 위, 아래 간격 동일하게 설정
            ) {
                // 뒤로 가기 아이콘
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                        tint = Colors.IconButton // 화살표 아이콘 색상
                    )
                }
                Spacer(modifier = Modifier.width(8.dp)) // 아이콘과 타이틀 간 간격
                // 타이틀 텍스트
                Text(
                    text = "FAQ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Colors.Title // 타이틀 텍스트 색상
                )
            }

            // Title 아래 Divider
            Divider(
                color = Colors.Divider,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp) // Divider의 좌우 패딩 설정
            )

            // FAQ 항목 리스트
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(faqList) { faq ->
                    Card(
                        elevation = 4.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // 질문 텍스트
                            Text(
                                text = "Q: ${faq.first}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // 답변 텍스트
                            Text(
                                text = "A: ${faq.second}",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
