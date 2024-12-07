package com.example.myapplication

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Server.Retrofit
import com.example.myapplication.Server.SOHRequest
import com.example.myapplication.Server.SOHResponse
import com.example.myapplication.ui.theme.Colors

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap


@Composable
fun Image(bitmap: Bitmap?) {
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
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Analyzed Image",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = "이미지를 불러오는 중...",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun AIImageScreen(
    onNavigateToPre: () -> Unit
) {
    var SOHResponse by remember { mutableStateOf<SOHResponse?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var SOH by remember { mutableStateOf<Int?>(0) }

    LaunchedEffect(Unit) {
        try {
            val SOHRequset = SOHRequest(device_number = "888777")
            SOHResponse = Retrofit.api.SOH(SOHRequset)

            var temp =  ( SOHResponse?.predicted_SOH?.toFloatOrNull() )
            if( temp != null ){
                temp = temp * 100;
                SOH = temp.toInt()
            }

            SOHResponse?.let {
                val imageResponse = Retrofit.api.Image(SOHRequset)
                if(imageResponse.isSuccessful){
                    imageResponse.body()?.let { responseBody ->
                        bitmap = BitmapFactory.decodeStream(responseBody.byteStream())
                    }
                }
            }
        }
        catch (e: Exception){
            e.printStackTrace()
        } finally {
            isLoading = false
        }

    }
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
                        text = "AI 배터리 충전 효율 분석 \n",
                        color = Colors.Title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if (isLoading){
                        Text(
                            text = "예측 중입니다.",
                            color = Colors.Title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    else {
                        SOHResponse?.let {
                            Text(
                                text = "현재 배터리의 효율은 ${SOH}% 입니다. \n",
                                color = Colors.Title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

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
                    Image(bitmap)


                    Text(
                        text = "현재까지의 데이터로 예측한 결과로 \n 실제 추세와는 다를 수 있습니다.",
                        color = Colors.Text,
                        fontSize = 16.sp,
                        lineHeight = 35.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    )
}