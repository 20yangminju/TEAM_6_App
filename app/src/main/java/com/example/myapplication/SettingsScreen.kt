import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.SettingItem
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.settingItems
import com.example.myapplication.ui.theme.Colors

@Composable
fun SettingsScreen(
    onItemClick: (String) -> Unit,
    onNavigateToPre: () -> Unit // 항목 클릭 시 처리할 콜백 함수
) {
    Scaffold(
        backgroundColor = Colors.Background,
        bottomBar = {
            BottomNavigationBar(
                currentScreen = "Settings",
                onItemSelected = { screen ->
                    // 네비게이션 바에서 선택된 화면에 맞게 처리
                }
            )
        },
        topBar = {
            TopAppBar(
                backgroundColor = Colors.Background,
                title = {
                    Text(text = "환경설정", color = Colors.Text)
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateToPre() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로 가기", tint = Colors.IconButton)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Background) // 배경을 검정색으로 설정
                .padding(innerPadding)
        ) {
            Divider(color = Colors.Divider, thickness = 1.dp)

            // 항목 나열
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(settingItems.size) { index ->
                    val item = settingItems[index]
                    SettingListItem(
                        settingItem = item,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

@Composable
fun SettingListItem(settingItem: SettingItem, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(settingItem.title) }
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        backgroundColor = Colors.TextField
    ) {
        Text(
            text = settingItem.title,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.h6,
            color = Colors.Text
        )
    }
}
