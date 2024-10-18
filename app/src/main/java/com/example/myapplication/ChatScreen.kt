import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import retrofit2.HttpException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    // 사용자의 입력과 GPT의 응답을 저장할 상태
    var userInput by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf("Chat history:\n") }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        // 채팅 히스토리 표시
        Text(
            text = chatHistory,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 사용자 입력 필드
        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Enter your message") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 전송 버튼
        Button(
            onClick = {
                if (userInput.isNotEmpty()) {
                    coroutineScope.launch {
                        val response = sendMessageToGPT(userInput)
                        chatHistory += "You: $userInput\nGPT: $response\n"
                        userInput = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send")
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



@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}
