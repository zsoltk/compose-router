package com.example.lifelike.entity

import java.io.Serializable

data class Photo(
    val id: Int
) : Serializable {
    val title: String = "Photo #$id"
}
