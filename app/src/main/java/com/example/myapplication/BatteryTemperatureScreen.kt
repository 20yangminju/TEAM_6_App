// BatteryTemperatureScreen.kt
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.myapplication.ChatBot.getCurrentLocation
import com.example.myapplication.NearbyStationsDialog
import com.example.myapplication.R
import com.example.myapplication.ShowTemperatureDialog
import com.example.myapplication.createNotification
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.resource.NotificationViewModel
import com.example.myapplication.ui.theme.Colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BatteryTemperatureScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateAIscreen: () -> Unit,
    notificationViewModel: NotificationViewModel
) {
    var isRequestFailed by remember { mutableStateOf(false) }
    var temperatureData1 by remember { mutableStateOf<TempResponse?>(null) }
    var temperatureData2 by remember { mutableStateOf<TempResponse?>(null) }
    var temperatureData3 by remember { mutableStateOf<TempResponse?>(null) }
    var temperatureData4 by remember { mutableStateOf<TempResponse?>(null) }
    var chargerData by remember { mutableStateOf<chargeResponse?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val temperatureHistory = remember { mutableStateListOf("32°C", "33°C", "34°C") }
    var showAlert by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // GPS 변수
    var apiLocation by remember { mutableStateOf<Location?>(null) }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
        if(isGranted){
            Toast.makeText(context, "위치 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "위치 권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    // 추가된 요구사항 상태 관리
    var currentLocation by remember { mutableStateOf("서울시 강남구") }
    var nearestStation by remember { mutableStateOf("테스트 충전소 1") }
    var showStationDialog by remember { mutableStateOf(false) }
    val stationList = listOf(
        "테스트 충전소 1-서울시 강남구-1.2km",
        "테스트 충전소 2-서울시 서초구-2.3km",
        "테스트 충전소 3-서울시 송파구-3.5km",
        "테스트 충전소 4-서울시 강남구-1.2km",
    )

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

            if ((temperatureData1?.module_temp ?: 0f) >= 45F
                || (temperatureData2?.module_temp ?: 0f) >= 45F
                || (temperatureData3?.module_temp ?: 0f) >= 45F
                || (temperatureData4?.module_temp ?: 0f) >= 45F) {
                showDialog = true
                createNotification(
                    context = context,
                    viewModel = notificationViewModel,
                    status = 0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            isRequestFailed = true
        }

        if(!hasLocationPermission){
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        getCurrentLocation(context) { location ->
            apiLocation = location

            if (location != null) {
                // 코루틴 스코프 내에서 suspend 함수 호출
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val chargeRequest = chargeRequest(
                            latitude = apiLocation!!.latitude.toFloat(),
                            longitude = apiLocation!!.longitude.toFloat()
                        )
                        val response = RetrofitInstance.api.find_charger(chargeRequest)
                        withContext(Dispatchers.Main) {
                            chargerData = response // UI 업데이트는 Main 스레드에서 수행

                            if(chargerData != null){
                                currentLocation = chargerData!!.address
                                nearestStation = chargerData!!.name
                            }

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } else {
                println("Location is null")
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
                        IconButton(onClick = { onNavigateAIscreen() }) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_assessment_24), // drawable 이미지 리소스
                                contentDescription = "AI 분석",
                                modifier = Modifier.size(24.dp), // 아이콘 크기 설정
                                alignment = Alignment.Center
                            )
                        }
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp) // 각 항목 간격 설정
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "배터리 온도 측정",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Colors.Title,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp) // 고정 높이 설정
                    )
                    {
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
                    }

                    if (showAlert) {
                        AlertDialog(
                            onDismissRequest = { showAlert = false },
                            title = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "온도 기록",
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold
                                    )
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

                    ShowTemperatureDialog(
                        showDialog = showDialog,
                        onDismiss = { showDialog = false })
                }
                item {
                    Divider(color = Colors.Divider, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(10.dp))
                    // 충전소 위치 텍스트
                    Text(
                        text = "충전소 위치",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Colors.Title,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),

                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn, // 아이콘 추가
                        contentDescription = "현 위치 아이콘",
                        tint = Colors.Text, // 아이콘 색상
                        modifier = Modifier.size(24.dp) // 아이콘 크기
                    )
                    Text("현재 위치: ",
                        fontWeight = FontWeight.Bold, color = Colors.Text,
                        modifier = Modifier.padding(start = 0.dp,end=5.dp)
                    )
                    Box(
                        modifier = Modifier
                            .background(Color.LightGray)
                            .padding(8.dp)
                    ) {
                        Text(text = currentLocation)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Search, // 아이콘 추가
                        contentDescription = "현 위치 아이콘",
                        tint = Colors.Text, // 아이콘 색상
                        modifier = Modifier.size(24.dp) // 아이콘 크기
                    )
                    Text("가까운 충전소: ", fontWeight = FontWeight.Bold, color = Colors.Text,
                        modifier = Modifier.padding(start = 0.dp,end=5.dp))
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(Color.LightGray)
                            .padding(8.dp)
                            .clickable { showStationDialog = true }
                    ) {
                        Text(text = nearestStation)
                    }
                }

                if (showStationDialog) {
                    NearbyStationsDialog(
                        stationList = stationList,
                        onStationSelect = { selectedStation ->
                            nearestStation = selectedStation
                        },
                        onDismiss = { showStationDialog = false }
                    )
                }}
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.LightGray)
                            .clickable {
                                val intent = Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("https://www.google.com/maps/dir/?api=1&destination=${nearestStation}")
                                )
                                context.startActivity(intent)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("지도 표시 (클릭하여 길찾기)", color = Color.White)
                    }
                }
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

