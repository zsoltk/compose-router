package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.FlexRow
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TextButtonStyle
import androidx.ui.material.surface.Surface
import androidx.ui.unit.dp
import com.example.lifelike.composable.loggedin.Menu.MenuItem.Gallery
import com.example.lifelike.composable.loggedin.Menu.MenuItem.News
import com.example.lifelike.composable.loggedin.Menu.MenuItem.Profile

interface Menu {

    sealed class MenuItem {
        object Gallery : MenuItem()
        object News : MenuItem()
        object Profile : MenuItem()
    }

    data class State(
        val menuItems: List<MenuItem> = listOf(Gallery, News, Profile),
        val currentSelection: MenuItem
    )

    companion object {
        @Composable
        fun Content(state: State, onMenuItemClicked: (MenuItem) -> Unit) {
            FlexRow {
                state.menuItems.forEach { item ->
                    expanded(1f) {
                        MenuItem(
                            item,
                            item == state.currentSelection
                        ) {
                            onMenuItemClicked(it)
                        }
                    }
                }
            }
        }

        @Composable
        private fun MenuItem(item: MenuItem, isSelected: Boolean, onClick: (MenuItem) -> Unit) {
            val color = MaterialTheme.colors()

            Surface(
                color = if (isSelected) color.secondary else color.surface,
                shape = RoundedCornerShape(topLeft = 4.dp, topRight = 4.dp)
            ) {
                Button(onClick = { onClick.invoke(item) }, style = TextButtonStyle()) {
                    Text(
                        text = when (item) {
                            Gallery -> "Gallery"
                            News -> "News"
                            Profile -> "Profile"
                        },
                        style = MaterialTheme.typography().body2.copy(
                            color = if (isSelected) color.onSecondary else color.onSurface
                        )
                    )
                }
            }
        }
    }
}
