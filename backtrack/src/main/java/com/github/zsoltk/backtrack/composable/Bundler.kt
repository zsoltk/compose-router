package com.github.zsoltk.backtrack.composable

import android.os.Bundle
import androidx.compose.Ambient
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.onCommit
import androidx.compose.unaryPlus

val savedInstanceState: Ambient<Bundle> =
    Ambient.of { Bundle() }

class TimeCapsule {
    private var savedInstanceState: Bundle = Bundle()

    @Composable
    fun Provider(savedInstanceState: Bundle?, children: @Composable() () -> Unit) {
        this.savedInstanceState = savedInstanceState?.getBundle(KEY) ?: Bundle()

        com.github.zsoltk.backtrack.composable.savedInstanceState.Provider(value = this.savedInstanceState) {
            children()
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putBundle(KEY, savedInstanceState)
    }

    companion object {
        private const val KEY = "TimeCapsule.Root"
    }
}
