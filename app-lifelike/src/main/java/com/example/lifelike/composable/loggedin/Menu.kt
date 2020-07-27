package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
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
                    Box(modifier = Modifier.weight(1f)) {
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
            val color = MaterialTheme.colors

            Surface(
                color = if (isSelected) color.secondary else color.surface,
                shape = RoundedCornerShape(topLeft = 4.dp, topRight = 4.dp)
            ) {
                Box(
                    modifier = Modifier.clickable(onClick = { onClick.invoke(item) })
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
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
                        style = MaterialTheme.typography.body2.copy(
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
