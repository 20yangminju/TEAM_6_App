// TemperatureHistoryList.kt
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.myapplication.ui.theme.Colors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TemperatureHistoryList(temperatureHistory: List<String>, onConfirm: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // 내용물 중앙 정렬
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp, max = 600.dp) // 알림창의 최소 및 최대 높이 설정
                .background(Color.White, RoundedCornerShape(8.dp)) // 배경색과 모서리 둥글기
                .padding(16.dp)
        ) {
            // 제목
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Colors.Title, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "온도 기록",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Colors.Text // 텍스트 색상 변경
                )
            }

            Spacer(modifier = Modifier.height(20.dp)) // 제목과 리스트 간 간격

            // 리스트
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false), // 나머지 공간 채우기 제거
                verticalArrangement = Arrangement.spacedBy(8.dp) // 항목 간 간격
            ) {
                items(temperatureHistory) { temp ->
                    Spacer(modifier = Modifier.height(5.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp), // 항목 좌우 여백
                        elevation = 4.dp,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White) // 온도 기록 배경색
                                .padding(16.dp)
                        ) {
                            // 온도
                            Text(
                                text = temp.split("\n")[0], // "온도" 부분
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black // 텍스트 색상
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            // 날짜
                            Text(
                                text = temp.split("\n")[1], // "날짜" 부분
                                fontSize = 14.sp,
                                color = Color.Gray // 날짜 텍스트 색상
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }

            Spacer(modifier = Modifier.height(20.dp)) // 리스트와 버튼 간 간격

            // 확인 버튼
            Button(
                onClick = { onConfirm() }, // 버튼 클릭 시 동작 설정
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Button) // 버튼 배경색 설정
            ) {
                Text(
                    text = "확인",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Colors.ButtonText
                )
            }
        }
    }
}
