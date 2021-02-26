package com.example.lifelike

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import com.example.lifelike.composable.Root
import com.example.lifelike.composable.Root.Routing.LoggedOut
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.backpress.LocalBackPressHandler
import com.github.zsoltk.compose.router.LocalRouting
import com.github.zsoltk.compose.savedinstancestate.BundleScope
import com.github.zsoltk.compose.savedinstancestate.saveAmbient
import com.github.zsoltk.compose.savedinstancestate.saveLocal

class MainActivity : AppCompatActivity() {

    private val backPressHandler = BackPressHandler()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                CompositionLocalProvider(
                    LocalBackPressHandler provides backPressHandler,
                    LocalRouting provides intent.deepLinkRoute()
                ) {
                    BundleScope(savedInstanceState) {
                        Root.Content(LoggedOut)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (!backPressHandler.handle()) {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.saveLocal()
    }
}
