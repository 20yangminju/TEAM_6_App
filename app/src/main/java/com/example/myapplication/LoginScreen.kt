import android.content.Context
import android.text.style.StyleSpan
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.writeLoginDataToJson

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LoginScreen(context: Context, onLogin: (String, String) -> Unit, onSignUp: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val textfieldBackgroundColor = Color(0xFF2C2C2E)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1C1C1E))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "로그인",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = Color(0xFFD1D1D6),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
        Divider(color = Color.White, thickness = 1.dp)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1C1C1E))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("이메일",
                color = Color.White,
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
                label = { Text("이메일을 입력해주세요.", color = Color(0xFFD1D1D6))},
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2C2C2E), RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.DarkGray,
                    textColor = Color(0xFFD1D1D6),
                    cursorColor = Color(0xFFD1D1D6),
                    focusedLabelColor = Color(0xFFD1D1D6),
                    unfocusedLabelColor = Color(0xFFD1D1D6)
                )
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text("비밀번호",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호를 입력해주세요.", color = Color(0xFFD1D1D6)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(textfieldBackgroundColor, RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.DarkGray,
                    textColor = Color(0xFFD1D1D6),
                    cursorColor = Color(0xFFD1D1D6),
                    focusedLabelColor = Color(0xFFD1D1D6),
                    unfocusedLabelColor = Color(0xFFD1D1D6)
                )
            )


            Spacer(modifier = Modifier.height(60.dp))

            Button(
                onClick = {
                    onLogin(email, password)
                    writeLoginDataToJson(context, email, password)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("로그인", color = Color(0xFFD1D1D6))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 회원 가입 버튼
            TextButton(
                onClick = { onSignUp() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("처음이신가요? 회원가입하기",
                    color = Color(0xFFD1D1D6),
                    textAlign = TextAlign.Center
                )}
            TextButton(
                onClick = {/*추후 구현해야하는 로직*/},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("아이디 또는 비밀번호 찾기",
                    color = Color(0xFFD1D1D6),
                    textAlign = TextAlign.Center)
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
        LoginScreen(context = context, onLogin = { _, _ -> }, onSignUp = { })
    }
}
