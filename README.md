![compose-router](https://i.imgur.com/Ali4NJi.png)

[![Version](https://jitpack.io/v/zsoltk/compose-router.svg)](https://jitpack.io/#zsoltk/backtrack)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)


## What's this?
Routing functionality for Jetpack Compose with back stack:

- Helps to map your whole app structure using Compose — not just the UI parts
- Supports a single-Activity approach — no Fragments, no Navigation component needed
- Simply branch on current routing and compose any other @Composable
- Back stack saves the history of routing
- Can be integrated with automatic back press handling to go back in screen history
- Can be integrated with automatic scoped `savedInstanceState` persistence

Compatible with Compose version **0.1.0-dev03**

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


## How to use
On any level where routing functionality is needed, create a sealed class to represent your routing:

```kotlin
sealed class Routing {
    object AlbumList : Routing()
    data class PhotosOfAlbum(val album: Album) : Routing()
    data class FullScreenPhoto(val photo: Photo) : Routing()
}
```

Use the `Router` Composable and enjoy back stack functionality:

```kotlin
@Composable
fun GalleryView(defaultRouting: Routing) {
    Router("GalleryView", defaultRouting) { backStack ->
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

For more usage examples see the example apps.

To go back in the back stack, you can either call the `.pop()` method programmatically, or just press the back button on the device (see next section for back press integration).

Back stack operations:
- **push()**
- **pushAndDropNested()**
- **pop()**
- **replace()**
- **newRoot()**


## Connect it to back press event

To ensure that back press automatically pops the back stack and restores history, add this to your Activity:

```kotlin
class MainActivity : AppCompatActivity() {
    private val backPressHandler = BackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            backPressHandler.Provider {
                // Your root composable goes here
            }
        }
    }

    override fun onBackPressed() {
        if (!backPressHandler.handle()) {
            super.onBackPressed()
        }
    }
}
```

## Connect it to savedInstanceState

Router can automatically add scoped Bundle support for your client code.

Minimal setup:

```kotlin
class MainActivity : AppCompatActivity() {
    private val backPressHandler = BackPressHandler()
    private val timeCapsule = TimeCapsule()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                timeCapsule.Provider(savedInstanceState) {
                    Root.Content(LoggedOut)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        timeCapsule.onSaveInstanceState(outState)
    }
}
```

In client code you can now use:

```kotlin
@Composable
fun Content() {
    val bundle = +ambient(savedInstanceState)
    var counter by +state { bundle.getInt(KEY_COUNTER, 0) }

    Button(text = "Counter: $counter", onClick = {
        bundle.putInt(KEY_COUNTER, ++counter)
    })
}
```
