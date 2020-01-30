package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.DrawImage
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutHeight
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.Spacer
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
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
            val typography = MaterialTheme.typography()

            Surface(color = Color.DarkGray) {
                Container(modifier = LayoutPadding(32.dp)) {
                    Column {
                        Container(modifier = LayoutFlexible(0.8f), expanded = true) {
                            DrawImage(image = image)
                        }

                        Spacer(modifier = LayoutHeight(32.dp))
                        Text(
                            text = photo.title,
                            style = typography.subtitle1.copy(
                                color = Color.White
                            )
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
