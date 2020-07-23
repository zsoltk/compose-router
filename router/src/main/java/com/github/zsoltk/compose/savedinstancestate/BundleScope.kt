package com.github.zsoltk.compose.savedinstancestate

import android.os.Bundle
import androidx.compose.Composable
import androidx.compose.Providers
import androidx.compose.onCommit

@Composable
fun BundleScope(
    savedInstanceState: Bundle?,
    children: @Composable (bundle: Bundle) -> Unit
) {
    BundleScope(BUNDLE_KEY, savedInstanceState ?: Bundle(), true, children)
}

@Composable
fun BundleScope(
    key: String,
    children: @Composable (bundle: Bundle) -> Unit
) {
    BundleScope(key, Bundle(), true, children)
}

/**
 * Scopes a new Bundle with [key] under ambient [AmbientSavedInstanceState] and provides it
 * to [children].
 */
@Composable
fun BundleScope(
    key: String,
    defaultBundle: Bundle = Bundle(),
    autoDispose: Boolean = true,
    children: @Composable (Bundle) -> Unit
) {
    val upstream = AmbientSavedInstanceState.current
    val downstream = upstream.getBundle(key) ?: defaultBundle

    onCommit {
        upstream.putBundle(key, downstream)
        if (autoDispose) {
            onDispose { upstream.remove(key) }
        }
    }

    Providers(AmbientSavedInstanceState provides downstream) {
        children(downstream)
    }
}
