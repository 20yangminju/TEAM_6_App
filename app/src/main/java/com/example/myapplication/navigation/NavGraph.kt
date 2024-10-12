// NavGraph.kt
package com.example.myapplication.navigation

import LoginScreen
import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.LoadUserInfoScreen // LoadUserInfoScreen 함수 임포트
import com.example.myapplication.SignUpScreen
import com.example.myapplication.FirstScreen
import com.example.myapplication.RegisterCarScreen
import com.example.myapplication.writeLoginDataToJson

data class LoginData(
    var id: String = "null",
    var password : String = "null"
)
@Composable
fun SetupNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    NavHost(navController = navController, startDestination = "FirstScreen") {
        composable("userInfo") {
            LoadUserInfoScreen(context) // 화면 컴포저블을 여기서 호출
        }
        composable("FirstScreen") {
            FirstScreen(context, onNavigateToLogin = {
                navController.navigate("Login")
            })
        }
        composable("Login") { 
            LoginScreen(context,
                onLogin = { navController.navigate("userInfo") },
                onSignUp = { navController.navigate("SignUp") }
                )
        // 필요에 따라 더 많은 화면을 추가할 수 있습니다.
        }
        composable("SignUp") {
            SignUpScreen(context,
                done = {navController.navigate("Login")},
                onNavigateToLogin = {
                    navController.navigate("Login") // 뒤로 가기 버튼(화살표 모양) 클릭시 로그인 화면으로 돌아간다.
                }
                )
        }
        composable("RegisterCar") {
            RegisterCarScreen(context,
                done = {navController.navigate("장치 고유 번호 등록")},//후에 장치 고유 번호 페이지 추가시 수정.
                onNavigateToPre = {
                    navController.popBackStack()
                }
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

    }

}
