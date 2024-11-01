// BatteryTemperatureScreen.kt
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BatteryTemperatureScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    val temperatureHistory = remember { mutableStateListOf("32°C", "33°C", "34°C") }
    var showAlert by remember { mutableStateOf(false) }

    Scaffold(
        backgroundColor = Colors.Background,
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "EV-PrepareCareFully", color = Colors.Title) },
                    backgroundColor = Colors.Background,
                    actions = {
                        IconButton(onClick = { onNavigateToSettings() }) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = "환경설정", tint = Colors.IconButton)
                        }
                        IconButton(onClick = { onNavigateToNotifications() }) {
                            Icon(imageVector = Icons.Default.Notifications, contentDescription = "알림", tint = Colors.IconButton)
                        }
                    }
                )
                Divider(color = Colors.Divider, thickness = 1.dp)
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentScreen = navController.currentDestination?.route ?: "main"
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // 상단 텍스트 및 Divider
                    Text(
                        text = "배터리 온도 측정",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Divider(color = Color.White, thickness = 2.dp, modifier = Modifier.padding(vertical = 16.dp))
                    Spacer(modifier = Modifier.height(30.dp))

                    // 온도 모듈 버튼 그리드
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TemperatureButton("온도 모듈 1", "34°C", onClick = { showAlert = true }, modifier = Modifier.weight(1f))
                        TemperatureButton("온도 모듈 2", "35°C", onClick = { showAlert = true }, modifier = Modifier.weight(1f))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TemperatureButton("온도 모듈 3", "36°C", onClick = { showAlert = true }, modifier = Modifier.weight(1f))
                        TemperatureButton("온도 모듈 4", "37°C", onClick = { showAlert = true }, modifier = Modifier.weight(1f))
                    }
                }

                // 알림창 구현
                if (showAlert) {
                    AlertDialog(
                        onDismissRequest = { showAlert = false },
                        title = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "온도 기록", color = Color.Black, fontWeight = FontWeight.Bold)
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription =  "종료 버튼",
                                    modifier = Modifier.clickable { showAlert = false }
                                )
                            }
                        },
                        text = {
                            TemperatureHistoryList(temperatureHistory)
                        },
                        confirmButton = {}
                    )
                }
            }
        }
    )
}

@Composable
fun TemperatureButton(title: String, temperature: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp))
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = temperature, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
        }
    }
}
