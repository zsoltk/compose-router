package com.example.lifelike.composable.loggedin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifelike.R
import com.example.lifelike.entity.Photo

interface FullScreenPhoto {

    companion object {
        @Composable
        fun Content(photo: Photo) {
            val image = painterResource(R.drawable.placeholder)
            val typography = MaterialTheme.typography

            Surface(color = Color.DarkGray) {
                Box(modifier = Modifier.padding(32.dp)) {
                    Column {
                        Box(modifier = Modifier.weight(0.8f).fillMaxSize().wrapContentSize(Alignment.Center)) {
                            Image(image, null)
                        }

                        Spacer(modifier = Modifier.height(32.dp))
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
