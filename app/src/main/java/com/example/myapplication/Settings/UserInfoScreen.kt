package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.Colors // 사용자 정의 Colors import

@Composable
fun UserInfoScreen(navController: NavHostController) {
    // 더미 데이터
    val email = remember { "user@example.com" }
    val password = remember { "********" }
    val ownerName = remember { "홍길동" }
    val carModel = remember { "Tesla Model 3" }
    val carNumber = remember { "12가 3456" }

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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로 가기",
                            tint = Colors.IconButton // 화살표 색상
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp)) // 아이콘과 타이틀 간 간격
                    Text(
                        text = "회원 정보 관리",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Colors.Title // 타이틀 텍스트 색상
                    )
                }
                Spacer(modifier = Modifier.height(12.dp)) // 화면 위와 title 간격
                Divider(
                    color = Colors.Divider,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 사용자 정보 입력 및 버튼
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 이메일
                UserInfoField(label = "이메일", value = email)

                // 비밀번호 확인
                UserInfoField(label = "비밀번호 확인", value = password)

                // 소유자명
                UserInfoField(label = "소유자명", value = ownerName)

                // 차종
                UserInfoField(label = "차종", value = carModel)

                // 차량번호
                UserInfoField(label = "차량번호", value = carNumber)

                // 장치 등록 버튼
                Button(
                    onClick = { navController.navigate("RegisterCar") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Colors.Button // 버튼 배경색
                    )
                ) {
                    Text(
                        text = "장치 등록",
                        color = Colors.ButtonText // 버튼 내부 텍스트 색상
                    )
                }
            }
        }
    }
}

@Composable
fun UserInfoField(label: String, value: String) {
    Column {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.Title, // 텍스트 색상
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = {}, // 읽기 전용 필드
            enabled = false, // 입력 불가능
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Colors.Placeholder, // 텍스트 필드 텍스트 색상
                focusedBorderColor = Colors.TextField,
                unfocusedBorderColor = Colors.TextField,
                cursorColor = Colors.TextField,
                placeholderColor = Colors.Placeholder // Placeholder 색상
            )
        )
    }
}
