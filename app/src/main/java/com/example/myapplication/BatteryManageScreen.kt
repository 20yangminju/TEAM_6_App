import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.createNotification
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.ui.theme.Colors
import com.example.myapplication.network.RetrofitInstance
import kotlinx.coroutines.launch

@Composable
fun BatteryManageScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var temperatureData by remember { mutableStateOf<TempResponse?>(null) }
    var isRequestFailed by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false)} // 다이얼로그 상태 관리
    val context = LocalContext.current // 알림 표시 context

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
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentScreen = navController.currentDestination?.route ?: "main"
            )
        },
        content = { innerPadding ->
            // BatteryInfoScreen의 내용을 추가
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(text = "배터리 정보", color = Colors.Text, style = MaterialTheme.typography.h6)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    coroutineScope.launch {
                        try {
                            isRequestFailed = false
                            val request = TempRequest(car_device_number = "674마5387", 1)
                            temperatureData = RetrofitInstance.api.temperature(request)
                            // 배터리 온도가 45도 이상일 때 상태표시줄 알림과 화면 알림 표시
                            if ((temperatureData?.module_temp ?: 0f) >= 45) {
                                showDialog = true;
                                createNotification(context)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            isRequestFailed = true
                            temperatureData = null
                        }
                    }
                }) {
                    Text("Get Battery Temp")
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isRequestFailed) {
                    Text("Failed to retrieve data.", color = Colors.Text)
                } else {
                    temperatureData?.let {
                        Text("Temperature: ${it.module_temp}", color = Colors.Text)
                        Text("Module Number: ${it.module_number}", color = Colors.Text)
                        Text("Car Number: ${it.car_device_number}", color = Colors.Text)
                        Text("Creat At : ${it.created_at}", color = Colors.Text)
                    } ?: Text("No temperature data available", color = Colors.Text)
                }
            }
        }
    )
    //ShowAlertDialog(showDialog = showDialog, onDismiss = {showDialog = false}) // 화면 UI 알림 표시
}
