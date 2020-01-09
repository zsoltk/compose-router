package com.example.nestedcontainers

import androidx.animation.AnimationEndReason.TargetReached
import androidx.animation.FloatPropKey
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.Key
import androidx.compose.effectOf
import androidx.compose.memo
import androidx.compose.onCommit
import androidx.compose.unaryPlus
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Draw
import androidx.ui.core.Layout
import androidx.ui.core.ipx
import androidx.ui.core.px
import androidx.ui.engine.geometry.Rect
import com.example.nestedcontainers.T.Finish
import com.example.nestedcontainers.T.Start

@Composable
fun <T> Translate(current: T, children: @Composable() (T) -> Unit) {
    val animState = +memo { AnimationState<T>() }
    if (animState.current != current) {
        animState.current = current
        val keys = animState.items.map { it.key }.toMutableSet() + current
        animState.items.clear()
        keys.mapTo(animState.items) { key ->
            AnimationItem(key) { children -> children() }
        }
    }

    val composable = @Composable() {
        LayoutItems {
            animState.items.forEach {
                it.animApplication { children(it.key) }
            }
        }
    }

    Key(key = current) {
        val floatValue = +animated(init = 0f, target = 1f, snap = animState.items.size == 1) {
            if (animState.current == current) {
                animState.items.removeAll { it.key != animState.current }
            }
        }
        Draw(children = composable) { canvas, parentSize ->
            canvas.save()
            val itemSize = (parentSize.width / animState.items.size).value
            val startX = itemSize * (animState.items.size - 1)
            val endX = itemSize * animState.items.size
            println("$startX $endX $floatValue ${(endX - startX) * floatValue}")
            canvas.clipRect(Rect(0f, 0f, itemSize, parentSize.height.value))
            canvas.translate(-startX + (endX - startX) * (1 - floatValue), 0f)
            drawChildren()
            canvas.restore()
        }
    }
}

@Composable
private fun LayoutItems(children: @Composable() () -> Unit) {
    Layout(children = children) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val width = placeables.sumBy { it.width.value }
        val height = placeables.firstOrNull()?.height ?: 0.ipx
        layout(width.ipx, height) {
            var width = 0
            placeables.forEach {
                it.place(width.px, 0.px)
                width += it.width.value
            }
        }
    }
}

class TranslateParams(
    val startX: Float = 0f,
    val endX: Float = 0f,
    val startY: Float = 0f,
    val endY: Float = 0f
)

private class AnimationState<T> {
    var current: T? = null
    val items = mutableListOf<AnimationItem<T>>()
}

private class AnimationItem<T>(
    val key: T,
    val animApplication: @Composable() (@Composable() () -> Unit) -> Unit
)

private val X = FloatPropKey()
private val Y = FloatPropKey()

private enum class T {
    Start, Finish
}

private fun transitionDef(params: TranslateParams) = transitionDefinition {
    state(Start) {
        this[X] = params.startX
        this[Y] = params.startY
    }

    state(Finish) {
        this[X] = params.endX
        this[Y] = params.endY
    }

    transition {
        X using tween { duration = 300 }
    }
}

private fun animated(init: Float, target: Float, snap: Boolean, onFinish: () -> Unit) = effectOf<Float> {
    if (snap) return@effectOf target

    val float = +animatedFloat(init)

    +onCommit {
        float.animateTo(target) { reason, _ ->
            if (reason == TargetReached) {
                onFinish()
            }
        }
//        onDispose {
//            float.stop()
//        }
    }

    float.value
}
