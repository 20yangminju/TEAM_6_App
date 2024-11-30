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
fun NoticeScreen(onNavigateBack: () -> Unit) {
    val noticeList = listOf(
        "2024-11-30: 앱 업데이트 v2.0 출시" to "새로운 기능으로 충전소 검색 및 길찾기 기능이 추가되었습니다.",
        "2024-11-15: 서버 점검 안내" to "2024-11-20 00:00 ~ 03:00까지 서버 점검이 예정되어 있습니다.",
        "2024-10-01: FAQ 페이지 추가" to "FAQ 화면을 통해 자주 묻는 질문과 답변을 확인할 수 있습니다."
    )

    Scaffold(
        backgroundColor = Colors.Background // 배경화면 색상
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Title 및 Divider
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp)) // 화면 위와 title 간격
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로 가기",
                            tint = Colors.IconButton // 화살표 아이콘 색상
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp)) // 아이콘과 타이틀 간 간격
                    Text(
                        text = "공지사항",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Colors.Title // 타이틀 텍스트 색상
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Divider(
                    color = Colors.Divider, // Divider 색상
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Divider와 LazyColumn 사이 Spacer 추가
            Spacer(modifier = Modifier.height(16.dp))

            // 공지사항 리스트
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(noticeList) { notice ->
                    Card(
                        elevation = 4.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // 리스트 제목
                            Text(
                                text = "📌 ${notice.first}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            // 리스트 내용
                            Text(
                                text = notice.second,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
