package com.example.myapplication.Formet

import android.provider.ContactsContract.CommonDataKinds.Nickname

data class LoginData(
    var email: String = "null",
    var password: String = "null"
)

data class SignUpData(
    val nickname: String,
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String
)
