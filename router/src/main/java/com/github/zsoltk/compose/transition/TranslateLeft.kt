package com.github.zsoltk.compose.transition

import androidx.animation.FloatPropKey
import androidx.animation.TransitionDefinition
import androidx.animation.TransitionState
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.Key
import androidx.compose.memo
import androidx.compose.unaryPlus
import androidx.ui.core.Draw
import androidx.ui.core.Layout
import androidx.ui.core.px
import com.github.zsoltk.compose.transition.AnimationParams.Opacity
import com.github.zsoltk.compose.transition.AnimationParams.X
import com.github.zsoltk.compose.transition.AnimationParams.Y
import com.github.zsoltk.compose.transition.TransitionStates.Finish
import com.github.zsoltk.compose.transition.TransitionStates.Start
import kotlin.math.roundToInt

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
//        println("Keys: ${keys.map { it.javaClass.simpleName }}")
        animState.items.clear()
        keys.mapTo(animState.items) { key ->
            AnimationItem(key) { children ->
                Transition(
                    definition = transitionDefinition.exitTransition,
                    initState = Start,
                    toState = Finish,
                    onStateChangeFinished = {
                        if (it == Finish && animState.current == current) {
//                            println("Cleanup for ${current.javaClass.simpleName}")
                            animState.items.removeAll { it.key != current }
                        }
                    }
                ) {
//                    println("Render exit children for ${key.javaClass.simpleName} ${animState.items.size} x = ${it[X]} y = ${it[Y]} opacity = ${it[Opacity]}")
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
                //                println("Render enter children for ${current.javaClass.simpleName} ${animState.items.size} x = ${it[X]} y = ${it[Y]} opacity = ${it[Opacity]}")
                children(it)
            }
        }
    }

    val layoutContent = @Composable() {
        animState.items.forEach { item ->
            Key(item.key) {
                item.transition { transitionState ->
                    val composable = @Composable() {
                        children(item.key)
                    }
                    val xFraction = transitionState[X]
                    val yFraction = transitionState[Y]
                    val opacityFraction = transitionState[Opacity]
                    Draw(children = composable) { canvas, parentSize ->
                        canvas.nativeCanvas.saveLayerAlpha(
                            0f,
                            0f,
                            parentSize.width.value,
                            parentSize.height.value,
                            (opacityFraction * 255f).roundToInt()
                        )
                        val startX = parentSize.width.value
                        val startY = parentSize.height.value
                        canvas.translate(startX * xFraction, startY * yFraction)
                        drawChildren()
                        canvas.restore()
                    }
                }
            }
        }
    }

    Layout(children = layoutContent) { measurables, constraints ->
        val placeables = measurables.map { it.measure(constraints) }
        val width = placeables.maxBy { it.width }!!.width
        val height = placeables.maxBy { it.height }!!.height
        layout(width, height) {
            placeables.forEach {
                it.place(0.px, 0.px)
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
        this[Opacity] = 1f
    }

    state(Finish) {
        this[X] = 0f
        this[Y] = 0f
        this[Opacity] = 1f
    }

    transition {
        X using tween { duration = 300 }
    }
}

private val exitAnim = transitionDefinition {
    state(Start) {
        this[X] = 0f
        this[Y] = 0f
        this[Opacity] = 1f
    }

    state(Finish) {
        this[X] = -1f
        this[Y] = 0f
        this[Opacity] = 1f
    }

    transition {
        X using tween { duration = 300 }
    }
}

private class TransitionDef(
    val enterTransition: TransitionDefinition<TransitionStates>,
    val exitTransition: TransitionDefinition<TransitionStates>
)
