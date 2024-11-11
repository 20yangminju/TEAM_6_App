package com.example.myapplication.resource

import androidx.lifecycle.ViewModel
import com.example.myapplication.NotificationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NotificationViewModel : ViewModel() {
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications

    fun addNotification(icon: Int, title: String, date: String) {
        val newNotification = NotificationItem(icon, title, date)
        _notifications.value = _notifications.value + newNotification
    }
}