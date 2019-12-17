package com.example.poormansbackstack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.HorizontalScroller
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.example.poormansbackstack.backpress.BackPress
import com.example.poormansbackstack.backpress.composable.RootBackHandler
import com.example.poormansbackstack.composable.SomeChild

class MainActivity : AppCompatActivity() {
    private val backPress = BackPress()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HorizontalScroller {
                    RootBackHandler(
                        backPress = backPress,
                        cantPopBackStack = { finish() }) {
                        SomeChild.Root()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        backPress.triggered = true
    }
}


@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        SomeChild.Root()
    }
}
