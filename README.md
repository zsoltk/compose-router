# Backtrack
[![Version](https://jitpack.io/v/zsoltk/backtrack.svg)](https://jitpack.io/#zsoltk/backtrack)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

## What's this?
Back stack functionality with back press handling for Jetpack Compose.

Compatible with version **0.1.0-dev03**

## Sample apps

#1 in `app-nested-containers` module:

![](https://i.imgur.com/w3Lr2IE.gif)

#2 in `app-lifelike` module:

![](https://i.imgur.com/4h22NyZ.gif)

## Download

Available through jitpack.

Add the maven repo to your root `build.gradle`

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency:
```groovy
implementation 'com.github.zsoltk:backtrack:{latest-version}'
```

## Setup

In your Activity:
```kotlin
class MainActivity : AppCompatActivity() {
    private val rootHandler = ScopedBackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootBackHandler(rootHandler) {
                // Your root composable goes here
            }
        }
    }

    override fun onBackPressed() {
        if (!rootHandler.handle()) {
            super.onBackPressed()
        }
    }
}
```

## How to use
On any level where back stack functionality is needed, create a sealed class to represent your routing:

```kotlin
sealed class Routing {
    object AlbumList : Routing()
    data class PhotosOfAlbum(val album: Album) : Routing()
    data class FullScreenPhoto(val photo: Photo) : Routing()
}
```

Use the `BackHandler` Composable and enjoy back stack functionality:

```kotlin
@Composable
fun GalleryView(defaultRouting: Routing) {
    BackHandler("GalleryView", defaultRouting) { backStack ->
        // compose further based on current routing:
        when (val routing = backStack.last()) {
            is Routing.AlbumList -> AlbumList.Content(
                onAlbumSelected = {
                    // add a new routing to the back stack:
                    backStack.push(Routing.PhotosOfAlbum(it))
                })

            is Routing.PhotosOfAlbum -> PhotosOfAlbum.Content(
                album = routing.album,
                onPhotoSelected = {
                    // add a new routing to the back stack:
                    backStack.push(Routing.FullScreenPhoto(it))
                })

            is Routing.FullScreenPhoto -> FullScreenPhoto.Content(
                photo = routing.photo
            )
        }
    }
}
```

To go back in the back stack, you can either call the `.pop()` method programmatically, or just press the back button on the device.

Back stack operations:
- **push()**
- **pushAndDropNested()**
- **pop()**
- **replace()**
- **newRoot()**

