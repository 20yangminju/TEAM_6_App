import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
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
fun CellBalanceScreen(
    navController: NavController,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit
) {
    val cells = List(98) { index -> CellData(index + 1, getRandomVoltage()) }
    val safePercentage = cells.count { it.voltageDeviation <= 20 }.toFloat() / cells.size * 100

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
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "셀 밸런스 상태 진단",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Colors.Title,
                    modifier = Modifier.padding(16.dp)
                )
                CellBalanceStatus(safePercentage)
                Spacer(modifier = Modifier.height(15.dp))
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
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                CellGrid(cells = cells)
            }
        }
    )
}

@Composable
fun CellBalanceStatus(safePercentage: Float) {
    val (icon, message, color) = when {
        safePercentage >= 70 -> {
            Triple(
                Icons.Default.CheckCircle,
                "셀 밸런스가 안전하게 유지되고 있습니다!",
                Color(0xFF4CAF50) // 초록색
            )
        }
        safePercentage > 50 && safePercentage < 70 ->{
            Triple(
                Icons.Default.Info,
                "셀 밸런스의 관리가 필요합니다.",
                Color(0xFFFFC107) // 노란색
            )
        }
        else -> {
            Triple(
                Icons.Default.Warning,
                "셀 밸런싱 작업 진행이 시급합니다.",
                Color(0xFFF44336) // 빨간색
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color.copy(alpha = 0.2f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = message,
            color = color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CellGrid(cells: List<CellData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(10),
        modifier = Modifier.fillMaxSize()
    ) {
        items(cells.size) { index ->
            CellBox(cell = cells[index])
        }
    }
}

@Composable
fun CellBox(cell: CellData) {
    val showDialog = remember { mutableStateOf(false) }
    val voltageHistory = List(10) {
        Pair((15..60).random(), "2024-11-08 12:${10 + it} PM")
    }

    val isSafe = cell.voltageDeviation <= 20
    val backgroundColor = if (isSafe) Color(0xFF87CEEB).copy(alpha = 0.8f) else Color.Red.copy(alpha = 0.8f)
    val borderColor = if (isSafe) Color(0xFF87CEEB).copy(alpha = 0.8f) else Color.Red.copy(alpha = 0.8f)

    val voltageOpacity = remember { mutableStateOf(0.9f) }

    Box(
        modifier = Modifier
            .size(48.dp)
            .background(backgroundColor)
            .border(1.dp, borderColor)
            .padding(4.dp)
            .clickable {
                voltageOpacity.value = 1.0f
                showDialog.value = true
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${cell.index}",
                color = Colors.Text.copy(alpha = 0.8f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = "${cell.voltageDeviation}",
                color = Colors.Text.copy(alpha = voltageOpacity.value),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "mV",
                color = Colors.Text.copy(alpha = 0.9f),
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            )
        }
    }

    LaunchedEffect(showDialog.value) {
        if (!showDialog.value) {
            voltageOpacity.value = 0.9f
        }
    }

    CellVoltageHistoryDialog(
        showDialog = showDialog.value,
        onDismiss = { showDialog.value = false },
        voltageHistory = voltageHistory
    )
}

data class CellData(val index: Int, val voltageDeviation: Int)

fun getRandomVoltage(): Int {
    val randomValue = (1..100).random() // 1~100 사이의 랜덤 값 생성
    return when {
        randomValue <= 70 -> (15..20).random() // 70% 확률로 정상 범위 값(15~20)
        randomValue <= 90 -> (21..50).random() // 20% 확률로 경고 범위 값(21~50)
        else -> (51..60).random() // 10% 확률로 위험 범위 값(51~60)
    }
}

