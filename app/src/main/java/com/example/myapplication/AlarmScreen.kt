package com.example.myapplication

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.ui.theme.Colors

data class NotificationItem(val icon: Int, val title: String, val date: String)

// 알람 모음 화면
@Composable
fun AlarmScreen(onNavigateToHome: () -> Unit, onBottomNavigationSelected: (String) -> Unit) {
    val notifications = listOf( // 이미지는 임시로 넣었습니다.
        NotificationItem(R.drawable.ic_launcher_foreground, "",""),
        NotificationItem(R.drawable.ic_launcher_foreground, "",""),
        NotificationItem(R.drawable.ic_launcher_foreground, "",""),
        NotificationItem(R.drawable.ic_launcher_foreground, "",""),
        NotificationItem(R.drawable.ic_launcher_foreground, "","")
    )
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
                        // 홈화면으로 이동
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                        tint = Colors.IconButton
                    )
                }
                Text(
                    "알림 내역",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Colors.Title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Colors.Divider, thickness = 2.dp, modifier = Modifier.fillMaxWidth())

            // RecyclerView를 LazyColumn으로 표현
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(notifications) { item ->
                    NotificationRow(item)
                    Divider(color = Color.Gray)
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            BottomNavigationBar(
                currentScreen = "알림 내역",
                onItemSelected = onBottomNavigationSelected
            )
        }
    }
}

@Composable
fun NotificationRow(item: NotificationItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(end = 16.dp)
        )
        Column {
            Text(text = item.title, fontSize = 18.sp, color = Colors.Text)
            Text(text = item.date, fontSize = 14.sp, color = Colors.Text)
        }
    }
}
// 편한 디자인을 위한 Preview
@Preview(showBackground = true)
@Composable
fun PreviewAlarmScreen() {
    AlarmScreen(
        onNavigateToHome = {},
        onBottomNavigationSelected = {}
    )
}