package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication.navigation.SetupNavGraph
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.resource.NotificationViewModel

class MainActivity : ComponentActivity() {
    private val notificationViewModel: NotificationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // NavController를 기억하고 네비게이션 그래프에 전달
                    val navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController,
                        notificationViewModel = notificationViewModel
                    )

                }
            }
        }
    }
}
