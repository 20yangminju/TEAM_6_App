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
        "충전하기" to R.drawable.baseline_ev_station_24,
        "운전 모드" to R.drawable.baseline_electric_car_24,
        "홈" to R.drawable.baseline_home_24,
        "배터리 관리" to R.drawable.baseline_battery_alert_24,
        "통계" to R.drawable.baseline_stacked_line_chart_24
    )

    BottomNavigation(
        backgroundColor = Colors.TextField,
        contentColor = Colors.Title,
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
                onClick = {
                    when (title) {
                        "충전하기" -> navController.navigate("charge")
                        "운전 모드" -> navController.navigate("car")
                        "홈" -> navController.navigate("main")
                        "배터리 관리" -> navController.navigate("manage")
                        "통계" -> navController.navigate("graph")
                    }
                }
            )
        }
    }
}
