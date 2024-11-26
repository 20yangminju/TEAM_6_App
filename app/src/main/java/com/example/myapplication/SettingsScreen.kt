import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.resource.SettingItem
import com.example.myapplication.resource.settingItems
import com.example.myapplication.ui.theme.Colors

@Composable
fun SettingsScreen(
    onItemClick: (String) -> Unit,
    onNavigateToPre: () -> Unit
) {
    val groupedItems = settingItems.groupBy { it.section }

    Scaffold(
        backgroundColor = Colors.Background,
//        bottomBar = {
//            BottomNavigationBar(
//                currentScreen = "Settings",
//                onItemSelected = { screen ->
//                    // 네비게이션 바에서 선택된 화면에 맞게 처리
//                }
//            )
//        },
        topBar = {
            TopAppBar(
                backgroundColor = Colors.Background,
                title = {
                    Text(text = "환경설정", color = Colors.Title)
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
                .background(Colors.Background)
                .padding(innerPadding)
        ) {
            Divider(color = Colors.Divider, thickness = 1.dp)

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                groupedItems.forEach { (section, items) ->
                    // 각 섹션의 헤더
                    item {
                        Column {
                            Text(
                                text = section,
                                style = MaterialTheme.typography.subtitle1,
                                color = Colors.Text,
                                modifier = Modifier.padding(vertical = 12.dp)
                            )
                            GradientLine() // 그라데이션 선 추가
                        }
                    }
                    // 섹션 내의 아이템들
                    items(items) { item ->
                        SettingListItem(
                            settingItem = item,
                            onItemClick = onItemClick
                        )
                    }
                    // 각 섹션의 마지막에 Divider 추가
                    item {
                        Divider(color = Colors.Divider, thickness = 1.dp)
                    }
                }
            }
        }
    }
}

@Composable
fun GradientLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp) // 양쪽에 패딩 적용
            .height(2.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Gray, Color.White, Color.Gray)
                )
            )
    )
}

@Composable
fun SettingListItem(settingItem: SettingItem, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(settingItem.title) }
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        backgroundColor = Colors.Background
    ) {
        Text(
            text = settingItem.title,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.h6,
            color = Colors.Text
        )
    }
}
