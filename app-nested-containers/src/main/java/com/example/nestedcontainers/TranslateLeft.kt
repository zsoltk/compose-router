package com.example.nestedcontainers

import androidx.animation.AnimationEndReason.TargetReached
import androidx.animation.FloatPropKey
import androidx.animation.TransitionDefinition
import androidx.animation.TransitionState
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.Key
import androidx.compose.effectOf
import androidx.compose.memo
import androidx.compose.onCommit
import androidx.compose.unaryPlus
import androidx.ui.animation.animatedFloat
import androidx.ui.core.Alignment.TopLeft
import androidx.ui.core.Draw
import androidx.ui.engine.geometry.Rect
import androidx.ui.layout.Stack
import com.example.nestedcontainers.TransitionStates.Finish
import com.example.nestedcontainers.TransitionStates.Start

@Composable
fun <T : Any> Tranlate(current: T, children: @Composable() (T) -> Unit) {
    val animState = +memo { AnimationState<T>() }
    val transitionDefinition = +memo {
        TransitionDef(
            enterTransition = transitionDef(TranslateParams(startX = 1f, endX = 0f)),
            exitTransition = transitionDef(TranslateParams(startX = 0f, endX = -1f))
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
                    toState = Finish
                ) {
//                    println("Render exit children for ${key.javaClass.simpleName} ${animState.items.size} ${it[X]}")
                    children(it)
                }
            }
        }

        animState.items += AnimationItem(current) { children ->
            Transition(
                definition = transitionDefinition.enterTransition,
                initState = if (animState.items.size == 1) Finish else Start,
                toState = Finish,
                onStateChangeFinished = {
                    if (it == Finish && animState.current == current) {
//                        println("Cleanup for ${current.javaClass.simpleName}")
                        animState.items.removeAll { it.key != animState.current }
                    }
                }
            ) {
//                println("Render enter children for ${current.javaClass.simpleName} ${animState.items.size} ${it[X]}")
                children(it)
            }
        }
    }

    Stack {
        aligned(TopLeft) {
            animState.items.forEach { item ->
                Key(item.key) {
                    item.transition {
                        val composable = @Composable() {
                            children(item.key)
                        }
                        val floatValue = it[X]
                        Draw(children = composable) { canvas, parentSize ->
                            canvas.save()
                            val startX = parentSize.width.value
                            val endX = 0f
//            println("$startX $endX $floatValue ${(endX - startX) * floatValue}")
                            canvas.clipRect(Rect(0f, 0f, parentSize.width.value, parentSize.height.value))
                            canvas.translate(startX * floatValue, 0f)
                            drawChildren()
                            canvas.restore()
                        }
                    }
                }
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
    val transition: @Composable() (@Composable() (TransitionState) -> Unit) -> Unit
)

private val X = FloatPropKey()
private val Y = FloatPropKey()

private enum class TransitionStates {
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

private class TransitionDef(
    val enterTransition: TransitionDefinition<TransitionStates>,
    val exitTransition: TransitionDefinition<TransitionStates>
)
