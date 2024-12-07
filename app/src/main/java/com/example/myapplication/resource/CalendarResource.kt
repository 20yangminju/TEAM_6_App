package com.example.myapplication.resource

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Colors
import java.text.SimpleDateFormat
import java.time.LocalDate

import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarHeaderBtn(date: MutableState<Calendar>) {
    val resultTime = SimpleDateFormat("yyyy년 MM월", Locale.KOREA).format(date.value.time)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                val newDate = Calendar.getInstance()
                newDate.time = date.value.time
                newDate.add(Calendar.MONTH, -1)
                date.value = newDate
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "이전 달",
                tint = Colors.Button
            )
        }

        Text(
            text = resultTime,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.Text,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(
            onClick = {

                val newDate = Calendar.getInstance()
                newDate.time = date.value.time
                newDate.add(Calendar.MONTH, 1)
                date.value = newDate
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "이전 달",
                tint = Colors.Button
            )
        }
    }
}

@Composable
fun CalendarDayName() {
    val nameList = listOf("일", "월", "화", "수", "목", "금", "토")
    Row() {
        nameList.forEach {

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Colors.Text
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDayList(date: MutableState<Calendar>, recommendedChargeDate: Calendar, currentDate : LocalDate){

    date.value.set(Calendar.DAY_OF_MONTH, 1)
    val today = Calendar.getInstance()
    val monthDayMax = date.value.getActualMaximum(Calendar.DAY_OF_MONTH)
    val monthFirstDay = date.value.get(Calendar.DAY_OF_WEEK) - 1
    val monthWeeksCount = (monthDayMax + monthFirstDay + 6) / 7


    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(monthWeeksCount) { week ->
            Row (
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                repeat(7) { day ->
                    val resultDay = week * 7 + day - monthFirstDay + 1
                    if(resultDay in 1 .. monthDayMax) {
                        val currentDateCal = Calendar.getInstance().apply {
                            time = date.value.time
                            set(Calendar.DAY_OF_MONTH, resultDay)
                        }
                        val isToday = currentDateCal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                                currentDateCal.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                                currentDateCal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
                        val isRecommendedDate = currentDateCal.get(Calendar.YEAR) == recommendedChargeDate.get(Calendar.YEAR) &&
                                currentDateCal.get(Calendar.MONTH) == recommendedChargeDate.get(Calendar.MONTH) &&
                                currentDateCal.get(Calendar.DAY_OF_MONTH) == recommendedChargeDate.get(Calendar.DAY_OF_MONTH)

                        val isCurrentDate = currentDateCal.get(Calendar.YEAR) == currentDate.year &&
                                currentDateCal.get(Calendar.MONTH) == currentDate.monthValue - 1 &&
                                currentDateCal.get(Calendar.DAY_OF_MONTH) == currentDate.dayOfMonth
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(13.dp)
                                .background(
                                    color = when {
                                        isCurrentDate -> Color.Yellow // Always highlight current date in yellow
                                        isToday -> Colors.Button // Highlight today in blue
                                        isRecommendedDate -> Color.Red // Highlight recommended charge date in red
                                        else -> Color.Transparent // No color for other days
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = resultDay.toString(),
                                fontSize = 15.sp,
                                color = when {
                                    isCurrentDate -> Color.Black
                                    else -> Colors.Text
                                }
                            )
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarApp(recommendedChargeDate: Calendar, currentDate : LocalDate) {
    val calendarInstance = Calendar.getInstance()
    val time = remember { mutableStateOf(calendarInstance) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CalendarHeaderBtn(time)
        CalendarDayName()
        CalendarDayList(time, recommendedChargeDate, currentDate)
    }
}