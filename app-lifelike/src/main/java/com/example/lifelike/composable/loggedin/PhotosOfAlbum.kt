package com.example.lifelike.composable.loggedin

import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.lifelike.R
import com.example.lifelike.entity.Album
import com.example.lifelike.entity.Photo
import com.example.lifelike.entity.albums


interface PhotosOfAlbum {

    companion object {
        @Composable
        fun Content(album: Album, onPhotoSelected: (Photo) -> Unit) {
            if (album.photos.isEmpty()) {
                EmptyView()
            } else {
                AlbumView(album = album, onPhotoSelected = onPhotoSelected)
            }
        }

        @Composable
        fun EmptyView() {
            Box(modifier = Modifier.fillMaxSize()) {
                Text("No photos yet")
            }
        }

        @Composable
        fun AlbumView(album: Album, onPhotoSelected: (Photo) -> Unit) {
            Column {
                AlbumTitle(album)
                PhotoCount(album)
                PhotoGrid(album = album, onPhotoSelected = onPhotoSelected)
            }
        }

        @Composable
        fun AlbumTitle(album: Album) {
            val typography = MaterialTheme.typography

            Text(
                text = album.name,
                style = typography.h4,
                modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 4.dp)
            )
        }

        @Composable
        fun PhotoCount(album: Album) {
            val typography = MaterialTheme.typography

            Text(
                text = "${album.photos.size} photos",
                style = typography.body1,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
            )
        }

        @Composable
        fun PhotoGrid(album: Album, onPhotoSelected: (Photo) -> Unit) {
            val cols = 4
            val image = imageResource(R.drawable.placeholder)
            val photoRows =  album.photos.chunked(cols)

            Box(modifier = Modifier.padding(4.dp)) {
                LazyColumnFor(photoRows) { row ->
                    WithConstraints {
                        Row {
                            val w = with(DensityAmbient.current) { (constraints.maxWidth.toDp().value / cols).dp }
                            row.forEach { photo ->
                                Box(modifier = Modifier
                                        .width(w)
                                        .padding(4.dp)
                                        .clickable(onClick = { onPhotoSelected(photo) })
                                ) {
                                    Box(modifier = Modifier.aspectRatio(1f)) {
                                        Image(image)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PhotosOfAlbumPreview() {
    PhotosOfAlbum.Content(albums.first()) {}
}
