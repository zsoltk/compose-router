package com.example.poormansbackstack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SomeChild.Root(BackPress) {
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        BackPress.triggered = true
    }
}

@Model
object BackPress {
    var triggered = false
}


@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        SomeChild.Root(BackPress) {}
    }
}
