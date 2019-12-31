package com.github.zsoltk.compose.router.serializable

import androidx.compose.Model
import java.io.Serializable

@Model
class BackStack<T : Serializable> internal constructor(
    internal var elements: ArrayList<T>, // ArrayList for Serializable temporarily
    private var onElementRemoved: ((Int) -> Unit)
) {
    val lastIndex: Int
        get() = elements.lastIndex

    val size: Int
        get() = elements.size

    fun last(): T =
        elements.last()

    fun push(element: T) {
        elements = ArrayList(elements.plus(element))
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
        elements = ArrayList(
            elements
                .subList(0, elements.lastIndex - 1)
                .plus(element)
        )
    }

    fun newRoot(element: T) {
        elements.indices.reversed().forEach { index ->
            onElementRemoved.invoke(index)
        }
        elements = arrayListOf(element)
    }
}
