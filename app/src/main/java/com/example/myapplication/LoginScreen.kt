import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.ui.theme.Colors
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.resource.writeLoginDataToJson

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LoginScreen(context: Context, onLogin: () -> Unit, onSignUp: () -> Unit,onFirstScreen: () -> Unit,onNavigateToMain: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val textfieldBackgroundColor = Colors.TextField

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
                        onFirstScreen()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                        tint = Colors.IconButton
                    )
                }
                Text(
                    "로그인",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Colors.Title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Colors.Divider, thickness = 2.dp, modifier = Modifier.fillMaxWidth())
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Colors.Background)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "이메일",
                    color = Colors.Label,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(10.dp))

                // 이메일 작성 Field
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("이메일을 입력해주세요.", color = Colors.Placeholder) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.TextField, RoundedCornerShape(8.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Colors.TextField,
                        textColor = Colors.Text,
                        cursorColor = Colors.Cursor,
                        focusedLabelColor = Colors.Placeholder,
                        unfocusedLabelColor = Colors.Placeholder
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    "비밀번호",
                    color = Colors.Label,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("비밀번호를 입력해주세요.", color = Colors.Placeholder) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(textfieldBackgroundColor, RoundedCornerShape(8.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Colors.TextField,
                        textColor = Colors.Text,
                        cursorColor = Colors.Cursor,
                        focusedLabelColor = Colors.Placeholder,
                        unfocusedLabelColor = Colors.Placeholder
                    )
                )


                Spacer(modifier = Modifier.height(60.dp))

                Button(
                    onClick = {

                        val loginRequest = LoginRequest(email, password)
                        RetrofitInstance.api.login(loginRequest).enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.isSuccessful) {
                                    Log.d("Login", "로그인 성공!")
                                    onLogin()
                                    onNavigateToMain()
                                } else {
                                    Log.d("Login", "로그인 실패: ${response.code()}")
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.e("Login", "로그인 오류 : ${t.message}")
                            }
                        })
                        writeLoginDataToJson(context, email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Colors.Button),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("로그인", color = Colors.ButtonText)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 회원 가입 버튼
                TextButton(
                    onClick = { onSignUp() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "처음이신가요? 회원가입하기",
                        color = Colors.Placeholder,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

// 좀 더 편한 디자인을 위해서 Preview를 추가하였습니다.
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val context = LocalContext.current
    MyApplicationTheme {
        LoginScreen(context = context, onLogin = { }, onSignUp = { }, onFirstScreen = { }, onNavigateToMain = {})
    }
}
