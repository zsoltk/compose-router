package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutWidth
import androidx.ui.layout.Row
import androidx.ui.layout.Spacer
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.imageResource
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
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
            val image = imageResource(R.drawable.placeholder)
            val typography = MaterialTheme.typography()

            Ripple(bounded = true) {
                Clickable(onClick = { onAlbumSelected(album) }) {
                    Row(modifier = LayoutPadding(all = 16.dp) + LayoutWidth.Fill) {
                            Container(width = 40.dp, height = 40.dp) {
                                DrawImage(image)
                            }
                            Spacer(modifier = LayoutWidth(16.dp))
                            Column {
                                Text(album.name, style = typography.subtitle1.copy(fontWeight = FontWeight.Bold))
                                Text("${album.photos.size} photos", style = typography.body1)
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
