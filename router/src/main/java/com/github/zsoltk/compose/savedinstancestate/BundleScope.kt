package com.github.zsoltk.compose.savedinstancestate

import android.os.Bundle
import androidx.compose.runtime.*

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
    val upstream = LocalSavedInstanceState.current
    val downstream = upstream.getBundle(key) ?: defaultBundle

    SideEffect {
        upstream.putBundle(key, downstream)
    }
    if (autoDispose) {
        DisposableEffect(Unit) { onDispose { upstream.remove(key) } }
    }

    CompositionLocalProvider(LocalSavedInstanceState provides downstream) {
        children(downstream)
    }
}
