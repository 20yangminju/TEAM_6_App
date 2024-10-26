import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.ui.theme.Colors

@Composable
fun MainScreen(
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
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 완속 충전
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Placeholder)
                        .clickable { navController.navigate("charge") }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "경고 아이콘",
                        tint = Colors.IconButton
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "최근 완속 충전 날짜:",
                        color = Colors.Text,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                // 배터리 온도 측정/현재 배터리 충전량
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Colors.IconButton)
                            .clickable { navController.navigate("manage") }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "배터리 온도 측정", color = Colors.Title)
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Colors.Background)
                            .border(width = 2.dp, color = Colors.Title)
                            .clickable { navController.navigate("charge") }
                            .padding(16.dp),

                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "현재 배터리 충전량", color = Colors.Text)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                //  Divider
                Divider(color = Colors.Text, thickness = 1.dp)

                Spacer(modifier = Modifier.height(10.dp))

                // 배터리 소모 속도
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Placeholder)
                        .clickable { navController.navigate("statistics") }
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "배터리 소모 속도", color = Colors.Text)
                }
                Spacer(modifier = Modifier.height(10.dp))
                // 총 주행 거리
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Placeholder)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "총 주행 거리:", color = Colors.Text)
                }
                Spacer(modifier = Modifier.height(10.dp))
                // 총 주행 시간
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Placeholder)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "총 주행 시간:", color = Colors.Text)
                }
                Spacer(modifier = Modifier.height(10.dp))
                // 연비
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Placeholder)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(text = "연비:", color = Colors.Text)
                }
            }
        }
    )
}
