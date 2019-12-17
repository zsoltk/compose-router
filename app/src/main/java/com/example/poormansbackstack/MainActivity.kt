package com.example.poormansbackstack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.HorizontalScroller
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    private val backPress = BackPress()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                HorizontalScroller {
                    SomeChild.Root(backPress) {
                        finish()
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
        SomeChild.Root(BackPress()) {}
    }
}
