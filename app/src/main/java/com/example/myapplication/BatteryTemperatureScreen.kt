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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.ShowTemperatureDialog
import com.example.myapplication.createNotification
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.ui.theme.Colors

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BatteryTemperatureScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    var isRequestFailed by remember { mutableStateOf(false) }
    var temperatureData1 by remember { mutableStateOf<TempResponse?>(null) }
    var temperatureData2 by remember { mutableStateOf<TempResponse?>(null) }
    var temperatureData3 by remember { mutableStateOf<TempResponse?>(null) }
    var temperatureData4 by remember { mutableStateOf<TempResponse?>(null) }
    var showDialog by remember { mutableStateOf(false)} // 다이얼로그 상태 관리
    val temperatureHistory = remember { mutableStateListOf("32°C", "33°C", "34°C") }
    var showAlert by remember { mutableStateOf(false) }
    val context = LocalContext.current // 알림 표시 context

    LaunchedEffect(Unit) {
        try {
            isRequestFailed = false
            val request_1 = TempRequest(car_device_number = "674마5387", 1)
            val request_2 = TempRequest(car_device_number = "674마5387", 2)
            val request_3 = TempRequest(car_device_number = "674마5387", 3)
            val request_4 = TempRequest(car_device_number = "674마5387", 4)
            temperatureData1 = RetrofitInstance.api.temperature(request_1)
            temperatureData2 = RetrofitInstance.api.temperature(request_2)
            temperatureData3 = RetrofitInstance.api.temperature(request_3)
            temperatureData4 = RetrofitInstance.api.temperature(request_4)

            // 배터리 온도가 45도 이상일 때 상태표시줄 알림과 화면 알림 표시
            if ((temperatureData1?.module_temp ?: 0f) >= 45F
                || (temperatureData2?.module_temp ?: 0f) >= 45F
                || (temperatureData3?.module_temp ?: 0f) >= 45F
                || (temperatureData4?.module_temp ?: 0f) >= 45F) {
                showDialog = true
                createNotification(context)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            isRequestFailed = true
            temperatureData1 = null
            temperatureData2 = null
            temperatureData3 = null
            temperatureData4 = null
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
                        TemperatureButton("온도 모듈 1", temperatureData1?.module_temp.toString(), onClick = { showAlert = true }, modifier = Modifier.weight(1f))
                        TemperatureButton("온도 모듈 2", temperatureData2?.module_temp.toString(), onClick = { showAlert = true }, modifier = Modifier.weight(1f))
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TemperatureButton("온도 모듈 3", temperatureData3?.module_temp.toString(), onClick = { showAlert = true }, modifier = Modifier.weight(1f))
                        TemperatureButton("온도 모듈 4", temperatureData4?.module_temp.toString(), onClick = { showAlert = true }, modifier = Modifier.weight(1f))
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
                ShowTemperatureDialog(showDialog = showDialog, onDismiss = {showDialog = false}) // 화면 UI 알림 표시
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
