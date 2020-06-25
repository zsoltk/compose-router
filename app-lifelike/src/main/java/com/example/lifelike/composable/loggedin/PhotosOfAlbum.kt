package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Box
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.layout.aspectRatio
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.Row
import androidx.ui.layout.padding
import androidx.ui.layout.width
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
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
                LazyColumnItems(photoRows) { row ->
                    WithConstraints {
                        Row {
                            val w = with(DensityAmbient.current) { (constraints.maxWidth.toDp().value / cols).dp }
                            row.forEach { photo ->
                                Box(modifier = Modifier
                                        .width(w)
                                        .padding(4.dp)
                                        .clickable(onClick = { onPhotoSelected(photo) })
                                ) {
                                    Box(modifier = Modifier.aspectRatio(1f).fillMaxSize()) {
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
