package com.example.lifelike.composable.loggedin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
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
