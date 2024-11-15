// BatteryTemperatureScreen.kt
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
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
import com.example.myapplication.resource.NotificationViewModel
import com.example.myapplication.ui.theme.Colors

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BatteryTemperatureScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    notificationViewModel: NotificationViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var isRequestFailed by remember { mutableStateOf(false) }
    var temperatureData1 by remember { mutableStateOf<TempResponse?>(null) }
    var temperatureData2 by remember { mutableStateOf<TempResponse?>(null) }
    var temperatureData3 by remember { mutableStateOf<TempResponse?>(null) }
    var temperatureData4 by remember { mutableStateOf<TempResponse?>(null) }
    var showDialog by remember { mutableStateOf(false) }
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
                createNotification(
                    context = context,
                    viewModel = notificationViewModel, // 알림 저장
                    status = 0
                )
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

                    // 온도 모듈 카드 그리드
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(4) { index ->
                            TemperatureButton(
                                title = "온도 모듈 ${index + 1}",
                                temperature = when (index) {
                                    0 -> temperatureData1?.module_temp?.toString() ?: "N/A"
                                    1 -> temperatureData2?.module_temp?.toString() ?: "N/A"
                                    2 -> temperatureData3?.module_temp?.toString() ?: "N/A"
                                    else -> temperatureData4?.module_temp?.toString() ?: "N/A"
                                },
                                onClick = { showAlert = true }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
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
                                    contentDescription = "종료 버튼",
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
                ShowTemperatureDialog(showDialog = showDialog, onDismiss = { showDialog = false }) // 화면 UI 알림 표시
            }
        }
    )
}

@Composable
fun TemperatureButton(
    title: String,
    temperature: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tempValue = temperature.toFloatOrNull() ?: 0f

    // 온도 상태에 따라 아이콘 색상, 메시지 설정
    val (icon, iconColor, statusMessage) = when {
        tempValue >= 50 -> Triple(Icons.Default.Warning, Color.Red, "과열 경고")
        tempValue >= 40 -> Triple(Icons.Default.Warning, Color(0xFFFFA726), "점검 필요")
        tempValue in 20f..39f -> Triple(Icons.Default.CheckCircle, Color.Green, "안정적")
        else -> Triple(Icons.Default.Info, Color.Blue, "온도 낮음")
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 8.dp,
        shape = RoundedCornerShape(12.dp),
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor, // 아이콘 색상 설정
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$temperature°C",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = statusMessage,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}

