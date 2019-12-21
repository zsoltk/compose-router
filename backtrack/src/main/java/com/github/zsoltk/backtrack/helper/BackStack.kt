package com.github.zsoltk.backtrack.helper

import androidx.compose.Model

@Model
class BackStack<T>(
    defaultElement: T
) {
    private var elements: List<Entry<T>> = listOf(Entry(defaultElement))

    class Entry<T>(val element: T) {
        val children: MutableMap<Any, BackStack<*>> = mutableMapOf()
    }

    val size: Int
        get() = elements.size

    internal fun lastEntry(): Entry<T> =
        elements.last()

    fun last(): T =
        elements.last().element

    fun push(element: T) {
        elements = elements + Entry(element)
    }

    fun pushAndDropNested(element: T) {
        elements.last().children.clear()
        push(element)
    }

    fun pop(): Boolean =
        // we won’t let the last item to be popped
        if (elements.size <= 1) false else {
            elements = elements.dropLast(1)
            true
        }

    fun replace(element: T) {
        elements = elements.dropLast(1) + Entry(element)
    }

    fun newRoot(element: T) {
        elements = listOf(Entry(element))
    }
}
