package com.example.lifelike.entity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class User(
    name: String,
    phone: String
){
    var name by mutableStateOf(name)
    var phone by mutableStateOf(phone)
}
