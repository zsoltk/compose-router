package com.example.nestedcontainers

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nestedcontainers.composable.SomeChild
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.backpress.LocalBackPressHandler
import com.github.zsoltk.compose.router.LocalRouting
import com.github.zsoltk.compose.savedinstancestate.BundleScope
import com.github.zsoltk.compose.savedinstancestate.saveLocal

class MainActivity : AppCompatActivity() {
    private val backPressHandler = BackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    CompositionLocalProvider(
                        LocalBackPressHandler provides backPressHandler,
                        LocalRouting provides intent.deepLinkRoute()
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
        outState.saveLocal()
    }
}


@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        SomeChild.Root()
    }
}
