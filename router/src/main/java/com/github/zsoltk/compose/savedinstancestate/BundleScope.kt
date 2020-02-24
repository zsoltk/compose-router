package com.github.zsoltk.compose.savedinstancestate

import android.os.Bundle
import androidx.compose.Composable
import androidx.compose.Providers
import androidx.compose.onCommit


@Composable
fun BundleScope(
    key: String,
    children: @Composable() (bundle: Bundle) -> Unit
) {
    BundleScope(key, true, children)
}

/**
 * Scopes a new Bundle with [key] under ambient [ActiveSavedInstanceState] and provides it
 * to [children].
 */
@Composable
fun BundleScope(
    key: String,
    autoDispose: Boolean = true,
    children: @Composable() (Bundle) -> Unit
) {
    val upstream = ActiveSavedInstanceState.current
    val downstream = upstream.getBundle(key) ?: Bundle()

    onCommit {
        upstream.putBundle(key, downstream)
        if (autoDispose) {
            onDispose { upstream.remove(key) }
        }
    }

    Providers(ActiveSavedInstanceState provides downstream) {
        children(downstream)
    }
}
