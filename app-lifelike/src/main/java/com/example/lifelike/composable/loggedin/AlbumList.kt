package com.example.lifelike.composable.loggedin

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lifelike.R
import com.example.lifelike.entity.Album
import com.example.lifelike.entity.albums


interface AlbumList {

    companion object {
        @Composable
        fun Content(onAlbumSelected: (Album) -> Unit) {
            LazyColumn {
                items(albums) {
                    AlbumRow(it, onAlbumSelected)
                }
            }
        }

        @Composable
        private fun AlbumRow(album: Album, onAlbumSelected: (Album) -> Unit) {
            val image = painterResource(R.drawable.placeholder)
            val typography = MaterialTheme.typography

            Box(
                    modifier = Modifier.clickable(onClick = { onAlbumSelected(album) })
            ) {
                Row(modifier = Modifier.padding(all = 16.dp)) {
                    Box(modifier = Modifier.size(40.dp, 40.dp)) {
                        Image(image, null)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
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
