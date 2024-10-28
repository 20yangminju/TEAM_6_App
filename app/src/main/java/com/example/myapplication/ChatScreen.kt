import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.resource.ChatBubble
import com.example.myapplication.ui.theme.Colors
import kotlinx.coroutines.launch
import retrofit2.HttpException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(onMainScreen: () -> Unit) {
    // 사용자의 입력과 GPT의 응답을 저장할 상태
    var userInput by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        chatHistory = chatHistory + Pair("환영합니다! 궁금한게 무엇인가요?", false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically

            ) {
                IconButton(
                    onClick = {
                        onMainScreen()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "홈화면 이동",
                        tint = Colors.IconButton
                    )
                }
                Text(
                    "챗봇",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Colors.Title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Colors.Divider, thickness = 2.dp, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(50.dp))

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                items(chatHistory) {(message, isUserMessage) ->
                    ChatBubble(message = message, isUserMessage = isUserMessage)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = userInput,
                    onValueChange = { userInput = it },
                    modifier = Modifier
                        .weight(0.8f)
                        .padding(end = 8.dp),
                    label = { Text("챗봇에게 메세지 보내기", color = Colors.Placeholder) },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Colors.TextField,
                        focusedIndicatorColor = Colors.Placeholder,
                        unfocusedIndicatorColor = Colors.Placeholder,
                        textColor = Colors.Text
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                // 전송 버튼
                Button(
                    onClick = {
                        if (userInput.isNotEmpty()) {
                            coroutineScope.launch {
                                chatHistory = chatHistory + Pair(userInput, true)
                                val response = sendMessageToGPT(userInput)
                                chatHistory = chatHistory + Pair(response, false)
                                userInput = ""

                                val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                if (lastVisibleItemIndex == null || lastVisibleItemIndex >= chatHistory.size - 3) {
                                    // 마지막 메시지 근처에 있을 때만 자동 스크롤
                                    listState.animateScrollToItem(chatHistory.size - 1)
                                }
                            }
                        }
                    },
                    modifier = Modifier.height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.Button)
                    ) {
                        Text("전송")
                    }
                }
            }
        }
    }

// GPT API 호출 함수 (기존처럼 정의)
suspend fun sendMessageToGPT(userMessage: String): String {
    return try {
        // 요청 데이터 생성
        val request = ChatRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(
                Message(role = "user", content = userMessage)  // 사용자 메시지 추가
            ),
            max_tokens = 100
        )

        // API 호출 및 응답 받기
        val response = RetrofitInstance.api.sendMessage(request)
        println("API Response: $response")

        // 응답에서 첫 번째 메시지의 내용을 가져옴
        response.choices.firstOrNull()?.message?.content?.trim() ?: "No response from GPT"

    } catch (e: HttpException) {
        if (e.code() == 429) {
            val retryAfter = e.response()?.headers()?.get("Retry-After")
            "Too Many Requests: Retry after $retryAfter seconds."
        } else {
            "HTTP Error: ${e.code()} - ${e.message()}"
        }
    }
}
