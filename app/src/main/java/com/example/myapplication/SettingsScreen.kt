import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.resource.SettingItem
import com.example.myapplication.resource.settingItems
import com.example.myapplication.ui.theme.Colors

@Composable
fun SettingsScreen(
    onItemClick: (String) -> Unit,
    onNavigateToPre: () -> Unit,
    navigateToFAQ: () -> Unit, // FAQ 화면으로 이동
    navigateToNotice: () -> Unit, // 공지사항 화면으로 이동
    navigateToInquiry: () -> Unit, //문의사항 화면으로 이동
    navigateToUserInfo: () -> Unit, //회원정보관리 화면으로 이동
    navigateToVersionInfo: () -> Unit, //버전 정보 화면으로 이동
    navigateToTermsOfService: () -> Unit, //이용약관 화면으로 이동
    navigateToPrivacyPolicy: () -> Unit //개인 정보 처리 방침 화면으로 이동

) {
    val groupedItems = settingItems.groupBy { it.section }

    Scaffold(
        backgroundColor = Colors.Background,

        topBar = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Background)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically // 수직 정렬 설정
                ) {
                    IconButton(onClick = { onNavigateToPre() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로 가기", tint = Colors.IconButton)
                    }
                    Spacer(modifier = Modifier.width(8.dp)) // 아이콘과 텍스트 간격
                    Text(
                        text = "환경설정",
                        style = androidx.compose.material3.MaterialTheme.typography.titleLarge.copy(
                            color = Colors.Title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Divider(
                    color = Colors.Divider,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 16.dp) // Divider의 padding 추가
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.Background)
                .padding(innerPadding)
        ) {

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
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                    // 섹션 내의 아이템들
                    items(items) { item ->
                        SettingListItem(
                            settingItem = item,
                            onItemClick = {
                                when (item.title) {
                                    "FAQ" -> navigateToFAQ()
                                    "공지사항" -> navigateToNotice()
                                    "문의사항" -> navigateToInquiry()
                                    "회원 정보 관리" -> navigateToUserInfo()
                                    "버전 정보" -> navigateToVersionInfo()
                                    "이용약관" -> navigateToTermsOfService()
                                    "개인 정보 처리 방침" -> navigateToPrivacyPolicy()
                                    else ->  onItemClick(item.title)
                                }
                            }
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // 아이콘과 텍스트 수직 정렬
        ) {
            // 아이콘 표시
            Icon(
                imageVector = settingItem.icon,
                contentDescription = null,
                tint = Colors.Text, // 원하는 색상 적용
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp)) // 아이콘과 텍스트 간격
            // 텍스트 표시
            Text(
                text = settingItem.title,
                style = MaterialTheme.typography.h6,
                color = Colors.Text
            )
        }
    }
}

