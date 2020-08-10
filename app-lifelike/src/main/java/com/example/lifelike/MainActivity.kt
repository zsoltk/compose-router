package com.example.lifelike

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Providers
import androidx.compose.ui.platform.setContent
import com.example.lifelike.composable.Root
import com.example.lifelike.composable.Root.Routing.LoggedOut
import com.github.zsoltk.compose.backpress.AmbientBackPressHandler
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.router.AmbientRouting
import com.github.zsoltk.compose.savedinstancestate.BundleScope
import com.github.zsoltk.compose.savedinstancestate.saveAmbient

class MainActivity : AppCompatActivity() {
    private val backPressHandler = BackPressHandler()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Providers(
                    AmbientBackPressHandler provides backPressHandler,
                    AmbientRouting provides intent.deepLinkRoute()
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
        outState.saveAmbient()
    }
}
