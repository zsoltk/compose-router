package com.example.nestedcontainers

import android.content.Intent
import android.util.Log
import com.example.nestedcontainers.composable.SomeChild

// TODO: implement your own validation / conversion
fun Intent.deepLinkRoute(): List<Any> =
    when (data?.host) {
        //  adb shell am start -a "android.intent.action.VIEW" -d "app-nested://default/abcd"
        "default" -> parseDefaultDeepLink(data?.path)
        null -> emptyList()
        else -> emptyList<Any>().also {
            Log.e("compose-router", "Unexpected deep link: $data")
        }
    }

private fun parseDefaultDeepLink(path: String?): List<Any> =
    // TODO: implement your own validation / conversion
    path
        ?.take(4)
        ?.toCharArray()
        ?.map {
            when (it.toUpperCase()) {
                'R' -> SomeChild.Routing.SubtreeRed
                'G' -> SomeChild.Routing.SubtreeGreen
                'B' -> SomeChild.Routing.SubtreeBlue
                else -> Unit.also {
                    Log.e("compose-router", "Unexpected deep link path: $path")
                }
            }
        }
        ?.filter { it !is Unit }
        ?: emptyList()
