package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.compose.frames.ModelList
import androidx.compose.onActive
import androidx.compose.remember
import androidx.core.content.res.ResourcesCompat
import androidx.ui.core.DensityAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.WithConstraints
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.*
//import androidx.ui.layout.Table
//import androidx.ui.layout.TableColumnWidth
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.example.lifelike.R
import com.example.lifelike.entity.Album
import com.example.lifelike.entity.Photo
import com.example.lifelike.entity.albums
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


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
            //VerticalScroller {
                Column {
                    AlbumTitle(album)
                    PhotoCount(album)
                    PhotoGrid(album = album, onPhotoSelected = onPhotoSelected)
                }
            //}
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
            val nbPhotos = album.photos.size
            val lastIndex = album.photos.lastIndex
            val cols = 4
            val rows = nbPhotos / cols
            val image = imageResource(R.drawable.placeholder)

            val photoRows =  mutableListOf<List<Photo>>()

            for (i in album.photos.indices step cols) {
                val row = mutableListOf<Photo>()
                for (r in 0 until cols){
                    if (i+r >= album.photos.size) break
                    row.add(album.photos[i+r])
                }
                photoRows.add(row)
            }


            Box(modifier = Modifier.padding(4.dp)) {
                //scrolling fast may cause exception: https://issuetracker.google.com/issues/154653504
                AdapterList(
                        data = photoRows,
                        itemCallback = { row ->
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
                )
            }

                /*Table layout was removed temporarily until we will make it available again with a refreshed API. (Id88a7)
                https://developer.android.com/jetpack/androidx/releases/ui#0.1.0-dev11

                Table(columns = cols, columnWidth = { TableColumnWidth.Fraction(1.0f / cols) }) {
                    for (i in 0..rows) {
                        tableRow {
                            val startIndex = i * cols
                            val maxIndex = (i + 1) * cols - 1
                            val endIndex = if (maxIndex > lastIndex) lastIndex else maxIndex

                            for (j in startIndex..endIndex) {
                                Box(modifier = Modifier.padding(4.dp)) {
                                    Clickable(onClick = { onPhotoSelected(album.photos[j]) }) {
                                        Box(modifier = Modifier.aspectRatio(1f).fillMaxSize()) {
                                            Image(image)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }*/
        }
    }
}


@Preview
@Composable
fun PhotosOfAlbumPreview() {
    PhotosOfAlbum.Content(albums.first()) {}
}
