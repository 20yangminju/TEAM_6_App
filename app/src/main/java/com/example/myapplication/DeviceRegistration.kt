package com.example.myapplication


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.myapplication.ui.theme.Colors

// 장치 고유 번호 등록 화면
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceRegistration(context: Context, done : () -> Unit) {
    var showToolTip by remember { mutableStateOf(false) }
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
                        //
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
            Divider(color = Colors.Divider,
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth())
            Spacer(modifier = Modifier.height(50.dp))
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "장치 고유 번호를 등록해야만 이용가능합니다.",
                    fontSize = 30.sp,
                    color = Colors.ButtonText,
                    lineHeight = 40.sp
                    )
                Spacer(modifier = Modifier.height(80.dp))
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
                Spacer(modifier = Modifier.height(10.dp))

//                Box { // 툴팁 부분은 추후에 수정할 예정입니다..
//                    Icon(
//                        imageVector = Icons.Default.Info,
//                        contentDescription = "도움말",
//                        tint = Colors.IconButton,
//                        modifier = Modifier
//                            .size(24.dp)
//                            .clickable {
//                                showToolTip = !showToolTip
//                            }
//                    )
//                    if (showToolTip) {
//                        Box(
//                            modifier = Modifier
//                                .background(Color.Gray, RoundedCornerShape(8.dp))
//                                .padding(8.dp)
//                                .align(Alignment.TopEnd)
//                        ) {
//                            Text(
//                                "장치 고유 번호는 장치 뒷면에서 확인가능합니다.",
//                                color = Colors.Text,
//                                fontSize = 14.sp
//                            )
//                        }
//                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = "",
                onValueChange = {},
                label = {
                    Text("장치의 고유 번호를 입력해주세요.", color = Colors.Placeholder)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Colors.TextField,
                    focusedIndicatorColor = Colors.Placeholder,
                    unfocusedIndicatorColor = Colors.Placeholder
                ),
                shape = RoundedCornerShape(8.dp)
            )
            Spacer(modifier = Modifier.height(60.dp))

            Button(
                    onClick = {done()},
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.Button),
                    shape = RoundedCornerShape(8.dp),

                ) {
                        Text( "장치 고유 번호 등록", color = Colors.ButtonText)
                }
            }
        }
    }

// 편한 디자인을 위한 Preview
@Preview(showBackground = true)
@Composable
fun PreviewDeviceRegistration() {
    DeviceRegistration(context = LocalContext.current, done = {})
}

