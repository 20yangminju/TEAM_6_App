// UserInfoScreen.kt
package com.example.myapplication.resource

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import java.io.InputStreamReader

data class User(
    val name: String,
    val age: Int,
    val email: String
)

@Composable
fun LoadUserInfoScreen(context: Context, onNavigateToChat: () -> Unit) {
    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(Unit) {
        user = loadUserFromJson(context) // Context를 매개변수로 전달
    }

    user?.let {
        UserInfoScreen(it, onNavigateToChat)
    } ?: run {
        Text(text = "Loading...", modifier = Modifier.padding(16.dp))
    }
}

// JSON에서 사용자 데이터 로드하는 함수
private fun loadUserFromJson(context: Context): User {
    val inputStream = context.assets.open("data.json") // Context를 사용하여 assets 접근
    val reader = InputStreamReader(inputStream)
    val gson = Gson()
    return gson.fromJson(reader, User::class.java)
}

@Composable
fun UserInfoScreen(user: User, onNavigateToChat: () -> Unit) {
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

        Button(onClick = onNavigateToChat, modifier = Modifier.fillMaxWidth()) { // ChatScreen으로 이동 버튼
            Text("Go to Chat")
        }
    }

}
