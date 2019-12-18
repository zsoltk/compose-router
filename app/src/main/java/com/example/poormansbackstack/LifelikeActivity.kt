package com.example.poormansbackstack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import com.example.poormansbackstack.backpress.HandlerList
import com.example.poormansbackstack.backpress.composable.RootBackHandler
import com.example.poormansbackstack.composable.lifelike.Root
import com.example.poormansbackstack.composable.lifelike.Root.Routing.LoggedOut

class LifelikeActivity : AppCompatActivity() {
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
