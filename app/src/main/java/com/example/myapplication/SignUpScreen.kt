package com.example.myapplication

import RegisterRequest
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.ui.theme.Colors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(context: Context, done : () -> Unit, onNavigateToLogin: () -> Unit )
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var signUpMessage by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Background) // 배경화면을 검은색으로 설정
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top, // 화면 맨 위쪽에 회원가입 버튼을 놓는다.
        )//Column 포함 요소: 화살표 버튼, 회원가입 Text, 구분선
        {
            Row(
                verticalAlignment = Alignment.CenterVertically, // 수직 정렬(동일 x축에 정렬)
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        // 화살표 클릭시 로그인 화면으로 이동(NavGraph 참고)
                        onNavigateToLogin()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                        tint = Colors.IconButton // 아이콘 색상을 흰색으로 설정
                    )
                }

                Text("회원가입",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Colors.Title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) //구분선과의 공백

            Divider(
                color = Colors.Divider, // 선 색상을 흰색으로 설정
                thickness = 2.dp,
                modifier = Modifier.fillMaxWidth() // 화면 전체 너비에 걸쳐 선을 그리도록 설정
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center, // 화면 중앙에 요소들을 배치
        )//Column 포함 요소: 이메일, 비밀번호, 비밀번호 확인, 회원가입 버튼
        {
            Text(
                text = "이메일",
                color = Colors.Label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start) // 왼쪽 정렬
            ) //label을 입력란 밖으로

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                //label = { Text("이메일") },
                placeholder = { Text("example@email.com", color = Colors.Placeholder) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Colors.Text, // 입력된 텍스트를 흰색으로 설정
                    containerColor = Colors.TextField, // 입력란 배경을 어두운 회색으로 설정
                    cursorColor = Colors.Cursor,
                ),
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "비밀번호",
                color = Colors.Label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                //label = { Text("비밀번호") },
                placeholder = { Text("영문, 숫자, 특수문자 조합(8~16자)", color = Colors.Placeholder) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Colors.Text, // 입력된 텍스트를 흰색으로 설정
                    containerColor = Colors.TextField, // 입력란 배경을 어두운 회색으로 설정
                    cursorColor = Colors.Cursor,
                ),


                )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "비밀번호 확인",
                color = Colors.Label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                //label = { Text("비밀번호 확인") },
                placeholder = { Text("비밀번호를 다시 입력해주세요.", color = Colors.Placeholder) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Colors.Text, // 입력된 텍스트를 흰색으로 설정
                    containerColor = Colors.TextField, // 입력란 배경을 어두운 회색으로 설정
                    cursorColor = Colors.Cursor,
                ),
            )

            Spacer(modifier = Modifier.height(60.dp))

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Colors.Button,
                    contentColor = Colors.ButtonText
                ),
                onClick = {
                    // 비밀번호 일치 여부 확인
                    if (password == confirmPassword) {
                        // 회원가입 로직 추가
                        val registerRequest = RegisterRequest(email, email, password)
                        RetrofitInstance.api.register(registerRequest).enqueue(object : Callback<Void>{
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.isSuccessful){
                                    Log.d("Register", "회원가입 성공!")
                                    signUpMessage = "회원가입 완료!"
                                    done()
                                }else{
                                    Log.d("Register", "회원가입 실패: ${response.code()}")
                                    signUpMessage = "회원가입 실패!"
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.e("Register", "회원가입 오류 : ${t.message}")
                            }

                        })

                    } else {
                        signUpMessage = "비밀번호가 일치하지 않습니다."
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),//버튼 모양 설정


            ) {
                Text("회원가입")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = signUpMessage, color = Colors.Alert) // 회원가입 메시지 표시
        }

    }
}



