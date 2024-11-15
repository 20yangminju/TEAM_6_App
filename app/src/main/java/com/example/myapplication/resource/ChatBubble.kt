package com.example.myapplication.resource

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Colors
@Composable
fun ChatBubble(message: String, isUserMessage: Boolean) {
    val bubbleColor = if (isUserMessage) Colors.MyBox else Colors.ChatGPTBox
    val shape = if (isUserMessage) RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp) else RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp)
    Column(modifier = Modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isUserMessage) {
                Image(
                    painter = painterResource(id = com.example.myapplication.R.drawable.chatbotimg),
                    contentDescription = "ChatbotImg",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .background(bubbleColor, shape)
                    .padding(12.dp)
                    .widthIn(max = 250.dp)
            ) {
                Text(
                    text = message,
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
            if (isUserMessage) {
                Image(
                    painter = painterResource(id = com.example.myapplication.R.drawable.userimg), // 사용자 이미지 리소스
                    contentDescription = "UserImg",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}