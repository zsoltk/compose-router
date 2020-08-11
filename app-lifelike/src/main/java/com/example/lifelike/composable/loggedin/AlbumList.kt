package com.example.lifelike.composable.loggedin

import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.lifelike.R
import com.example.lifelike.entity.Album
import com.example.lifelike.entity.albums


interface AlbumList {

    companion object {
        @Composable
        fun Content(onAlbumSelected: (Album) -> Unit) {
            ScrollableColumn {
                albums.forEach {
                    AlbumRow(it, onAlbumSelected)
                }
            }
        }

        @Composable
        private fun AlbumRow(album: Album, onAlbumSelected: (Album) -> Unit) {
            val image = imageResource(R.drawable.placeholder)
            val typography = MaterialTheme.typography

            Box(
                    modifier = Modifier.clickable(onClick = { onAlbumSelected(album) })
            ) {
                Row(modifier = Modifier.padding(all = 16.dp)) {
                    Box(modifier = Modifier.preferredSize(40.dp, 40.dp)) {
                        Image(image)
                    }
                    Spacer(modifier = Modifier.preferredWidth(16.dp))
                    Column {
                        Text(
                            album.name,
                            style = typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                        )
                        Text("${album.photos.size} photos", style = typography.body1)
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
