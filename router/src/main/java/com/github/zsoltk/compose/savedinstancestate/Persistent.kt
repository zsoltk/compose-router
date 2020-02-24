package com.github.zsoltk.compose.savedinstancestate

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.state

@Composable
fun persistentInt(key: String, defaultValue: Int = 0): MutableState<Int> {
    val bundle = AmbientSavedInstanceState.current

    val state: MutableState<Int> = state {
        bundle.getInt(key, defaultValue)
    }

    saveInt(key, state.value)

    return state
}

@Composable
private fun saveInt(key: String, value: Int) {
    val bundle = AmbientSavedInstanceState.current
    bundle.putInt(key, value)
}
