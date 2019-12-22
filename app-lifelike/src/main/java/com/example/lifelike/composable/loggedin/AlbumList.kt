package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.imageResource
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import com.example.lifelike.R
import com.example.lifelike.entity.Album
import com.example.lifelike.entity.albums


interface AlbumList {

    companion object {
        @Composable
        fun Content(onAlbumSelected: (Album) -> Unit) {
            VerticalScroller {
                Column {
                    albums.forEach {
                        AlbumRow(it, onAlbumSelected)
                    }
                }
            }
        }

        @Composable
        private fun AlbumRow(album: Album, onAlbumSelected: (Album) -> Unit) {
            val image = +imageResource(R.drawable.placeholder)
            val typography = +MaterialTheme.typography()

            Ripple(bounded = true) {
                Clickable(onClick = { onAlbumSelected(album) }) {
                    FlexRow(modifier = Spacing(all = 16.dp)) {
                        inflexible {
                            Container(modifier = Size(40.dp, 40.dp)) {
                                DrawImage(image)
                            }
                            WidthSpacer(width = 16.dp)
                            Column {
                                Text(album.name, style = typography.subtitle1.copy(fontWeight = FontWeight.Bold))
                                Text("${album.photos.size} photos", style = typography.body1)
                            }
                        }
                        flexible(0f) {
                            Container(expanded = true) {}
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun AlbumListPreview() {
    AlbumList.Content {}
}