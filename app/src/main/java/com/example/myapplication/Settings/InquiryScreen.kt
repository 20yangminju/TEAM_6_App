package com.example.myapplication.screens

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Properties
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Composable
fun InquiryScreen(
    onNavigateBack: () -> Unit,
    onSubmitInquiry: (String, String, String) -> Unit // 제목, 이메일, 내용 전달
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var content by remember { mutableStateOf(TextFieldValue("")) }
    var isSubmitted by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        backgroundColor = Colors.Background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "뒤로 가기",
                            tint = Colors.IconButton
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "문의사항",
                        fontSize = 20.sp,
                        color = Colors.Title
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Divider(
                    color = Colors.Divider,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isSubmitted) {
                Text(
                    text = "문의사항이 접수되었습니다. 감사합니다!",
                    color = Colors.Title,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            } else {
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red, // 에러 메시지 색상
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("문의 제목", color = Colors.Placeholder) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Colors.TextField,
                        focusedBorderColor = Colors.TextField,
                        unfocusedBorderColor = Colors.TextField,
                        cursorColor = Colors.TextField
                    )
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("답변 받을 이메일 주소", color = Colors.Placeholder) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Colors.TextField,
                        focusedBorderColor = Colors.TextField,
                        unfocusedBorderColor = Colors.TextField,
                        cursorColor = Colors.TextField
                    )
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("문의 내용", color = Colors.Placeholder) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    maxLines = 5,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Colors.TextField,
                        focusedBorderColor = Colors.TextField,
                        unfocusedBorderColor = Colors.TextField,
                        cursorColor = Colors.TextField
                    )
                )

                Button(
                    onClick = {
                        if (title.text.isBlank()) {
                            errorMessage = "문의 제목을 입력해주세요."
                        } else if (content.text.isBlank()) {
                            errorMessage = "문의 내용을 입력해주세요."
                        } else if (email.text.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email.text).matches()) {
                            errorMessage = "유효한 이메일 주소를 입력해주세요."
                        } else {
                            errorMessage = ""
                            CoroutineScope(Dispatchers.IO).launch {
                                // Send email logic
                                sendInquiryEmail(
                                    toEmail = "25key@naver.com",
                                    fromEmail = email.text,
                                    subject = title.text,
                                    message = content.text
                                )
                                isSubmitted = true
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Colors.Button
                    )
                ) {
                    Text(
                        text = "문의 접수",
                        color = Colors.ButtonText
                    )
                }
            }
        }
    }
}

fun sendInquiryEmail(toEmail: String, fromEmail: String, subject: String, message: String) {
    val host = "smtp.gmail.com" // SMTP 서버 (예: Gmail)
    val port = "587" // SMTP 포트 (TLS)
    val username = "your-email@gmail.com" // SMTP 서버 인증 이메일
    val password = "your-email-password" // SMTP 서버 인증 비밀번호

    // SMTP 서버 설정
    val properties = Properties().apply {
        put("mail.smtp.auth", "true") // 인증 사용
        put("mail.smtp.starttls.enable", "true") // TLS 사용
        put("mail.smtp.host", host)
        put("mail.smtp.port", port)
    }

    // 인증 정보 설정
    val session = Session.getInstance(properties, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(username, password)
        }
    })

    try {
        // 이메일 메시지 생성
        val mimeMessage = MimeMessage(session).apply {
            setFrom(InternetAddress(username)) // 발신자
            setRecipient(Message.RecipientType.TO, InternetAddress(toEmail)) // 수신자
            this.subject = subject // 제목 설정 (명시적으로 this 사용)
            setText("보낸 사람: $fromEmail\n\n$message") // 내용
        }

        // 이메일 전송
        Transport.send(mimeMessage)
        println("이메일이 성공적으로 전송되었습니다.")
    } catch (e: MessagingException) {
        e.printStackTrace()
        println("이메일 전송 중 오류 발생: ${e.message}")
    }

}