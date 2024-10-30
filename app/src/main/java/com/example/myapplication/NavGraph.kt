// NavGraph.kt
package com.example.myapplication.navigation

import BatteryManageScreen
import BatteryTemperatureScreen
import CarModeScreen
import CellBalanceScreen
import ChatScreen
import LoginScreen
import MainScreen
import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.AlarmScreen
import com.example.myapplication.DeviceRegistration
import com.example.myapplication.resource.LoadUserInfoScreen;
import com.example.myapplication.SignUpScreen
import com.example.myapplication.FirstScreen
import com.example.myapplication.RegisterCarScreen

data class LoginData(
    var id: String = "null",
    var password : String = "null"
)

@Composable
fun SetupNavGraph(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "FirstScreen") {
        composable("FirstScreen") {
            FirstScreen(context, onNavigateToLogin = {
                navController.navigate("Login")
            })
        }
        composable("Login") {
            LoginScreen(context,
                onLogin = { navController.navigate("userInfo") },
                onSignUp = { navController.navigate("SignUp") },
                onFirstScreen = { navController.navigate("FirstScreen") },
                onNavigateToMain = { navController.navigate("main") }
            )
        }
        composable("userInfo") {
            LoadUserInfoScreen(context, onNavigateToChat = {
                navController.navigate("ChatScreen")
            })
        }
        composable("main") {
            MainScreen(
                navController = navController,
                onNavigateToSettings = { navController.navigate("Setting") },
                onNavigateToNotifications = { navController.navigate("AlarmScreen")}
            )

        }
        composable("operationMode") { CarModeScreen(
                navController = navController,
                onNavigateToSettings = { navController.navigate("Setting") },
                onNavigateToNotifications = { navController.navigate("AlarmScreen") }

        ) }
        composable("batteryTemperature") { BatteryTemperatureScreen(
            navController = navController,
            onNavigateToSettings = { navController.navigate("Setting") },
            onNavigateToNotifications = { navController.navigate("AlarmScreen") }

        ) }
        composable("cellBalance") { CellBalanceScreen(
            navController = navController,
            onNavigateToSettings = { navController.navigate("Setting") },
            onNavigateToNotifications = { navController.navigate("AlarmScreen") }

        ) }
        composable("AlarmScreen") {
            AlarmScreen(
                onNavigateToHome = {
                    navController.navigate("main")
                },
                onBottomNavigationSelected = { selectedScreen ->
                    navController.navigate(selectedScreen)
                }
            )
        }
        composable("SignUp") {
            SignUpScreen(context,
                //done = { navController.navigate("Login")
                done = { navController.navigate("RegisterCar")},
                onNavigateToLogin = { navController.navigate("Login") }
            )
        }
        composable("DeviceRegistration") {
            DeviceRegistration(
                context = context,
                done = { navController.navigate("main") }
            )
        }
        composable("RegisterCar") {
            RegisterCarScreen(context,
                done = { navController.navigate("DeviceRegistration") },
                onNavigateToPre = { navController.popBackStack() }
            )
        }
        composable("Setting") {
            SettingsScreen(
                onItemClick = { item ->
                    navController.navigate("ItemDetail/$item")
                },
                onNavigateToPre = {
                    navController.popBackStack()
                }
            )
        }
        composable("ChatScreen") {
            ChatScreen(
                navController = navController,
                onMainScreen = {navController.navigate("main")})
        }
    }
}
