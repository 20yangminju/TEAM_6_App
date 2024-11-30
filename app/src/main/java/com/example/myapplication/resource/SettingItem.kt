package com.example.myapplication.resource

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

data class SettingItem(
    val title: String,
    val section: String, // 각 항목이 속한 섹션 (예: 고객지원, 내 정보 관리, 앱 정보)
    val icon: ImageVector // 아이콘 리소스 추가
)

// 섹션별로 아이콘 설정
val settingItems = listOf(
    SettingItem("문의사항", "고객지원", Icons.Default.Email),
    SettingItem("FAQ", "고객지원", Icons.Default.List),
    SettingItem("공지사항", "고객지원", Icons.Default.Star),
    SettingItem("회원 정보 관리", "내 정보 관리", Icons.Default.AccountCircle),
    SettingItem("버전 정보", "앱 정보", Icons.Default.Info),
    SettingItem("이용약관", "앱 정보", Icons.Default.CheckCircle),
    SettingItem("개인 정보 처리 방침", "앱 정보", Icons.Default.Edit)
)
