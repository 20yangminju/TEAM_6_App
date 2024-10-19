package com.example.myapplication

data class SettingItem(
    val title: String,
    val section: String // 각 항목이 속한 섹션 (예: 고객지원, 내 정보 관리, 앱 정보)
)

val settingItems = listOf(
    SettingItem("문의사항", "고객지원",),
    SettingItem("FAQ", "고객지원"),
    SettingItem("공지사항", "고객지원"),
    SettingItem("회원 정보 관리", "내 정보 관리"),
    SettingItem("버전 정보", "앱 정보"),
    SettingItem("이용약관", "앱 정보"),
    SettingItem("개인 정보 처리 방침", "앱 정보")
)
