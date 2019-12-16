package com.example.poormansbackstack

import androidx.compose.Composable
import androidx.compose.memo
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.HeightSpacer
import androidx.ui.layout.Spacing
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.res.colorResource
import com.example.poormansbackstack.SomeChild.Routing.SubtreeA
import com.example.poormansbackstack.SomeChild.Routing.SubtreeB
import com.example.poormansbackstack.SomeChild.Routing.SubtreeC

interface SomeChild {
    /**
     * Describes which subtree we compose locally. Imagine these are more meaningful names :)
     */
    sealed class Routing {
        object SubtreeA : Routing()
        object SubtreeB : Routing()
        object SubtreeC : Routing()
    }

    companion object {
        private const val MAX_NESTING_LEVEL = 5

        private val colorSets = mapOf(
            SubtreeA to listOf(
                R.color.red_200,
                R.color.red_300,
                R.color.red_400,
                R.color.red_500,
                R.color.red_600,
                R.color.red_700
            ),
            SubtreeB to listOf(
                R.color.green_200,
                R.color.green_300,
                R.color.green_400,
                R.color.green_500,
                R.color.green_600,
                R.color.green_700
            ),
            SubtreeC to listOf(
                R.color.blue_200,
                R.color.blue_300,
                R.color.blue_400,
                R.color.blue_500,
                R.color.blue_600,
                R.color.blue_700
            )
        )

        @Composable
        fun Root(backPress: BackPress, cantPopBackStack: () -> Unit) {
            Content(
                backPress = backPress,
                cantPopBackStack = cantPopBackStack,
                level = 0,
                id = "Root",
                bgColor = R.color.blue_grey_200,
                defaultRouting = SubtreeA
            )
        }

        /**
         * In order to simplify things for a demo that should show many levels of nested
         * Composable blocks each with their own back stack, now we'll only use this one
         * with parameters to reflect the identity of the current child.
         *
         * Feel free to imagine that all of these Content() Composables are actually different
         * implementations of various functionality of a real app. Names used in the [Routing]
         * sealed class would also reflect more meaningful names of actual app functionality.
         */
        @Composable
        private fun Content(
            backPress: BackPress,
            cantPopBackStack: () -> Unit,
            level: Int,
            id: String,
            bgColor: Int,
            defaultRouting: Routing
        ) {
            if (level < MAX_NESTING_LEVEL) {
                if (level + 1 == MAX_NESTING_LEVEL) {
                    NestedContainerWithRouting(backPress, cantPopBackStack, level, id, bgColor, defaultRouting)
                }
                NestedContainerWithRouting(backPress, cantPopBackStack, level, id, bgColor, defaultRouting)
            } else {
                Leaf(backPress, cantPopBackStack, level, id)
            }
        }

        private fun Leaf(
            backPress: BackPress,
            cantPopBackStack: () -> Unit,
            level: Int,
            id: String
        ) {
            if (backPress.triggered) {
                cantPopBackStack()
            }

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Leaf $level.$id",
                    style = (+MaterialTheme.typography()).body1,
                    modifier = Spacing(16.dp)
                )
            }
        }

        class State<T>(var value: T)

        @Composable
        private fun NestedContainerWithRouting(
            backPress: BackPress,
            cantPopBackStack: () -> Unit,
            level: Int,
            id: String,
            bgColor: Int,
            defaultRouting: Routing
        ) {
            var backStack by +state { BackStack(defaultRouting) }
            val nbChildren = if (level + 2 == MAX_NESTING_LEVEL) 2 else 1
            val unhandledCount = +memo { State(0) }

            val cantPopBackStackDelegate = {
                if (++unhandledCount.value == nbChildren) {
                    val newBackStack =  backStack.pop()
                    if (newBackStack.size < backStack.size) {
                        backStack = newBackStack
                        BackPress.triggered = false
                    } else {
                        cantPopBackStack()
                    }
                    unhandledCount.value = 0
                }
            }

            Container(
                name = if (level == 0) "Root" else "L$level.$id",
                bgColor = bgColor,
                onButtonClick = { backStack = backStack.push(backStack.last().next()) }
            ) {
                when (val currentRouting = backStack.last()) {
                    /**
                     * Now these branches below are almost the same, so you could of course
                     * get rid of the when expression and parameterize the whole thing (only
                     * the id labels "A" are different).
                     *
                     * But I thought that would remove the demonstration value imagining that in
                     * real life circumstances these branches would have more meaningful names
                     * ("Profile", "Settings", "Chat", "Gallery", etc.) with different
                     * Composables on the right side.
                     */
                    SubtreeA -> {
                        Content(
                            backPress,
                            cantPopBackStackDelegate,
                            level + 1,
                            "A",
                            colorSets[currentRouting]!![level],
                            currentRouting
                        )
                    }
                    SubtreeB -> Content(
                        backPress,
                        cantPopBackStackDelegate,
                        level + 1,
                        "B",
                        colorSets[currentRouting]!![level],
                        currentRouting
                    )
                    SubtreeC -> Content(
                        backPress,
                        cantPopBackStackDelegate,
                        level + 1,
                        "C",
                        colorSets[currentRouting]!![level],
                        currentRouting
                    )
                }
            }
        }

        @Composable
        private fun Container(
            name: String,
            bgColor: Int,
            onButtonClick: () -> Unit,
            children: @Composable() () -> Unit
        ) {
            Surface(color = +colorResource(bgColor)) {
                Column(modifier = Spacing(16.dp)) {
                    Button(text = "$name.NEXT", onClick = onButtonClick)
                    HeightSpacer(height = 16.dp)
                    children()
                }
            }
        }

        private fun Routing.next(): Routing =
            when (this) {
                SubtreeA -> SubtreeB
                SubtreeB -> SubtreeC
                SubtreeC -> SubtreeA
            }
    }
}
