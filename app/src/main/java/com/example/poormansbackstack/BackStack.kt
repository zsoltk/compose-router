package com.example.poormansbackstack

data class BackStack<T>(
    private val defaultElement: T,
    private val elements: List<T> = listOf(defaultElement)
) {
    fun last(): T =
        elements.last()

    fun push(element: T) = copy(
        elements = elements + element
    )

    fun pop(): BackStack<T> =
        // we won’t let the last item to be popped
        if (elements.size <= 1) this else copy(
            elements = elements.dropLast(1)
        )
}
