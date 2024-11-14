package com.example.myapplication.ChatBot

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.widget.Toast

@Composable
fun RequestLocationPermission() {
    val context = LocalContext.current
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
        if (isGranted) {
            Toast.makeText(context, "위치 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
            // 권한이 허용되었을 때 위치 가져오기 호출
            getCurrentLocation(context) { location ->
                if (location != null) {
                    Toast.makeText(
                        context,
                        "현재 위치: 위도 ${location.latitude}, 경도 ${location.longitude}",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(context, "위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(context, "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    if (!hasLocationPermission) {
        Button(onClick = {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }) {
            Text("위치 권한 요청")
        }
    } else {
        Text("위치 권한이 이미 허용되었습니다.")
        // 권한이 이미 허용된 경우 위치 가져오기 호출
        getCurrentLocation(context) { location ->
            if (location != null) {
                Toast.makeText(
                    context,
                    "현재 위치: 위도 ${location.latitude}, 경도 ${location.longitude}",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(context, "위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
