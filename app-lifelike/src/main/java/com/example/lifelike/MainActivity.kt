package com.example.lifelike

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.example.lifelike.composable.Root
import com.example.lifelike.composable.Root.Routing.LoggedOut
import com.github.zsoltk.backtrack.composable.RootBackHandler
import com.github.zsoltk.backtrack.helper.HandlerList

class MainActivity : AppCompatActivity() {
    private val rootHandler = HandlerList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RootBackHandler(rootHandler) {
                    Root.Content(LoggedOut)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!rootHandler.handle()) {
            super.onBackPressed()
        }
    }
}
