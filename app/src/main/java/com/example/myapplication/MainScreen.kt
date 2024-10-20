import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.ui.theme.Colors

@Composable
fun MainScreen(
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
                        // 환경설정 아이콘 버튼
                        IconButton(onClick = { onNavigateToSettings() }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "환경설정",
                                tint = Colors.IconButton
                            )
                        }
                        // 알림 아이콘 버튼
                        IconButton(onClick = { onNavigateToNotifications() }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "알림",
                                tint = Colors.IconButton
                            )
                        }
                    }
                )
                // Divider 추가
                Divider(color = Colors.Divider, thickness = 1.dp)
            }
        },
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "Settings",
                onItemSelected = { screen ->
                    // 네비게이션 바에서 선택된 화면에 맞게 처리
                }
            )
        },
        content = { innerPadding ->
            // 메인 화면의 콘텐츠
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // 메인 화면의 내용을 여기에 추가
                Text(text = "콘텐츠 작성할 곳")
            }
        }
    )
}
