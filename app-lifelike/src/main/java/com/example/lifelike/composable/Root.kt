package com.example.lifelike.composable

import androidx.compose.Composable
import com.example.lifelike.entity.User
import com.github.zsoltk.backtrack.composable.BackHandler

interface Root {

    sealed class Routing {
        object LoggedOut: Routing()
        data class LoggedIn(val user: User): Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing) {
            BackHandler(defaultRouting) { backStack ->
                when (val currentRouting = backStack.last()) {
                    is Routing.LoggedOut -> LoggedOut.Content(
                        onLoggedIn = { user ->
                            backStack.replace(
                                Routing.LoggedIn(
                                    user
                                )
                            )
                        }
                    )
                    is Routing.LoggedIn -> LoggedIn.Content(
                        defaultRouting = LoggedIn.Routing.Hello,
                        user = currentRouting.user,
                        onLogout = {
                            backStack.replace(Routing.LoggedOut)
                        }
                    )
                }
            }
        }
    }
}
