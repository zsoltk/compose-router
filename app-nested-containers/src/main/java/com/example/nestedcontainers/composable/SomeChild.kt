package com.example.nestedcontainers.composable

import androidx.animation.Spring.Companion.DampingRatioHighBouncy
import androidx.animation.transitionDefinition
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.stateFor
import androidx.compose.unaryPlus
import androidx.ui.animation.ColorPropKey
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Row
import androidx.ui.layout.Spacing
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButtonStyle
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Surface
import androidx.ui.res.colorResource
import com.example.nestedcontainers.R
import com.example.nestedcontainers.TranslateLeft
import com.example.nestedcontainers.composable.SomeChild.Routing.SubtreeBlue
import com.example.nestedcontainers.composable.SomeChild.Routing.SubtreeGreen
import com.example.nestedcontainers.composable.SomeChild.Routing.SubtreeRed
import com.github.zsoltk.compose.router.Router
import com.github.zsoltk.compose.savedinstancestate.savedInstanceState

interface SomeChild {
    /**
     * Describes which subtree we compose locally. Imagine these are more meaningful names :)
     */
    sealed class Routing {
        object SubtreeRed : Routing()
        object SubtreeGreen : Routing()
        object SubtreeBlue : Routing()
    }

    companion object {
        private const val MAX_NESTING_LEVEL = 5

        val colorPropKey = ColorPropKey()
        val defs by lazy {
            (0 .. MAX_NESTING_LEVEL).associateWith { level ->
                transitionDefinition {
                    state(SubtreeRed) {
                        this[colorPropKey] = +colorResource(colorSets[Color.Red]!![level])
                    }
                    state(SubtreeBlue) {
                        this[colorPropKey] = +colorResource(colorSets[Color.Blue]!![level])
                    }
                    state(SubtreeGreen) {
                        this[colorPropKey] = +colorResource(colorSets[Color.Green]!![level])
                    }

                    transition {
                        colorPropKey using tween {
                            duration = 2000
                        }
                    }

                    transition(fromState = SubtreeRed, toState = SubtreeGreen) {
                        colorPropKey using physics {
                            dampingRatio = DampingRatioHighBouncy
                        }
                    }
                }
            }
        }

        private val colorSets = mapOf(
            Color.Red to listOf(
                R.color.red_200,
                R.color.red_300,
                R.color.red_400,
                R.color.red_600,
                R.color.red_700,
                R.color.red_800
            ),
            Color.Green to listOf(
                R.color.green_200,
                R.color.green_300,
                R.color.green_400,
                R.color.green_500,
                R.color.green_600,
                R.color.green_700
            ),
            Color.Blue to listOf(
                R.color.blue_200,
                R.color.blue_300,
                R.color.blue_400,
                R.color.blue_500,
                R.color.blue_600,
                R.color.blue_700
            )
        )

        // Careful with that axe, Eugene
        private val nbChildrenPerLevel = mapOf(
            0 to 1,
            1 to 1,
            2 to 1,
            3 to 2,
            4 to 1
        )

        @Composable
        fun Root() {
            Content(
                level = 0,
                id = "Root",
                defaultRouting = SubtreeRed,
                bgColor = Color.White
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
            level: Int,
            id: String,
            bgColor: Color,
            defaultRouting: Routing
        ) {
            if (level >= MAX_NESTING_LEVEL) {
                Leaf(
                    level,
                    id
                )
            } else {
                NestedContainerWithRouting(
                    level,
                    id,
                    bgColor,
                    defaultRouting
                )
            }
        }

        @Composable
        private fun Leaf(
            level: Int,
            id: String
        ) {
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

        @Composable
        private fun NestedContainerWithRouting(
            level: Int,
            id: String,
            bgColor: Color,
            defaultRouting: Routing
        ) {
            Router("$level.$id", defaultRouting) { backStack ->
                val nbChildren = nbChildrenPerLevel.getOrDefault(level, 1)
                Container(
                    name = if (level == 0) "Root" else "L$level.$id",
                    size = backStack.size,
                    bgColor = bgColor,
                    onButtonClick = { backStack.push(backStack.last().next()) }
                ) {
                    Row {
                        for (i in 1..nbChildren) {
                            Child(
                                i,
                                backStack.last(),
                                level
                            )
                        }
                    }
                }
            }
        }

        /**
         * Adds a button and some children
         */
        @Composable
        private fun Container(
            name: String,
            size: Int,
            bgColor: Color,
            onButtonClick: () -> Unit,
            children: @Composable() () -> Unit
        ) {
            val bundle = +ambient(savedInstanceState)
            var counter by +stateFor(this) {
                bundle.getInt("counter", 0)
            }

            Surface(color = bgColor) {
                Column(modifier = Spacing(16.dp)) {
                    Ripple(bounded = true) {
                        Button(text = "$name.NEXT", onClick = onButtonClick)
                    }
                    Text("Back stack: $size")
                    Ripple(bounded = true) {
                        Button(
                            text = "Local data: $counter",
                            style = TextButtonStyle(contentColor = Color.White),
                            onClick = {
                                bundle.putInt("counter", ++counter)
                            })
                    }
                    children()
                }
            }
        }

        /**
         * Adds child based on current routing
         */
        @Composable
        private fun Child(
            ord: Int,
            currentRouting: Routing,
            level: Int
        ) {
            TranslateLeft(current = currentRouting) {
                when (it) {
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
                    is SubtreeRed -> {
                        Content(
                            level + 1,
                            "R$ord",
                            +colorResource(colorSets[Color.Red]!![level + 1]),
                            currentRouting
                        )
                    }
                    is SubtreeGreen -> {
                        Content(
                            level + 1,
                            "G$ord",
                            +colorResource(colorSets[Color.Green]!![level + 1]),
                            currentRouting
                        )
                    }
                    is SubtreeBlue -> {
                        Content(
                            level + 1,
                            "B$ord",
                            +colorResource(colorSets[Color.Blue]!![level + 1]),
                            currentRouting
                        )
                    }
                }

            }
        }

        private fun Routing.next(): Routing =
            when (this) {
                is SubtreeRed -> SubtreeGreen
                is SubtreeGreen -> SubtreeBlue
                is SubtreeBlue -> SubtreeRed
            }
    }
}
