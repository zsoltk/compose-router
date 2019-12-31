package com.github.zsoltk.compose.savedinstancestate

import android.os.Bundle
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.onCommit
import androidx.compose.unaryPlus


@Composable
fun BundleScope(
    key: String,
    children: @Composable() (bundle: Bundle) -> Unit
) {
    BundleScope(key, true, children)
}

/**
 * Scopes a new Bundle with [key] under ambient [savedInstanceState] and provides it
 * to [children].
 */
@Composable
fun BundleScope(
    key: String,
    autoDispose: Boolean = true,
    children: @Composable() (Bundle) -> Unit
) {
    val upstream = +ambient(savedInstanceState)
    val downstream = upstream.getBundle(key) ?: Bundle()

    +onCommit {
        upstream.putBundle(key, downstream)
        if (autoDispose) {
            onDispose { upstream.remove(key) }
        }
    }

    savedInstanceState.Provider(value = downstream) {
        children(downstream)
    }
}
