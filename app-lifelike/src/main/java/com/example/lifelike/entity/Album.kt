package com.example.lifelike.entity

import java.io.Serializable

data class Album(
    val name: String,
    val photos: List<Photo>
)  : Serializable


val albums = listOf(
    Album("Trip to Budapest", MutableList(19) { i -> Photo(i) }),
    Album("Cats", MutableList(39) { i -> Photo(i) }),
    Album("Family", MutableList(162) { i -> Photo(i) }),
    Album("Todo", MutableList(15) { i -> Photo(i) }),
    Album("California road trip", MutableList(112) { i -> Photo(i) }),
    Album("KotlinConf", MutableList(43) { i -> Photo(i) }),
    Album("Summer of '19", MutableList(72) { i -> Photo(i) })
)