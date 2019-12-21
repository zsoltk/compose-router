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
            BackHandler("Root", defaultRouting) { backStack ->
                when (val currentRouting = backStack.last()) {
                    is Routing.LoggedOut -> LoggedOut.Content(
                        defaultRouting = LoggedOut.Routing.Splash,
                        onLoggedIn = { user ->
                            // play around with other back stack operations here:
                            backStack.newRoot(
                                Routing.LoggedIn(user)
                            )
                        }
                    )
                    is Routing.LoggedIn -> LoggedIn.Content(
                        defaultRouting = LoggedIn.Routing.Hello,
                        user = currentRouting.user,
                        onLogout = {
                            // play around with other back stack operations here:
                            backStack.newRoot(
                                Routing.LoggedOut
                            )
                        }
                    )
                }
            }
        }
    }
}
