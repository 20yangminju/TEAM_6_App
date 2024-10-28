package com.example.myapplication.resource

import android.content.Context
import com.example.myapplication.Formet.LoginData
import com.google.gson.Gson
import java.io.InputStreamReader
import java.io.OutputStreamWriter

private fun readLoginDataFromJson(context: Context): LoginData {
    val inputStream = context.assets.open("login.json")
    val reader = InputStreamReader(inputStream)
    val gson = Gson()
    return gson.fromJson(reader, LoginData::class.java)
}

fun writeLoginDataToJson(context: Context, id: String, password: String) {
    // 로그인 데이터를 읽어옵니다.
    val loginData = readLoginDataFromJson(context)

    // 사용자 입력으로 데이터 업데이트
    loginData.email = id
    loginData.password = password

    // 업데이트된 데이터를 JSON 문자열로 변환
    val json = Gson().toJson(loginData)

    // 파일을 내부 저장소에 저장
    OutputStreamWriter(context.openFileOutput("login.json", Context.MODE_PRIVATE)).use {
        it.write(json)
    }

}
