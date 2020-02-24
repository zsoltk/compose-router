package com.example.nestedcontainers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Providers
import androidx.ui.core.setContent
import androidx.ui.foundation.HorizontalScroller
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.example.nestedcontainers.composable.SomeChild
import com.github.zsoltk.compose.backpress.AmbientBackPressHandler
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.router.AmbientRouting
import com.github.zsoltk.compose.savedinstancestate.AmbientTimeCapsule
import com.github.zsoltk.compose.savedinstancestate.TimeCapsule

class MainActivity : AppCompatActivity() {
    private val backPressHandler = BackPressHandler()
    private lateinit var timeCapsule: TimeCapsule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timeCapsule = TimeCapsule(savedInstanceState)

        setContent {
            MaterialTheme {
                HorizontalScroller {
                    Providers(
                        AmbientBackPressHandler provides backPressHandler,
                        AmbientTimeCapsule provides timeCapsule,
                        AmbientRouting provides intent.deepLinkRoute()
                    ) {
                        SomeChild.Root()
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
        timeCapsule.onSaveInstanceState(outState)
    }
}


@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        SomeChild.Root()
    }
}
