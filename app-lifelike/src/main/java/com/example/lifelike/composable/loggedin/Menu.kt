package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.Container
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutWidth
import androidx.ui.layout.Row
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.text.AnnotatedString
import androidx.ui.text.ParagraphStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
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
            Row {
                state.menuItems.forEach { item ->
                    Container(modifier = LayoutFlexible(1f)) {
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
                Clickable(
                    onClick = { onClick.invoke(item) }
                ) {
                    Text(
                        modifier = LayoutWidth.Fill + LayoutPadding(8.dp),
                        text = AnnotatedString(
                            text = when (item) {
                                Gallery -> "Gallery"
                                News -> "News"
                                Profile -> "Profile"
                            },
                            paragraphStyle = ParagraphStyle(
                                textAlign = TextAlign.Center
                            )
                        ),
                        style = MaterialTheme.typography().body2.copy(
                            color = if (isSelected) color.onSecondary else color.onSurface
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMenu() {
    Menu.Content(Menu.State(currentSelection = Gallery), onMenuItemClicked = {})
}
