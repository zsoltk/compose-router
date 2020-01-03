package com.example.lifelike

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.lifelike.composable.LoggedIn
import com.example.lifelike.composable.Root
import com.example.lifelike.entity.User

// TODO: implement your own validation / conversion
fun Intent.deepLinkRoute(): List<Any> =
    when (data?.host) {
        //  adb shell 'am start -a "android.intent.action.VIEW" -d "app-lifelike://go-to-profile?name=fake&phone=123123"'
        "go-to-profile" -> parseProfileDeepLink(data)
        null -> emptyList()
        else -> emptyList<Any>().also {
            Log.e("compose-router", "Unexpected deep link: $data")
        }
    }

private fun parseProfileDeepLink(data: Uri): List<Any> {
    // Bear in mind that this is only a proof of concept implementation.
    // In real life you definitely don't want to do it like this.
    val name = data.getQueryParameter("name") ?: "<empty>"
    val phone = data.getQueryParameter("phone") ?: "<empty>"
    val user = User(name, phone)

    return listOf(
        // TODO support waiting for a routing to happen (e.g. user to authenticate by themselves)
        //  before moving on with the rest
        Root.Routing.LoggedIn(user),
        LoggedIn.Routing.Profile
    )
}
