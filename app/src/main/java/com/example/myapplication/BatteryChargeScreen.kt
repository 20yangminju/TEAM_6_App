package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.resource.BatteryBarChart
import com.example.myapplication.resource.CalendarApp
import com.example.myapplication.resource.LabelledBox
import com.example.myapplication.ui.theme.Colors
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

// 배터리 현황 페이지
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BatteryChargeScreen(navController: NavController,
                        onNavigateToSettings: () -> Unit,
                        onNavigateToNotifications: () -> Unit
) {
    // 그래프 테스트용
    val sampleData = listOf("12:00" to 80,
        "13:00" to 70,
        "14:00" to 50,
        "15:00" to 30,
        "17:00" to 20,
        "18:00" to 90,
        "19:00" to 80,
        "20:00" to 75,
        "21:00" to 60,
        "22:00" to 50,
        "23:00" to 40,
        "24:00" to 20
    )
    val recommendedChargeDate = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, 30) // 30일 뒤의 날짜로 설정
    }

    val lastChargeDate = LocalDate.now() // 마지막 충전 날짜 → 추후 수정 필요
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val recommendedChargeDateText = dateFormatter.format(recommendedChargeDate.time)
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val lastChargeDateText = lastChargeDate.format(dateTimeFormatter)

    val batteryPercentage by remember { mutableStateOf(10) } // → 추후 수정 필요
    var expanded by remember { mutableStateOf(false) }
    var selectedCount by remember { mutableStateOf(1) }
    var saveCount by remember { mutableStateOf(0) }

    val batteryHistory = remember { mutableStateListOf<Pair<String, Int>>() }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3600000)
            val currentTime = LocalDateTime.now().format(dateTimeFormatter)
            batteryHistory.add(Pair(currentTime, batteryPercentage)) // 배터리 상태 저장

            // 최근 24시간의 데이터만 유지
            if(batteryHistory.size > 24) {
                batteryHistory.removeFirst() // 가장 오래된 기록 삭제
            }
        }
    }


    Scaffold(
        backgroundColor = Colors.Background,
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "EV-PrepareCareFully", color = Colors.Title) },
                    backgroundColor = Colors.Background,
                    actions = {
                        IconButton(onClick = { onNavigateToSettings() }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "환경설정",
                                tint = Colors.IconButton
                            )
                        }
                        IconButton(onClick = { onNavigateToNotifications() }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "알림",
                                tint = Colors.IconButton
                            )
                        }
                    }
                )
                Divider(color = Colors.Divider, thickness = 1.dp)
                Spacer(modifier = Modifier.height(20.dp))
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentScreen = navController.currentDestination?.route ?: "main"
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    // 권장 완속 충전 날짜 정보
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Colors.Button, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = "경고 아이콘",
                            tint = Colors.IconButton
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "권장 완속 충전 날짜: $recommendedChargeDateText",
                            color = Colors.Text,
                            fontSize = 18.sp
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    // 배터리 온도 측정 및 현재 배터리 충전량
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .border(
                                width = 2.dp,
                                color = Colors.Text,
                                shape = RoundedCornerShape(8.dp)
                            ) // 테두리 추가
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center, // 좌우 정렬
                        verticalAlignment = Alignment.CenterVertically // 수직 가운데 정렬
                    ) {
                        // 배터리 충전량 그래프
                        Box(
                            modifier = Modifier
                                .size(100.dp) // 그래프 크기
                                .weight(1f), // 좌측으로 정렬되도록 weight 설정
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.offset(y = (-10).dp)
                            ) {
                                Text(
                                    "현재 배터리 충전량",
                                    color = Colors.Text,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                                Box(
                                    modifier = Modifier.size(100.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        progress = 1f,
                                        color = Colors.Text,
                                        modifier = Modifier.size(100.dp),
                                        strokeWidth = 8.dp
                                    )
                                    CircularProgressIndicator(
                                        progress = batteryPercentage / 100f,
                                        color = Colors.Button,
                                        modifier = Modifier.size(100.dp),
                                        strokeWidth = 8.dp
                                    )
                                    Text(
                                        "$batteryPercentage%",
                                        color = Colors.Text,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.offset(y = 10.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp)) // 그래프와 텍스트 사이 간격 추가

                        // 배터리 상태 멘트
                        if (batteryPercentage >= 70) {
                            Text(
                                text = "배터리가 충분합니다!",
                                color = Colors.Text,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        } else if (batteryPercentage < 30) {
                            Text(
                                text = "배터리 충전이 필요합니다.",
                                color = Colors.Text,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                        //else Text(충전중)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    // 마지막 충전 날짜 정보
                    Text(
                        text = "마지막 충전 날짜: $lastChargeDateText",
                        color = Colors.Text,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    Text(
                        text = "지난 24시간의 배터리 변화량",
                        color = Colors.Title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    BatteryBarChart(data = sampleData) // 테스트용
                    //BatteryBarChart(batteryHistory) // 실제 데이터 출력
                }
                // 완속 충전 스케줄링 캘린더
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 16.dp), // 좌우 여백 추가
                        verticalAlignment = Alignment.CenterVertically, // 수직 가운데 정렬
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "완속 충전 스케줄링",
                            color = Colors.Title,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .wrapContentSize(Alignment.TopStart)
                        ) {
                            OutlinedButton(
                                onClick = { expanded = true },
                                border = BorderStroke(1.dp, Colors.Button),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = Colors.Text
                                )
                            ) {
                                Text(text = "$selectedCount 회", fontSize = 14.sp)
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                (1..5).forEach { count ->
                                    DropdownMenuItem(onClick = {
                                        selectedCount = count
                                        saveCount = selectedCount
                                        expanded = false
                                    }) {
                                        Text(
                                            text = "$count 회",
                                            color = Color.Black,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "• 한 달에 한 번 이상 100% 완속 충전으로\n  배터리 성능 저하를 예방할 수 있습니다.",
                            color = Colors.Text,
                            fontSize = 16.sp,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            LabelledBox(text = "오늘 날짜", backgroundColor = Colors.Button)
                            LabelledBox(text = "권장 날짜", backgroundColor = Color.Red)
                            LabelledBox(text = "과거 충전 날짜", backgroundColor = Color.Yellow)
                        }
                    }
                }
                item {
                    CalendarApp(recommendedChargeDate = recommendedChargeDate)
                }
            }
        }
    )
}