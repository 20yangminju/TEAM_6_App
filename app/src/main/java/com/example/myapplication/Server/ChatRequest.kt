// 요청 데이터 클래스 (API에 보낼 형식)
data class ChatRequest(
    val model: String = "gpt-3.5-turbo",  // 사용할 GPT 모델
    val messages: List<Message>,  // 메시지 리스트
    val max_tokens: Int = 100  // 최대 토큰 수
)

// 메시지 데이터 클래스
data class Message(
    val role: String,  // "system", "user", "assistant"
    val content: String  // 메시지 내용
)

// 응답 데이터 클래스 (API에서 받을 형식)
data class ChatResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message  // GPT 응답 메시지
)
