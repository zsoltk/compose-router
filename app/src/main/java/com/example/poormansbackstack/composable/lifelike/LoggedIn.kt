package com.example.poormansbackstack.composable.lifelike

import androidx.compose.Composable
import androidx.ui.layout.FlexColumn
import com.example.poormansbackstack.backpress.composable.BackHandler
import com.example.poormansbackstack.entity.User

interface LoggedIn {
    sealed class Routing {
        object Hello : Routing()
        object LoremIpsum : Routing()
        object Profile : Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing, user: User, onLogout: () -> Unit) {
            BackHandler(defaultRouting) { backStack ->
                val routing = backStack.last()

                FlexColumn {
                    expanded(1f) {
                        routing.toContent(user, onLogout)
                    }

                    inflexible {
                        Menu.Content(
                            state = routing.toMenuState(),
                            onMenuItemClicked = { menuItem ->
                                backStack.push(menuItem.toRouting())
                            }
                        )
                    }
                }
            }
        }

        /**
         * Renders Content of another Composable module based on the selected Routing
         */
        @Composable
        private fun Routing.toContent(user: User, onLogout: () -> Unit) = when (this) {
            is Routing.Hello -> Hello.Content()
            is Routing.LoremIpsum -> LoremIpsum.Content()
            is Routing.Profile -> Profile.Content(user, onLogout)
        }

        /**
         * Creates Menu state with menu item selected based on current routing choice
         */
        private fun Routing.toMenuState() =
            Menu.State(
                currentSelection = when (this) {
                    is Routing.Hello -> Menu.MenuItem.Hello
                    is Routing.LoremIpsum -> Menu.MenuItem.LoremIpsum
                    is Routing.Profile -> Menu.MenuItem.Profile
                }
            )

        /**
         * Maps the MenuItem to the corresponding Routing choice
         */
        private fun Menu.MenuItem.toRouting(): Routing = when (this) {
            is Menu.MenuItem.Hello -> Routing.Hello
            is Menu.MenuItem.LoremIpsum -> Routing.LoremIpsum
            is Menu.MenuItem.Profile -> Routing.Profile
        }
    }
}
