package com.example.lifelike.composable.loggedin

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.ScrollableColumn
import androidx.ui.foundation.Text
import androidx.ui.layout.padding
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp


interface News {

    companion object {
        @Composable
        fun Content() {
            Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                ScrollableColumn {
                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Adipiscing elit duis tristique sollicitudin nibh sit amet. Sed lectus vestibulum mattis ullamcorper. Sit amet purus gravida quis blandit turpis cursus. Amet nulla facilisi morbi tempus iaculis urna id volutpat lacus. In nibh mauris cursus mattis molestie a iaculis. Eu turpis egestas pretium aenean pharetra magna ac placerat vestibulum. Sollicitudin tempor id eu nisl. Vitae nunc sed velit dignissim sodales ut eu sem. Vulputate eu scelerisque felis imperdiet proin fermentum. Aliquam ut porttitor leo a diam sollicitudin tempor. Sed sed risus pretium quam.\n" +
                                "\n" +
                                "Amet cursus sit amet dictum sit. Duis ut diam quam nulla porttitor. Ultricies lacus sed turpis tincidunt id aliquet risus feugiat. Rhoncus dolor purus non enim praesent elementum facilisis leo. Risus nullam eget felis eget. Massa sed elementum tempus egestas sed. Rhoncus urna neque viverra justo nec ultrices dui. Volutpat sed cras ornare arcu. Lacus luctus accumsan tortor posuere ac ut consequat semper. Penatibus et magnis dis parturient montes nascetur ridiculus mus.\n" +
                                "\n" +
                                "Purus viverra accumsan in nisl nisi scelerisque. Habitant morbi tristique senectus et netus. Et netus et malesuada fames ac turpis egestas integer eget. Urna duis convallis convallis tellus id interdum. Adipiscing diam donec adipiscing tristique risus nec. Viverra mauris in aliquam sem fringilla. Ut pharetra sit amet aliquam id diam maecenas ultricies mi. Consequat nisl vel pretium lectus quam id leo in vitae. Leo duis ut diam quam nulla porttitor massa. Amet commodo nulla facilisi nullam vehicula ipsum a. Massa sapien faucibus et molestie ac feugiat sed lectus vestibulum. Dui accumsan sit amet nulla facilisi morbi. Nisl condimentum id venenatis a condimentum vitae sapien pellentesque.\n" +
                                "\n" +
                                "Duis at tellus at urna condimentum mattis pellentesque id. Cursus metus aliquam eleifend mi in. Lacus luctus accumsan tortor posuere ac. Sed tempus urna et pharetra pharetra massa massa ultricies mi. Urna id volutpat lacus laoreet non curabitur. Et egestas quis ipsum suspendisse ultrices gravida dictum. Metus dictum at tempor commodo. Nullam non nisi est sit amet facilisis magna etiam tempor. Pellentesque habitant morbi tristique senectus et. Erat nam at lectus urna duis convallis convallis. Quis vel eros donec ac odio tempor orci dapibus ultrices. Maecenas sed enim ut sem viverra aliquet eget sit. Consectetur lorem donec massa sapien faucibus et molestie ac feugiat. Leo a diam sollicitudin tempor id eu nisl nunc mi. Sit amet consectetur adipiscing elit ut aliquam. Tellus at urna condimentum mattis pellentesque. Vitae turpis massa sed elementum tempus. Ultrices vitae auctor eu augue ut lectus arcu bibendum. Vulputate enim nulla aliquet porttitor lacus luctus accumsan tortor posuere.\n" +
                                "\n" +
                                "Lobortis elementum nibh tellus molestie nunc non. Orci eu lobortis elementum nibh tellus molestie nunc non blandit. Eu nisl nunc mi ipsum faucibus vitae aliquet nec ullamcorper. Eu lobortis elementum nibh tellus molestie nunc non blandit massa. In massa tempor nec feugiat nisl. Cras fermentum odio eu feugiat pretium nibh. Lacus viverra vitae congue eu consequat ac. Urna porttitor rhoncus dolor purus non enim praesent elementum. Facilisi etiam dignissim diam quis enim lobortis. Varius morbi enim nunc faucibus. Egestas congue quisque egestas diam in arcu cursus. Vulputate ut pharetra sit amet."
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun LoremIpsumPreview() {
    News.Content()
}
