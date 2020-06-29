package com.example.lifelike.entity

import androidx.compose.getValue
import androidx.compose.mutableStateOf
import androidx.compose.setValue


class User(
    name: String,
    phone: String
){
    var name by mutableStateOf(name)
    var phone by mutableStateOf(phone)
}
