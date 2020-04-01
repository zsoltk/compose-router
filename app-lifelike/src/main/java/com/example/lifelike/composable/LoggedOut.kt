package com.example.lifelike.composable

import androidx.compose.Composable
import com.example.lifelike.composable.loggedout.*
import com.example.lifelike.entity.User
import com.github.zsoltk.compose.router.Router

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

            Router(defaultRouting) { backStack ->
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

                when (val currentRouting = backStack.last()) {
                    Routing.Splash -> Splash.Content(onNext = currentRouting.next())
                    Routing.RegUserName -> RegUserName.Content(
                        user = user,
                        onNext = currentRouting.next()
                    )
                    Routing.RegUserPhone -> RegUserPhone.Content(
                        user = user,
                        onNext = currentRouting.next()
                    )
                    Routing.RegConfirmSmsCode -> RegConfirmSmsCode.Content(onNext = currentRouting.next())
                    Routing.RegFinal -> RegFinal.Content(onNext = { onLoggedIn(user) })
                }
            }
        }
    }
}
