package com.example.myapplication.resource

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Colors



@Composable
fun LabelledBox(text: String, backgroundColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(8.dp))
                .size(15.dp)
        )
        Text(
            text = text,
            fontSize = 12.sp,
            color = Colors.Text,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}