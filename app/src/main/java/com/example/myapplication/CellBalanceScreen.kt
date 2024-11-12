import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "셀 밸런스 측정",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth() // 화면 너비 채우기 및 중앙 정렬
                )
                Spacer(modifier = Modifier.height(15.dp))
                Divider(color = Color.White, thickness = 2.dp, modifier = Modifier.padding(horizontal = 16.dp))
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
                CellGrid(cells = List(98) { index -> CellData(index + 1, getRandomVoltage()) })
            }
        }
    )
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
        Pair((15..60).random(), "2024-11-08 12:${10 + it} PM") // 샘플 데이터
    }

    val backgroundColor = when {
        cell.voltageDeviation <= 20 -> Color(0xFF87CEEB)
        cell.voltageDeviation in 20..50 -> Color.Red
        else -> Color.DarkGray
    }

    Box(
        modifier = Modifier
            .size(48.dp)
            .background(backgroundColor)
            .border(1.dp, Color.White)
            .padding(4.dp)
            .clickable { showDialog.value = true }, // 클릭 시 알림창 열기
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${cell.index}",
                color = Colors.Text,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = "${cell.voltageDeviation}",
                color = Colors.Text,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "mV",
                color = Colors.Text,
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            )
        }
    }

    // 알림창 호출
    CellVoltageHistoryDialog(
        showDialog = showDialog.value,
        onDismiss = { showDialog.value = false },
        voltageHistory = voltageHistory
    )
}


data class CellData(val index: Int, val voltageDeviation: Int)

// 샘플 데이터를 위해 임의의 전압 편차 값을 생성하는 함수
fun getRandomVoltage(): Int {
    return (15..60).random()
}
