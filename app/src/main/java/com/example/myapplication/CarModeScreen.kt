import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.ui.theme.Colors

@Composable
fun CarModeScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    val speed by remember { mutableStateOf(0) }
    val rpm by remember { mutableStateOf(0) }
    val batteryPercentage by remember { mutableStateOf(50) }
    val totalDistance by remember { mutableStateOf(100) }
    val totalTime by remember { mutableStateOf("02:15:30") }
    val fuelEfficiency by remember { mutableStateOf(10.0f) }

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
                Divider(
                    color = Colors.Divider,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
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
                    .padding(horizontal = 16.dp) // 양옆 패딩 적용
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "차량 운전 모드",
                        color = Colors.Text,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Button(
                        onClick = { /* 운전 시작 */ },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Colors.Button,
                            contentColor = Colors.Placeholder
                        ),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("운전 시작")
                    }
                }
                Divider(
                    color = Colors.Divider,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp) // 상하에만 패딩 적용
                )
                Spacer(modifier = Modifier.height(32.dp)) //구분선과의 공백

                // 속도 및 RPM 정보
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // 속도 정보
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .background(Colors.CarBox, shape = RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("현재 속력", color = Colors.Text, fontSize = 18.sp)
                            Text("$speed", color = Colors.Text, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                            Text("km/h", color = Colors.Text, fontSize = 18.sp)
                        }
                    }
                    // RPM 정보
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .background(Colors.CarBox, shape = RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("RPM", color = Colors.Text, fontSize = 18.sp)
                            Text("$rpm", color = Colors.Text, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                            Text("rpm", color = Colors.Text, fontSize = 18.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Divider(
                    color = Colors.Divider,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp) // 상하에만 패딩 적용
                )
                Spacer(modifier = Modifier.height(32.dp))

                // 배터리 현황 및 주행 정보
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    // 배터리 현황 (직사각형 Box, 흰색 테두리 추가, 세로 길이 증가)
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(250.dp) // 총 주행 거리와 총 주행 시간 Box 높이에 맞춰 설정
                            .background(Colors.Background, shape = RoundedCornerShape(8.dp))
                            .border(2.dp, Colors.Text, RoundedCornerShape(8.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("현재 배터리 현황", color = Colors.Text, fontSize = 18.sp)
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
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 16.dp)
                    ) {
                        // 총 주행 거리
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Colors.CarBox, shape = RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        ) {
                            Column {
                                Text("총 주행 거리:", color = Colors.Text, fontSize = 16.sp)
                                Text("$totalDistance km", color = Colors.Text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(50.dp))

                        // 총 주행 시간
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Colors.CarBox, shape = RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        ) {
                            Column {
                                Text("총 주행 시간:", color = Colors.Text, fontSize = 16.sp)
                                Text(totalTime, color = Colors.Text, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    )
}
