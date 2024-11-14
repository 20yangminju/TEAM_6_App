package com.example.myapplication.ChatBot

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.location.Priority
import android.location.Location

@SuppressLint("MissingPermission") // 권한이 적절히 요청되었는지 확인
fun getCurrentLocation(context: Context, onLocationRetrieved: (Location?) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val cancellationTokenSource = CancellationTokenSource()

    fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationTokenSource.token)
        .addOnSuccessListener { location: Location? ->
            onLocationRetrieved(location)
        }
        .addOnFailureListener { e ->
            println("위치 가져오기 실패: ${e.message}")
            onLocationRetrieved(null)
        }
}
