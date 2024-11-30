package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Colors // 사용자 정의 Colors import

@Composable
fun InquiryScreen(
    onNavigateBack: () -> Unit,
    onSubmitInquiry: (String, String, String) -> Unit // 제목, 이메일, 내용 전달
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var content by remember { mutableStateOf(TextFieldValue("")) }
    var isSubmitted by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = Colors.Background, // 배경화면 색상
        topBar = {
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
                        text = "문의사항",
                        fontSize = 20.sp,
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
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isSubmitted) {
                Text(
                    text = "문의사항이 접수되었습니다. 감사합니다!",
                    color = Colors.Title, // 접수 완료 텍스트 색상
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                // 제목 입력
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("문의 제목", color = Colors.Placeholder) }, // Placeholder 색상
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Colors.TextField, // 텍스트 색상
                        focusedBorderColor = Colors.TextField,
                        unfocusedBorderColor = Colors.TextField,
                        cursorColor = Colors.TextField
                    )
                )

                // 이메일 입력
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("답변 받을 이메일 주소", color = Colors.Placeholder) }, // Placeholder 색상
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Colors.TextField, // 텍스트 색상
                        focusedBorderColor = Colors.TextField,
                        unfocusedBorderColor = Colors.TextField,
                        cursorColor = Colors.TextField
                    )
                )

                // 문의 내용 입력
                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("문의 내용", color = Colors.Placeholder) }, // Placeholder 색상
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 5,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Colors.TextField, // 텍스트 색상
                        focusedBorderColor = Colors.TextField,
                        unfocusedBorderColor = Colors.TextField,
                        cursorColor = Colors.TextField
                    )
                )

                // 문의 접수 버튼
                Button(
                    onClick = {
                        onSubmitInquiry(title.text, email.text, content.text)
                        isSubmitted = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Colors.Button // 버튼 배경색
                    )
                ) {
                    Text(
                        text = "문의 접수",
                        color = Colors.ButtonText // 버튼 텍스트 색상
                    )
                }
            }
        }
    }
}
