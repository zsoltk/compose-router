package com.example.nestedcontainers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.HorizontalScroller
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.github.zsoltk.backtrack.helper.ScopedBackPressHandler
import com.github.zsoltk.backtrack.composable.RootBackHandler
import com.example.nestedcontainers.composable.SomeChild

class MainActivity : AppCompatActivity() {
    private val rootHandler = ScopedBackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HorizontalScroller {
                    RootBackHandler(rootHandler) {
                        SomeChild.Root()
                    }
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


@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        SomeChild.Root()
    }
}
