package com.example.lifelike.entity

data class Photo(
    val id: Int
) {
    val title: String = "Photo #$id"
}
