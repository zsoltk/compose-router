package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Spacer
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.padding
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.wrapContentSize
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
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
