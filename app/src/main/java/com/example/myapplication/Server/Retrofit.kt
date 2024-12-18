// RetrofitInstance.kt
package com.example.myapplication.network

import ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://o5127a1und.execute-api.ap-northeast-2.amazonaws.com/prod/" // 에뮬레이터의 로컬호스트 주소

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
