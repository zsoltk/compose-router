package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import com.example.lifelike.entity.Album
import com.example.lifelike.entity.Photo
import com.github.zsoltk.compose.router.transient.BackHandler

interface Gallery {

    sealed class Routing {
        object AlbumList : Routing()
        data class PhotosOfAlbum(val album: Album) : Routing()
        data class FullScreenPhoto(val photo: Photo) : Routing()
    }

    companion object {
        @Composable
        fun Content(defaultRouting: Routing) {
            BackHandler("Gallery", defaultRouting) { backStack ->
                when (val routing = backStack.last()) {
                    is Routing.AlbumList -> AlbumList.Content(
                        onAlbumSelected = {
                            backStack.push(Routing.PhotosOfAlbum(it))
                        })

                    is Routing.PhotosOfAlbum -> PhotosOfAlbum.Content(
                        album = routing.album,
                        onPhotoSelected = {
                            backStack.push(Routing.FullScreenPhoto(it))
                        })

                    is Routing.FullScreenPhoto -> FullScreenPhoto.Content(
                        photo = routing.photo
                    )
                }
            }
        }
    }
}
