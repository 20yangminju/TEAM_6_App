package com.example.myapplication.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun UrlImageExample() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(220.dp)
            .shadow(10.dp, shape = RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color.Gray, Color.LightGray)
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .background(Color.DarkGray, shape = RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = rememberImagePainter("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb4klPJ%2FbtqOjnmPgTa%2FPQnIKh2KFInfSUzeXd9N1K%2Fimg.jpg"),
            contentDescription = "Test Image from URL",
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun AIImageScreen(
    onNavigateToPre: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Colors.Background,
                title = {
                    Text(text = "AI 분석", color = Colors.Title)
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateToPre() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "뒤로 가기",
                            tint = Colors.IconButton
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(paddingValues)
            ) {
                Divider(color = Colors.Divider, thickness = 1.dp)
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "AI 배터리 충전 효율 분석",
                        color = Colors.Title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "• AI로 배터리의 충전 효율을 분석한 이미지입니다.",
                        color = Colors.Text,
                        fontSize = 16.sp,
                        lineHeight = 35.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    UrlImageExample()
                }
            }
        }
    )
}