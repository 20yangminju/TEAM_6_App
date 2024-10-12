package com.example.myapplication

data class SettingItem(val title: String)

val settingItems = listOf(
    SettingItem("문의사항"),
    SettingItem("FAQ"),
    SettingItem("공지사항"),
    SettingItem("회원 정보 관리"),
    SettingItem("차량 등록"),
    SettingItem("버전 정보"),
    SettingItem("이용약관"),
    SettingItem("개인 정보 처리 방침")
)