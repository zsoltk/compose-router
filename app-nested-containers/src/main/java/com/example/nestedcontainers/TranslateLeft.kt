package com.example.nestedcontainers

import androidx.animation.AnimationEndReason
import androidx.animation.TweenBuilder
import androidx.compose.Composable
import androidx.compose.Key
import androidx.compose.effectOf
import androidx.compose.memo
import androidx.compose.onCommit
import androidx.compose.unaryPlus
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Alignment
import androidx.ui.core.Clip
import androidx.ui.core.IntPx
import androidx.ui.core.Layout
import androidx.ui.core.px
import androidx.ui.foundation.shape.RectangleShape
import androidx.ui.layout.Stack

@Composable
fun <T> TranslateLeft(current: T, children: @Composable() (T) -> Unit) {
    val animState = +memo { CrossfadeState<T>() }
    if (current != animState.current) {
        animState.current = current
        val keys = animState.items.toMutableList()
        if (!keys.contains(current)) {
            keys.add(current)
        }
        animState.items.clear()
        keys.forEach { key ->
            animState.items.add(key)
        }
    }
    Clip(RectangleShape) {
        Stack {
            aligned(Alignment.TopLeft) {
                animState.items.forEach { key ->
                    Key(key = key) {
                        val animatedFloat = +animatedOpacity(
                            visible = (key == current),
                            onAnimationFinish = {
                                if (key == animState.current) {
                                    // leave only the current in the list
                                    animState.items.removeAll { it != animState.current }
                                }
                            })
                        Layout(children = @Composable { children(key) }) { measurables, constraints ->
                            val placeables = measurables.map { it.measure(constraints) }
                            if (placeables.isEmpty()) {
                                layout(IntPx.Zero, IntPx.Zero) { }
                            } else {
                                val item = placeables.first()
                                layout(item.width, item.height) {
                                    item.place((animatedFloat * item.width.value).px, 0.px)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private class CrossfadeState<T> {
    var current: T? = null
    val items = mutableListOf<T>()
}

private fun animatedOpacity(
    visible: Boolean,
    onAnimationFinish: () -> Unit = {}
) = effectOf<Float> {
    val animatedFloat = +animatedFloat(if (visible) 1f else 0f)
    +onCommit(visible) {
        animatedFloat.animateTo(
            if (visible) 0f else -1f,
            anim = TweenBuilder<Float>().apply { duration = 300 },
            onEnd = { reason, _ ->
                if (reason == AnimationEndReason.TargetReached) {
                    onAnimationFinish()
                }
            })
    }
    animatedFloat.value
}
