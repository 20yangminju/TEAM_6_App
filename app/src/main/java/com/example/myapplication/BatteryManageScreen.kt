import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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
    var batteryData by remember { mutableStateOf<BatteryRequest?>(null) }

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
                            batteryData = RetrofitInstance.api.batteryInfo()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }) {
                    Text("Get Battery Info")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // 배터리 데이터 표시
                batteryData?.let {
                    Text("Voltage: ${it.auxiliaryBatteryVoltage}", color = Colors.Text)
                    Text("Battery Power: ${it.batteryPower}", color = Colors.Text)
                    Text("State of Charge: ${it.stateOfChargeDisplay}", color = Colors.Text)
                    Text("Charging Status: ${if (it.hvChargingStatus) "Charging" else "Not Charging"}", color = Colors.Text)
                    Text("Battery Current: ${it.batteryCurrent}", color = Colors.Text)
                    Text("Last Updated: ${it.createdAt}", color = Colors.Text)
                } ?: Text("No battery data available", color = Colors.Text)
            }
        }
    )
}
