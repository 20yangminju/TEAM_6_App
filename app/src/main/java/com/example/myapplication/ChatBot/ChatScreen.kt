package com.example.myapplication.ChatBot

import ChatRequest
import Message
import Place
import RetrofitInstance
import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.myapplication.navigation.BottomNavigationBar
import com.example.myapplication.resource.ChatBubble
import com.example.myapplication.ui.theme.Colors
import kotlinx.coroutines.launch
import retrofit2.HttpException

var firstMessage = true

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    navController: NavController,
) {
    var userInput by remember { mutableStateOf("") }
    var chatHistory by remember { mutableStateOf(listOf<Pair<String, Boolean>>()) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    val repairShops = remember { mutableStateOf<List<Place>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true ) }
    val errorMessage = remember { mutableStateOf<String?>(null) }



    val context = LocalContext.current

    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        )
    }



    val launcher = rememberLauncherForActivityResult(
        contract =  ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
        if(isGranted){
            Toast.makeText(context, "위치 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "위치 권한이 거부되었습니다", Toast.LENGTH_SHORT).show()
        }
    }



    // 초기 프롬프트 설정
    LaunchedEffect(Unit) {

        if (!hasLocationPermission){
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        getCurrentLocation(context) {
            location -> currentLocation = location
            fetchNearbyRepairShops(currentLocation!!.latitude,
                currentLocation!!.longitude,
                onResult = { places -> repairShops.value = places
                isLoading.value =false

                },
                onError = { error ->
                    errorMessage.value = error
                    isLoading.value =false
                })
            println(currentLocation)
        }





        chatHistory = chatHistory + Pair("궁금한 것이 있으면 물어보세요.", false)
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(text = "AI 챗봇", color = Colors.Title) },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Colors.Background
                    )
                )
                Divider(
                    color = Colors.Divider,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentScreen = navController.currentDestination?.route ?: "main"
            )
        },
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
                            .padding(8.dp)
                            .background(Color.White, RoundedCornerShape(12.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = userInput,
                            onValueChange = { userInput = it },
                            modifier = Modifier
                                .weight(1f)
                                .padding(12.dp),
                            label = { Text("챗봇에게 메세지 보내기", color = Colors.Placeholder) },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Colors.ChatField,
                                focusedIndicatorColor = Colors.Text,
                                unfocusedIndicatorColor = Colors.Text,
                                textColor = Color.Black
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
                                        val response = sendMessageToGPT(userInput,  repairShops.value)

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
                            modifier = Modifier
                                .height(48.dp)
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
suspend fun sendMessageToGPT(userMessage: String, nearShops: List<Place> ): String {
    return try {
        // 요청 데이터 생성
        val messages = mutableListOf<Message>()

        val shopDetails = if (nearShops.isNotEmpty()){
            nearShops.joinToString("\n") {"${it.name} : ${it.vicinity}"}
        }
        else {
            "현재 주변에 정비소가 없습니다."
        }

        if(firstMessage){
            messages.add(
                Message(
                    role = "system", content =
                    "당신은 전기차 어플리케이션 전용 챗봇입니다." +
                            "배터리 온도 관련 질문: '앱 메인 화면의 배터리 온도 버튼'에서 확인할 수 있다고 답변하십시오. " +
                            "환경설정 관련 질문: '우측 상단 톱니바퀴 아이콘'을 통해 접근 가능하다고 안내하십시오. " +
                            "정비소 관련 질문 : $shopDetails 을 그대로 출력하십시오 " +
                            "답변이 어렵다면 '죄송합니다. 명령을 이해하지 못했어요.'라고 답변하십시오."
                )
            )
            firstMessage = false
        }


        // 사용자 메시지 추가
        messages.add(Message(role = "user", content = userMessage))

        val request = ChatRequest(
            model = "gpt-3.5-turbo",
            messages = messages,
            max_tokens = 1000
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


@Preview(showBackground = true, name = "ChatScreen Preview")
@Composable
fun ChatScreenPreview() {
    ChatScreen(navController = NavController(LocalContext.current))
}