package com.example.myapplication.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Colors

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentScreen: String
) {
    val items = listOf(
        "챗봇" to R.drawable.baseline_chat_bubble_24,
        "배터리 현황" to R.drawable.baseline_electric_car_24,
        "홈" to R.drawable.baseline_home_24,
        "배터리 온도" to R.drawable.baseline_battery_alert_24,
        "셀 밸런스" to R.drawable.baseline_grid_on_24
    )

    BottomNavigation(
        backgroundColor = Colors.TextField,
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEach { (title, icon) ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = title
                    )
                },
                label = { Text(text = title, fontSize = 10.sp) },
                selected = currentScreen == title,
                selectedContentColor = Color.White, // 선택된 상태일 때 아이콘과 텍스트의 색상을 흰색으로 설정
                unselectedContentColor = Colors.Placeholder, // 선택되지 않은 상태의 색상
                onClick = {
                    when (title) {
                        "챗봇" -> navController.navigate("ChatScreen")
                        "배터리 현황" -> navController.navigate("BatteryChargeScreen")
                        "홈" -> navController.navigate("main")
                        "배터리 온도" -> navController.navigate("batteryTemperature")
                        "셀 밸런스" -> navController.navigate("cellBalance")
                    }
                }
            )
        }
    }
}

