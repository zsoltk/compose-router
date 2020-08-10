package com.example.lifelike.composable

import androidx.compose.foundation.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.lifelike.composable.loggedin.Gallery
import com.example.lifelike.composable.loggedin.Gallery.Routing.AlbumList
import com.example.lifelike.composable.loggedin.Menu
import com.example.lifelike.composable.loggedin.News
import com.example.lifelike.composable.loggedin.Profile
import com.example.lifelike.entity.User
import com.github.zsoltk.compose.router.Router

interface LoggedIn {
    sealed class Routing {
        object Gallery : Routing()
        object News : Routing()
        object Profile : Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing, user: User, onLogout: () -> Unit) {
            Router(defaultRouting) { backStack ->
                val routing = backStack.last()

                Column {
                    Box(modifier = Modifier.weight(1f).wrapContentSize(Alignment.TopStart)) {
                        routing.toContent(user, onLogout)
                    }

                    Menu.Content(
                        state = routing.toMenuState(),
                        onMenuItemClicked = { menuItem ->
                            backStack.push(menuItem.toRouting())
                        }
                    )
                }
            }
        }

        /**
         * Renders Content of another Composable module based on the selected Routing
         */
        @Composable
        private fun Routing.toContent(user: User, onLogout: () -> Unit) = when (this) {
            is Routing.Gallery -> Gallery.Content(AlbumList)
            is Routing.News -> News.Content()
            is Routing.Profile -> Profile.Content(
                user,
                onLogout
            )
        }

        /**
         * Creates Menu state with menu item selected based on current routing choice
         */
        private fun Routing.toMenuState() =
            Menu.State(
                currentSelection = when (this) {
                    is Routing.Gallery -> Menu.MenuItem.Gallery
                    is Routing.News -> Menu.MenuItem.News
                    is Routing.Profile -> Menu.MenuItem.Profile
                }
            )

        /**
         * Maps the MenuItem to the corresponding Routing choice
         */
        private fun Menu.MenuItem.toRouting(): Routing = when (this) {
            is Menu.MenuItem.Gallery -> Routing.Gallery
            is Menu.MenuItem.News -> Routing.News
            is Menu.MenuItem.Profile -> Routing.Profile
        }
    }
}
