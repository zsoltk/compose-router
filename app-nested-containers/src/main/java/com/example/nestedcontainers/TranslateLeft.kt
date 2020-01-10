package com.example.nestedcontainers

import androidx.animation.FloatPropKey
import androidx.animation.Spring.Companion.DampingRatioHighBouncy
import androidx.animation.TransitionDefinition
import androidx.animation.TransitionState
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.Key
import androidx.compose.memo
import androidx.compose.unaryPlus
import androidx.ui.core.Alignment.TopLeft
import androidx.ui.core.Draw
import androidx.ui.core.Opacity
import androidx.ui.layout.Stack
import com.example.nestedcontainers.AnimationParams.Opacity
import com.example.nestedcontainers.AnimationParams.X
import com.example.nestedcontainers.AnimationParams.Y
import com.example.nestedcontainers.TransitionStates.Finish
import com.example.nestedcontainers.TransitionStates.Start

@Composable
fun <T : Any> Tranlate(current: T, children: @Composable() (T) -> Unit) {
    val animState = +memo { AnimationState<T>() }
    val transitionDefinition = +memo {
        TransitionDef(
            enterTransition = enterAnim,
            exitTransition = exitAnim
        )
    }
    if (animState.current != current) {
        animState.current = current
        val keys = animState.items.map { it.key }.toMutableSet()
        animState.items.clear()
        keys.mapTo(animState.items) { key ->
            AnimationItem(key) { children ->
                Transition(
                    definition = transitionDefinition.exitTransition,
                    initState = Start,
                    toState = Finish,
                    onStateChangeFinished = {
                        if (it == Finish && animState.current == current) {
                            //println("Cleanup for ${current.javaClass.simpleName}")
                            animState.items.removeAll { it.key != animState.current }
                        }
                    }
                ) {
                    //println("Render exit children for ${key.javaClass.simpleName} ${animState.items.size} ${it[X]}")
                    children(it)
                }
            }
        }

        animState.items += AnimationItem(current) { children ->
            Transition(
                definition = transitionDefinition.enterTransition,
                initState = if (animState.items.size == 1) Finish else Start,
                toState = Finish
            ) {
                //println("Render enter children for ${current.javaClass.simpleName} ${animState.items.size} ${it[X]}")
                children(it)
            }
        }
    }

    Stack {
        aligned(TopLeft) {
            animState.items.forEach { item ->
                Key(item.key) {
                    item.transition { transitionState ->
                        val composable = @Composable() {
                            children(item.key)
                        }
                        val xFraction = transitionState[X]
                        val yFraction = transitionState[Y]
                        val opacityFraction = transitionState[Opacity]
                        Opacity(opacity = opacityFraction) {
                            Draw(children = composable) { canvas, parentSize ->
                                canvas.save()
                                val startX = parentSize.width.value
                                val startY = parentSize.width.value
//            println("$startX $endX $floatValue ${(endX - startX) * floatValue}")
                                canvas.nativeCanvas.clipRect(0f, 0f, parentSize.width.value, parentSize.height.value)
                                canvas.translate(startX * xFraction, startY * yFraction)
                                drawChildren()
                                canvas.restore()
                            }
                        }
                    }
                }
            }
        }

    }
}

private class AnimationState<T> {
    var current: T? = null
    val items = mutableListOf<AnimationItem<T>>()
}

private class AnimationItem<T>(
    val key: T,
    val transition: @Composable() (@Composable() (TransitionState) -> Unit) -> Unit
)

object AnimationParams {
    val X = FloatPropKey()
    val Y = FloatPropKey()
    val Opacity = FloatPropKey()
}

private enum class TransitionStates {
    Start, Finish
}

private val enterAnim = transitionDefinition {
    state(Start) {
        this[X] = 1f
        this[Y] = 0f
        this[Opacity] = 0f
    }

    state(Finish) {
        this[X] = 0f
        this[Y] = 0f
        this[Opacity] = 1f
    }

    transition {
        X using physics { dampingRatio = DampingRatioHighBouncy }
        X using tween { duration = 1000 }
        Opacity using tween { duration = 1000 }
    }
}

private val exitAnim = transitionDefinition {
    state(Start) {
        this[X] = 0f
        this[Y] = 0f
        this[Opacity] = 1f
    }

    state(Finish) {
        this[X] = 0f
        this[Y] = 1f
        this[Opacity] = 0f
    }

    transition {
        Y using tween { duration = 1000 }
        Opacity using tween { duration = 1000 }
    }
}

private class TransitionDef(
    val enterTransition: TransitionDefinition<TransitionStates>,
    val exitTransition: TransitionDefinition<TransitionStates>
)
