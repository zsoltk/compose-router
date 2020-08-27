package com.github.zsoltk.compose.savedinstancestate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun persistentInt(key: String, defaultValue: Int = 0): MutableState<Int> {
    val bundle = AmbientSavedInstanceState.current

    val state: MutableState<Int> = remember { mutableStateOf(
        bundle.getInt(key, defaultValue)
    )}

    saveInt(key, state.value)

    return state
}

@Composable
private fun saveInt(key: String, value: Int) {
    val bundle = AmbientSavedInstanceState.current
    bundle.putInt(key, value)
}
