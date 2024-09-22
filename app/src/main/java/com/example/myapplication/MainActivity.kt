package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.gson.Gson
import java.io.InputStreamReader

data class User(
    val name: String,
    val age: Int,
    val email: String
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 비동기 데이터를 로드하는 컴포저블 함수 호출
                    LoadUserInfoScreen()
                }
            }
        }
    }

    // assets 폴더에서 JSON 파일을 읽어오는 함수
    private fun loadUserFromJson(): User {
        val inputStream = assets.open("data.json")
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        return gson.fromJson(reader, User::class.java)
    }



    @Composable
    fun LoadUserInfoScreen() {
        // remember와 LaunchedEffect를 사용하여 비동기로 데이터 로드
        var user by remember { mutableStateOf<User?>(null) }

        LaunchedEffect(Unit) {
            // 데이터를 로드하는 작업을 비동기로 수행
            user = loadUserFromJson()
        }

        // 데이터가 로드되면 UserInfoScreen 컴포저블을 호출
        user?.let {
            UserInfoScreen(it)
        } ?: run {
            // 데이터를 로딩하는 동안 로딩 메시지 표시
            Text(text = "Loading...", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun UserInfoScreen(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Name: ${user.name}", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Age: ${user.age}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Email: ${user.email}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun UserInfoScreenPreview() {
    val sampleUser = User("John Doe", 30, "johndoe@example.com")
    MyApplicationTheme {
        UserInfoScreen(user = sampleUser)
    }
}
