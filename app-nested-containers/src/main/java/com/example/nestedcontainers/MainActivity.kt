package com.example.nestedcontainers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.HorizontalScroller
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.example.nestedcontainers.composable.SomeChild
import com.github.zsoltk.compose.backpress.AmbientBackPressHandler
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.router.AmbientRouting
import com.github.zsoltk.compose.savedinstancestate.BundleScope
import com.github.zsoltk.compose.savedinstancestate.saveAmbient

class MainActivity : AppCompatActivity() {
    private val backPressHandler = BackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HorizontalScroller {
                    Providers(
                        AmbientBackPressHandler provides backPressHandler,
                        AmbientRouting provides intent.deepLinkRoute()
                    ) {
                        BundleScope(savedInstanceState) {
                            SomeChild.Root()
                        }
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


@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        SomeChild.Root()
    }
}
