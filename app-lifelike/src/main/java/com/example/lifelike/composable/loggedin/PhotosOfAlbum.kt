package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Image
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutAspectRatio
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.Table
import androidx.ui.layout.TableColumnWidth
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
            Container(expanded = true) {
                Text("No photos yet")
            }
        }

        @Composable
        fun AlbumView(album: Album, onPhotoSelected: (Photo) -> Unit) {
            VerticalScroller {
                Column {
                    AlbumTitle(album)
                    PhotoCount(album)
                    PhotoGrid(album = album, onPhotoSelected = onPhotoSelected)
                }
            }
        }

        @Composable
        fun AlbumTitle(album: Album) {
            val typography = MaterialTheme.typography()

            Text(
                text = album.name,
                style = typography.h4,
                modifier = LayoutPadding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 4.dp)
            )
        }

        @Composable
        fun PhotoCount(album: Album) {
            val typography = MaterialTheme.typography()

            Text(
                text = "${album.photos.size} photos",
                style = typography.body1,
                modifier = LayoutPadding(start = 8.dp, end = 8.dp, bottom = 16.dp)
            )
        }

        @Composable
        fun PhotoGrid(album: Album, onPhotoSelected: (Photo) -> Unit) {
            val nbPhotos = album.photos.size
            val lastIndex = album.photos.lastIndex
            val cols = 4
            val rows = nbPhotos / cols
            val image = imageResource(R.drawable.placeholder)


            Container(modifier = LayoutPadding(4.dp)) {
                Table(columns = cols, columnWidth = { TableColumnWidth.Fraction(1.0f / cols) }) {
                    for (i in 0..rows) {
                        tableRow {
                            val startIndex = i * cols
                            val maxIndex = (i + 1) * cols - 1
                            val endIndex = if (maxIndex > lastIndex) lastIndex else maxIndex

                            for (j in startIndex..endIndex) {
                                Container(modifier = LayoutPadding(4.dp)) {
                                    Clickable(onClick = { onPhotoSelected(album.photos[j]) }) {
                                        Container(expanded = true, modifier = LayoutAspectRatio(1f)) {
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
}


@Preview
@Composable
fun PhotosOfAlbumPreview() {
    PhotosOfAlbum.Content(albums.first()) {}
}
