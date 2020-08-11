package com.github.zsoltk.compose.router

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class BackStack<T> internal constructor(
    initialElement: T,
    private var onElementRemoved: ((Int) -> Unit)
) {
    var elements by mutableStateOf(listOf(initialElement))
        private set

    val lastIndex: Int
        get() = elements.lastIndex

    val size: Int
        get() = elements.size

    fun last(): T =
        elements.last()

    fun push(element: T) {
        elements = elements.plus(element)
    }

    fun pushAndDropNested(element: T) {
        onElementRemoved.invoke(lastIndex)
        push(element)
    }

    fun pop(): Boolean =
        // we won’t let the last item to be popped
        if (size <= 1) false else {
            onElementRemoved.invoke(lastIndex)
            elements = ArrayList(
                elements.subList(0, lastIndex) // exclusive
            )
            true
        }

    fun replace(element: T) {
        onElementRemoved.invoke(lastIndex)
        elements = elements
            .subList(0, elements.lastIndex) // exclusive
            .plus(element)
    }

    fun newRoot(element: T) {
        elements.indices.reversed().forEach { index ->
            onElementRemoved.invoke(index)
        }
        elements = arrayListOf(element)
    }
}
