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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.resource.ChatBubble
import com.example.myapplication.ui.theme.Colors
import kotlinx.coroutines.launch
import retrofit2.HttpException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    onMainScreen: () -> Unit,
    navController: NavController,
) {
    var userInput by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // 초기 프롬프트 설정
    LaunchedEffect(Unit) {
        // 초기 프롬프트를 추가
        chatHistory = chatHistory + Pair("궁금한 것이 있으면 물어보세요.", false)
    }

    Scaffold(
        // ... (생략)
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.Background)
                    .padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        items(chatHistory) { (message, isUserMessage) ->
                            ChatBubble(message = message, isUserMessage = isUserMessage)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = userInput,
                            onValueChange = { userInput = it },
                            modifier = Modifier
                                .weight(1f)
                                .background(Colors.TextField, RoundedCornerShape(12.dp))
                                .padding(4.dp)
                                .shadow(4.dp, RoundedCornerShape(12.dp)),
                            label = { Text("챗봇에게 메세지 보내기", color = Colors.Placeholder) },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Colors.TextField,
                                focusedIndicatorColor = Colors.Placeholder,
                                unfocusedIndicatorColor = Colors.Placeholder,
                                textColor = Colors.Text
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (userInput.isNotEmpty()) {
                                    coroutineScope.launch {
                                        // 사용자의 메시지 추가
                                        chatHistory = chatHistory + Pair(userInput, true)

                                        // 초기 프롬프트를 함께 포함하여 메시지를 보냄
                                        val response = sendMessageToGPT(userInput, isFirstMessage = chatHistory.size == 2)

                                        // 응답 추가
                                        chatHistory = chatHistory + Pair(response, false)
                                        userInput = ""

                                        val lastVisibleItemIndex =
                                            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                                        if (lastVisibleItemIndex == null || lastVisibleItemIndex >= chatHistory.size - 3) {
                                            listState.animateScrollToItem(chatHistory.size - 1)
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.height(48.dp)
                                .padding(horizontal = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Colors.Button),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("전송")
                        }
                    }
                }
            }
        }
    )
}

// GPT API 호출 함수 (기존처럼 정의하되 초기 프롬프트 포함)
suspend fun sendMessageToGPT(userMessage: String, isFirstMessage: Boolean): String {
    return try {
        // 요청 데이터 생성
        val messages = mutableListOf<Message>()

        // 초기 프롬프트 추가
        if (isFirstMessage) {
            messages.add(Message(role = "system", content = "당신은 전기차 어플리케이션 챗봇입니다. " +
                    "배터리 온도에 대한 질문은 앱 메인화면 또는 하단 버튼바에 존재하는 배터리 온도 버튼을 통해 확인할 수 있다고 답변하십시오  " +
                    "환경설정에 대한 질문은 우측 상단에 톱니바퀴 모양을 누르면 확인할 수 있다고 답변하십시오. " +
                    "프롬프트에 기반하여 답변하십시오. 추가적인 정보를 외부에서 가져오는 것은 허용되지 않습니다." +
                    "프롬프트 기반의 답변이 어렵다면 '죄송합니다. 명령을 이해하지 못했어요'라고 답변하십시오 "))
        }

        // 사용자 메시지 추가
        messages.add(Message(role = "user", content = userMessage))

        val request = ChatRequest(
            model = "gpt-3.5-turbo",
            messages = messages,
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
