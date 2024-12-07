package com.example.myapplication

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.myapplication.ui.theme.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterCarScreen(context: Context, done : () -> Unit, onNavigateToPre: () -> Unit )
{
    var owner by remember { mutableStateOf("") }
    var carNumber by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // DropdownMenu 상태
    val carTypes = listOf("니로", "코나")//차종 리스트 예시
    var selectedCarType by remember { mutableStateOf("") }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.Background)
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top, // 화면 맨 위쪽에 차량 등록 버튼을 놓는다.
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically, // 수직 정렬(동일 x축에 정렬)
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        // 화살표 클릭시 뒤로가기
                        onNavigateToPre()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로 가기",
                        tint = Colors.IconButton
                    )
                }

                Text("차량 등록",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Colors.Title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                color = Colors.Divider,
                thickness = 2.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center, // 화면 중앙에 요소들을 배치
        )//Column 포함 요소:  소유자명, 차종, 차량번호 버튼
        {
            Text(
                text = "소유자명",
                color = Colors.Label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start) // 왼쪽 정렬
            ) //label을 입력란 밖으로

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = owner,
                onValueChange = { owner = it },
                placeholder = { Text("차량 소유자명을 입력해주세요.", color = Colors.Placeholder) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Colors.Text,
                    containerColor = Colors.TextField,
                    cursorColor = Colors.Cursor,
                ),
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "차종",
                color = Colors.Label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(10.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded } // 메뉴 열림/닫힘 상태 변경
            ) {
                // 선택된 차종 표시
                OutlinedTextField(
                    value = selectedCarType,
                    onValueChange = {},
                    readOnly = true, // 읽기 전용으로 설정 (직접 입력 방지)
                    placeholder = { Text("차량 선택", color = Colors.Placeholder)},
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Colors.Text,
                        focusedBorderColor = Colors.TextField,
                        unfocusedBorderColor = Colors.TextField
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()}
                        .background(Colors.TextField),
                    trailingIcon = {
                        //ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Colors.IconButton
                        )
                    }
                )

                // 드롭다운 메뉴
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }, // 메뉴 닫기
                    modifier = Modifier
                        .background(Colors.TextField)
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() }
                )
                ) {
                    carTypes.forEachIndexed  { index, carType ->
                        DropdownMenuItem(
                            text = { Text(carType, color = Colors.Text,
                                )},
                            onClick = {
                                selectedCarType = carType // 선택된 차종 업데이트
                                expanded = false // 메뉴 닫기
                            }

                        )
                        if (index < carTypes.size - 1) {
                            Divider(color = Color.White, thickness = 1.dp)
                        }

                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "차량번호",
                color = Colors.Label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextField(
                value = carNumber,
                onValueChange = { carNumber = it },
                placeholder = { Text("차량 번호를 입력해주세요.", color = Colors.Placeholder) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Colors.Text,
                    containerColor = Colors.TextField,
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
                    done()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),//버튼 모양 설정


            ) {
                Text("차량 등록")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}

