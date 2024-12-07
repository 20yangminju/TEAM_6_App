package com.example.myapplication


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.myapplication.ui.theme.Colors

// 장치 고유 번호 등록 화면
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceRegistration(navController: NavHostController, context: Context, done: () -> Unit) {
    var deviceId by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.navigate("RegisterCar")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                        tint = Colors.IconButton
                    )
                }
                Text(
                    "장치 고유 번호 등록",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Colors.Title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Colors.Divider, thickness = 2.dp, modifier = Modifier.fillMaxWidth())

            // 상단에 요소들이 오도록 수정
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp), // 상단에 여백을 추가하여 위로 올림
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start // 왼쪽 정렬로 변경
            ) {
                Text(
                    "장치 고유 번호를 등록해야만 이용가능합니다.",
                    fontSize = 30.sp,
                    color = Colors.ButtonText,
                    lineHeight = 40.sp
                )
                Spacer(modifier = Modifier.height(150.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "장치 고유 번호",
                        color = Colors.Label,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                TextField(
                    value = deviceId,
                    onValueChange = {deviceId = it},
                    label = {
                        Text("장치의 고유 번호를 입력해주세요.", color = Colors.Placeholder)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Colors.TextField,
                        focusedIndicatorColor = Colors.Placeholder,
                        unfocusedIndicatorColor = Colors.Placeholder,
                        textColor = Colors.Text
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { done() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.Button),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text("장치 고유 번호 등록", color = Colors.ButtonText)
                }
            }
        }
    }
}

