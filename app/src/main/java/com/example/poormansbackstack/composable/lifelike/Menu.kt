package com.example.poormansbackstack.composable.lifelike

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.FlexRow
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButtonStyle
import androidx.ui.material.surface.Surface
import com.example.poormansbackstack.composable.lifelike.Menu.MenuItem.Hello
import com.example.poormansbackstack.composable.lifelike.Menu.MenuItem.LoremIpsum
import com.example.poormansbackstack.composable.lifelike.Menu.MenuItem.Profile

interface Menu {

    sealed class MenuItem {
        object Hello : MenuItem()
        object LoremIpsum : MenuItem()
        object Profile : MenuItem()
    }

    data class State(
        val menuItems: List<MenuItem> = listOf(Hello, LoremIpsum, Profile),
        val currentSelection: MenuItem
    )

    companion object {
        @Composable
        fun Content(state: State, onMenuItemClicked: (MenuItem) -> Unit) {
            FlexRow {
                state.menuItems.forEach { item ->
                    expanded(1f) {
                        MenuItem(item, item == state.currentSelection) {
                            onMenuItemClicked(it)
                        }
                    }
                }
            }
        }

        @Composable
        private fun MenuItem(item: Menu.MenuItem, isSelected: Boolean, onClick: (Menu.MenuItem) -> Unit) {
            val color = +MaterialTheme.colors()

            Surface(
                color = if (isSelected) color.secondary else color.surface,
                shape = RoundedCornerShape(topLeft = 4.dp, topRight = 4.dp)
            ) {
                Button(onClick = { onClick.invoke(item) }, style = TextButtonStyle()) {
                    Text(
                        text = when (item) {
                            Hello -> "Hello"
                            LoremIpsum -> "Lorem ipsum"
                            Profile -> "Profile"
                        },
                        style = (+MaterialTheme.typography()).body2.copy(
                            color = if (isSelected) color.onSecondary else color.onSurface
                        )
                    )
                }
            }
        }
    }
}