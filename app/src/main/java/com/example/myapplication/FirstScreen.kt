package com.example.myapplication

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun FirstScreen(context : Context, onNavigateToLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1E))
            .clickable { onNavigateToLogin() },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "EV\n\nPrepareCareFully",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 80.dp),
            textAlign = TextAlign.Start

        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "안전을 위한 한걸음",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp)
        )
//        Image(
//            painter = painterResource(id = R.drawable.이미지이름),
//            contentDescription = "Car image",
//            contentScale = ContentScale.Fit,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp)
//        )
        Spacer(modifier = Modifier.height(400.dp))
        Text(
            text = "화면을 터치하여 시작",
            color = Color.White,
            fontSize = 25.sp
        )
    }
}

// 편한 디자인을 위한 Preview
@Preview(showBackground = true)
@Composable
fun PreviewFirstScreen() {
    FirstScreen(context = LocalContext.current, onNavigateToLogin = {})
}

