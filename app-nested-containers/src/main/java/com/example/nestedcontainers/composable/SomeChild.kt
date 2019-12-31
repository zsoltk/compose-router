package com.example.nestedcontainers.composable

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.stateFor
import androidx.compose.unaryPlus
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
import com.example.nestedcontainers.composable.SomeChild.Routing.SubtreeA
import com.example.nestedcontainers.composable.SomeChild.Routing.SubtreeB
import com.example.nestedcontainers.composable.SomeChild.Routing.SubtreeC
import com.github.zsoltk.backtrack.composable.BackHandler
import com.github.zsoltk.backtrack.composable.savedInstanceState
import java.io.Serializable

interface SomeChild {
    /**
     * Describes which subtree we compose locally. Imagine these are more meaningful names :)
     */
    sealed class Routing : Serializable {
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
                R.color.red_600,
                R.color.red_700,
                R.color.red_800
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
            level: Int,
            id: String,
            bgColor: Int,
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
            bgColor: Int,
            defaultRouting: Routing
        ) {
            BackHandler("$level.$id", defaultRouting) { backStack ->
                val nbChildren = nbChildrenPerLevel.getOrDefault(level, 1)

                Container(
                    name = if (level == 0) "Root" else "L$level.$id",
                    size =  backStack.size,
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
            bgColor: Int,
            onButtonClick: () -> Unit,
            children: @Composable() () -> Unit
        ) {
            val bundle = +ambient(savedInstanceState)
            var counter by +stateFor(this) {
                bundle.getInt("counter", 0)
            }

            Surface(color = +colorResource(bgColor)) {
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
            when (currentRouting) {
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
                        level + 1,
                        "A$ord",
                        colorSets[currentRouting]!![level],
                        currentRouting
                    )
                }
                SubtreeB -> Content(
                    level + 1,
                    "B$ord",
                    colorSets[currentRouting]!![level],
                    currentRouting
                )
                SubtreeC -> Content(
                    level + 1,
                    "C$ord",
                    colorSets[currentRouting]!![level],
                    currentRouting
                )
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
