package com.example.poormansbackstack.backstack

import androidx.compose.Model

@Model
class BackStack<T>(
    defaultElement: T
) {
    private var elements: List<T> = listOf(defaultElement)

    val size: Int
        get() = elements.size

    fun last(): T =
        elements.last()

    fun push(element: T) {
        elements = elements + element
    }

    fun pop(): Boolean =
        // we won’t let the last item to be popped
        if (elements.size <= 1) false else {
            elements = elements.dropLast(1)
            true
        }

    fun replace(element: T) {
        elements = elements.dropLast(1) + element
    }

    fun newRoot(element: T) {
        elements = listOf(element)
    }
}
