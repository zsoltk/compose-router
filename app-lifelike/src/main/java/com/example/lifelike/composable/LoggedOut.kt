package com.example.lifelike.composable

import androidx.animation.transitionDefinition
import androidx.compose.Composable
import com.example.lifelike.composable.loggedout.RegConfirmSmsCode
import com.example.lifelike.composable.loggedout.RegFinal
import com.example.lifelike.composable.loggedout.RegUserName
import com.example.lifelike.composable.loggedout.RegUserPhone
import com.example.lifelike.composable.loggedout.Splash
import com.example.lifelike.entity.User
import com.github.zsoltk.compose.router.Router
import com.github.zsoltk.compose.transition.AnimateChange
import com.github.zsoltk.compose.transition.AnimationParams.X
import com.github.zsoltk.compose.transition.TransitionStates.Active
import com.github.zsoltk.compose.transition.TransitionStates.Enter
import com.github.zsoltk.compose.transition.TransitionStates.Exit

interface LoggedOut {

    sealed class Routing {
        object Splash : Routing()
        object RegUserName : Routing()
        object RegUserPhone : Routing()
        object RegConfirmSmsCode : Routing()
        object RegFinal : Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing, onLoggedIn: (User) -> Unit) {
            val user = User("Demo user", "123456789")

            Router("LoggedOut", defaultRouting) { backStack ->
                fun Routing.next(): () -> Unit = {
                    when (this) {
                        Routing.Splash -> Routing.RegUserName
                        Routing.RegUserName -> Routing.RegUserPhone
                        Routing.RegUserPhone -> Routing.RegConfirmSmsCode
                        Routing.RegConfirmSmsCode -> Routing.RegFinal
                        Routing.RegFinal -> null
                    }?.let {
                        backStack.push(it)
                    }
                }

                AnimateChange(current = backStack.last(), transition = anim) { currentRouting ->
                    when (currentRouting) {
                        Routing.Splash -> Splash.Content(onNext = currentRouting.next())
                        Routing.RegUserName -> RegUserName.Content(user = user, onNext = currentRouting.next())
                        Routing.RegUserPhone -> RegUserPhone.Content(user = user, onNext = currentRouting.next())
                        Routing.RegConfirmSmsCode -> RegConfirmSmsCode.Content(onNext = currentRouting.next())
                        Routing.RegFinal -> RegFinal.Content(onNext = { onLoggedIn(user) })
                    }
                }
            }
        }

        private val anim = transitionDefinition {
            state(Enter) {
                this[X] = 1f
            }

            state(Active) {
                this[X] = 0f
            }

            state(Exit) {
                this[X] = -1f
            }

            transition {
                X using tween { duration = 300 }
            }
        }
    }
}
