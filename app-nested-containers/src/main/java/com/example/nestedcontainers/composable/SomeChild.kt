package com.example.nestedcontainers.composable

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.material.ripple.ripple
import androidx.ui.res.colorResource
import androidx.ui.unit.dp
import com.example.nestedcontainers.R
import com.example.nestedcontainers.composable.SomeChild.Routing.*
import com.github.zsoltk.compose.router.Router
import com.github.zsoltk.compose.savedinstancestate.persistentInt

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
                bgColor = R.color.blue_grey_200,
                defaultRouting = SubtreeRed
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
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp)
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
            bgColor: Int,
            onButtonClick: () -> Unit,
            children: @Composable() () -> Unit
        ) {
            val counter = persistentInt("counter")
            val textStyle = MaterialTheme.typography.body1

            Surface(color = colorResource(bgColor)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Box(modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)) {
                        Button(onClick = onButtonClick, modifier = Modifier.ripple(true)) {
                            Text("$name.NEXT", style = textStyle.copy(color = Color.White))
                        }
                    }
                    Box(modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)) {
                        Text("Back stack: $size", style = textStyle)
                    }
                    Box(modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)) {
                        Clickable(onClick = { counter.value++ }, modifier = Modifier.ripple(true)) {
                            Text("Local data: ${counter.value}", style = textStyle)
                        }
                    }
                    Box(modifier = Modifier.wrapContentHeight(Alignment.CenterVertically)) {
                        children()
                    }
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
                is SubtreeRed -> {
                    Content(
                        level + 1,
                        "R$ord",
                        colorSets[Color.Red]!![level],
                        currentRouting
                    )
                }
                is SubtreeGreen -> Content(
                    level + 1,
                    "G$ord",
                    colorSets[Color.Green]!![level],
                    currentRouting
                )
                is SubtreeBlue -> Content(
                    level + 1,
                    "B$ord",
                    colorSets[Color.Blue]!![level],
                    currentRouting
                )
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
