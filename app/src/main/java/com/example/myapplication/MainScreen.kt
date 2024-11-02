// MainScreen.kt
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.ui.theme.Colors
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    // 권장 완속 충전 날짜 설정
    val recommendedChargeDate = LocalDate.now().plusDays(30)
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // 주행 관련 변수 설정
    val batteryPercentage by remember { mutableStateOf(50) }
    val totalDistance by remember { mutableStateOf("1200 km") }
    val totalTime by remember { mutableStateOf("15:30:00") }
    val fuelEfficiency by remember { mutableStateOf("12.5 km/l") }

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 권장 완속 충전 날짜 정보
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Placeholder)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "경고 아이콘",
                        tint = Colors.IconButton
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "권장 완속 충전 날짜: ${recommendedChargeDate.format(dateFormatter)}",
                        color = Colors.Text,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                // 배터리 온도 측정 및 현재 배터리 충전량
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 배터리 온도 측정 버튼
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(150.dp)
                            .background(Colors.IconButton)
                            .clickable { navController.navigate("batteryTemperature") }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "배터리 온도 측정", color = Colors.Title, fontSize = 20.sp)
                            Spacer(modifier = Modifier.height(10.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription =  "안전 마크",
                                tint = Color(0xFF32CD32), // 연두색
                                modifier = Modifier.size(48.dp) // 아이콘 크기
                            )
                        }
                    }

                    // 현재 배터리 충전량 버튼
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(150.dp)
                            .background(Colors.Background)
                            .border(width = 2.dp, color = Colors.Text)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("현재 배터리 충전량", color = Colors.Text, fontSize = 16.sp)
                            Box(
                                modifier = Modifier
                                    .size(100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                // 기본 검은색 배경에 흰색으로 배터리 잔량 표시
                                CircularProgressIndicator(
                                    progress = 1f,
                                    color = Colors.Background,
                                    modifier = Modifier.size(100.dp)
                                )
                                CircularProgressIndicator(
                                    progress = batteryPercentage / 100f,
                                    color = Colors.Text,
                                    modifier = Modifier.size(100.dp)
                                )
                                Text(
                                    "$batteryPercentage%",
                                    color = Colors.Text,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Divider
                Divider(color = Colors.Text, thickness = 1.dp)

                Spacer(modifier = Modifier.height(20.dp))

                // 총 주행 거리
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Placeholder)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "총 주행 거리: $totalDistance",
                        color = Colors.Text,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                // 총 주행 시간
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Placeholder)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "총 주행 시간: $totalTime",
                        color = Colors.Text,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))

                // 연비
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Placeholder)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "연비: $fuelEfficiency",
                        color = Colors.Text,
                        fontSize = 20.sp
                    )
                }
            }
        }
    )
}
