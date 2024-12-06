package com.example.myapplication.Server

import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.ResponseBody
import retrofit2.Response

data class SOHRequest(val device_number : String)
data class SOHResponse(val device_number: String, val predicted_SOH : String)



interface AiApi{
    @POST("predict")
    suspend fun SOH(@Body SOHRequest: SOHRequest): SOHResponse
    @POST("image")
    suspend fun Image(@Body SOHRequest: SOHRequest): Response<ResponseBody>
}
