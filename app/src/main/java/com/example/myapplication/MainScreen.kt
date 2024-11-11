import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.ui.theme.Colors
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    // 권장 완속 충전 날짜 설정
    val recommendedChargeDate = LocalDate.now().plusDays(30) // 권장 완속 충전 날짜
    val MonthYearFormatter = DateTimeFormatter.ofPattern("yyyy년 M월", Locale.KOREAN)
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()

    var displayedMonth by remember { mutableStateOf(YearMonth.now())}
    val daysInMonth = displayedMonth.lengthOfMonth()
    val daysInMonthList = (1..daysInMonth).map { LocalDate.of(displayedMonth.year, displayedMonth.month, it) }
    val weeks = daysInMonthList.chunked(7)
    val daysOfWeek = listOf("월", "화", "수", "목", "금", "토", "일")
    val formattedMonthYear = displayedMonth.format(MonthYearFormatter)

    // 주행 관련 변수 설정
    val batteryPercentage by remember { mutableStateOf(50) }
    val totalDistance by remember { mutableStateOf("1200 km") }
    val totalTime by remember { mutableStateOf("15:30:00") }
    val fuelEfficiency by remember { mutableStateOf("12.5 km/kWh") }

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
                            text = "권장 완속 충전 날짜: ${recommendedChargeDate.format(dateFormatter)}",
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
                                    contentDescription = "안전 마크",
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
                }
                item {
                    Text(
                        text = "차량 주행 정보",
                        color = Colors.Title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                item{
                    Spacer(modifier =  Modifier.height(10.dp))
                }
                item {
                    // 총 주행 거리
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.carimage),
                            contentDescription = "차량 이미지",
                            modifier = Modifier.size(300.dp)
                        )
                        Column(
                            modifier = Modifier
                                .offset(x = (-100).dp, y = (-80).dp) // 위치 조정
                                .background(Colors.Button, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "연비: $fuelEfficiency",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                        // 총 주행 거리 표시
                        Column(
                            modifier = Modifier
                                .offset(x = 0.dp, y = 90.dp) // 위치 조정
                                .background(Colors.Button, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "총 주행 거리: $totalDistance",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                        // 총 주행 시간 표시
                        Column(
                            modifier = Modifier
                                .offset(x = 100.dp, y = (-80).dp) // 위치 조정
                                .background(Colors.Button, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "총 주행 시간: $totalTime",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                // 완속 충전 스케줄링 표
                item {
                    Text(
                        text = "완속 충전 스케줄링",
                        color = Colors.Title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                item{
                    Spacer(modifier =  Modifier.height(10.dp))
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "• 한 달에 한 번 이상 100% 완속 충전으로\n  배터리 성능 저하를 예방할 수 있습니다.",
                            color = Colors.Text,
                            fontSize = 16.sp
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            displayedMonth = displayedMonth.minusMonths(1)
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "이전 달",
                                tint = Colors.Button
                            )
                        }
                        Text(
                            text = formattedMonthYear,
                            color = Colors.Text,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = {
                            displayedMonth = displayedMonth.plusMonths(1)
                        }) {
                            Icon(Icons.Default.ArrowForward, contentDescription = "다음 달", tint = Colors.Button)
                        }
                    }
                }
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            daysOfWeek.forEach{ dayOfWeek ->
                                Text(
                                    text = dayOfWeek,
                                    color = Colors.Text,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        weeks.forEach { week ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                week.forEach { day ->
                                    val isToday = day == today
                                    val isRecommendedDate = day == recommendedChargeDate
                                    Box(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .weight(1f)
                                            .background(
                                                color = when {
                                                    isToday -> Colors.Button // 오늘 날짜는 버튼 색
                                                    isRecommendedDate -> Color.Red // 권장 날짜는 빨간색
                                                    else -> Colors.Background
                                                },
                                                shape = MaterialTheme.shapes.small
                                            )
                                            .aspectRatio(1f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        when {
                                            isToday -> {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = day.dayOfMonth.toString(),
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    Text(
                                                        text = "오늘 날짜",
                                                        color = Color.White,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                            isRecommendedDate -> {
                                                Column(
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    verticalArrangement = Arrangement.Center
                                                ) {
                                                    Text(
                                                        text = day.dayOfMonth.toString(),
                                                        color = Color.White,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    Text(
                                                        text = "권장 날짜",
                                                        color = Color.White,
                                                        fontSize = 10.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                            }
                                            else -> {
                                                Text(
                                                    text = day.dayOfMonth.toString(),
                                                    color = Colors.Text,
                                                    fontWeight = FontWeight.Normal
                                                )
                                            }
                                        }
                                    }
                                }
                                if (week.size < 7) {
                                    repeat(7 - week.size) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }
                }
                item {Spacer(modifier = Modifier.height(20.dp))}
            }
        }
    )
}
