package com.example.lifelike.composable.loggedin

import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.lifelike.R
import com.example.lifelike.entity.Photo

interface FullScreenPhoto {

    companion object {
        @Composable
        fun Content(photo: Photo) {
            val image = imageResource(R.drawable.placeholder)
            val typography = MaterialTheme.typography

            Surface(color = Color.DarkGray) {
                Box(modifier = Modifier.padding(32.dp)) {
                    Column {
                        Box(modifier = Modifier.weight(0.8f).fillMaxSize().wrapContentSize(Alignment.Center)) {
                            Image(asset = image)
                        }

                        Spacer(modifier = Modifier.preferredHeight(32.dp))
                        Text(
                            text = photo.title,
                            style = typography.subtitle1.copy(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun FullScreenPhotoPreview() {
    FullScreenPhoto.Content(Photo(167))
}
