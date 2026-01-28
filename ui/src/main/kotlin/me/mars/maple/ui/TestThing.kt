package me.mars.maple.ui

import androidx.compose.runtime.*
import app.cash.redwood.Modifier
import arc.scene.style.Drawable
import arc.scene.ui.Dialog
import arc.util.Align
import me.mars.maple.renderer.PrimitivesWidgetSystem
import me.mars.maple.schema.api.Arrangement
import me.mars.maple.schema.compose.ColumnScope
import mindustry.gen.Icon
import mindustry.gen.Tex
import mindustry.graphics.Pal

fun main() {

    val contUi = Maple.runMaple(PrimitivesWidgetSystem()) {
        Column {
//            TaskProgress(0.49f, buildList {
//                repeat(90) {
//                    add(it/100f)
//                }
//                shuffle()
//            })
//            TaskProgress(0.99f, buildList {
//                repeat(90) {
//                    add(it/100f)
//                }
//                shuffle()
//            })
            Button(onClick = {}) {
                Column {
                    Label("hello!")
//                    Label("I think you should click me")
                }
            }

            BarShowcase()
//            CheckBoxShowcase()
//            CollapserShowcase()
//            ImageButtonShowcase()
//            ImageShowcase()
//            TextAreaShowcase()
        }
    }
    val buttonUi = Maple.runMaple(PrimitivesWidgetSystem()) {
        Row {
            TextButton("ello!", {})
            TextButton("Kill table composition", { contUi.cancel() })
        }
    }

    val dialog = Dialog("Maple")
    dialog.closeOnBack()
    dialog.addCloseButton()
    dialog.cont.fill()
    dialog.cont.add(contUi.root).grow()
    dialog.buttons.add(buttonUi.root).grow()
    dialog.addCloseButton()
    dialog.show()

}

@Composable
fun ColumnScope.BarShowcase() {
    var name by remember { mutableStateOf("Loading") }
    var prog by remember { mutableStateOf(0f) }

    Row {
        Label("Bar name:")
        TextField(name, { name = it })
    }
    Row {
        TextButton("-", { prog += 0.01f })
        Label("Prog: $prog")
        TextButton("+", { prog -= 0.01f })
    }
    Bar(name, Pal.bar, prog, modifier = Modifier.sizeIn(200f, 75f, 200f, 75f).fill(true, true))
}

@Composable
fun CheckBoxShowcase() {
    var checked by remember { mutableStateOf(false) }
    CheckBox("Checkbox checked: $checked", checked, { checked = !checked })
}

@Composable
fun CollapserShowcase() {
    var collapsed by remember { mutableStateOf(false) }
    CheckBox("Collapsed: $collapsed", collapsed, { collapsed = !collapsed })
    Collapser(collapsed, enforceMinSize = true) {
        Column(arrangement = Arrangement.End) {
            repeat(20) {
                Label("Item $it")
            }
        }
    }
}

@Composable
fun ImageButtonShowcase() {
    val icons: Array<Drawable> = arrayOf(Icon.admin, Icon.move, Icon.map, Icon.chat)
    var clicks by remember { mutableStateOf(0) }
    ImageButton(icons[clicks % icons.size], { clicks += 1 })
}

@Composable
fun ImageShowcase() {
    val icons: Array<Drawable> = arrayOf(Icon.admin, Icon.move, Icon.map, Icon.chat)
    var clicks by remember { mutableStateOf(0) }
    Row {
        TextButton("Click me!", { clicks += 1 })
        Image(icons[clicks % icons.size])
    }
}

@Composable
fun TextAreaShowcase() {
    var text by remember { mutableStateOf("") }
    TextArea(text, { text = it })
}

@Composable
fun TaskProgress(total: Float, individual: List<Float>) {
    var collapsed by remember { mutableStateOf(false) }
    Box(background = Tex.pane) {
        Column(modifier = Modifier.padding(top = 25, bottom = 25, left = 10, right = 10)) {
            Row {
                ImageButton(if (collapsed) Icon.downOpen else Icon.upOpen, { collapsed = !collapsed })
                ImageButton(Icon.cancel, {})
                ImageButton(Icon.exportSmall, {})
            }
            Collapser(collapsed) {
                ScrollPane(scrollbarsOnTop = true, modifier = Modifier.sizeIn(minHeight = 150f, maxHeight = 150f, minWidth = 250f).fill(fillX = true)) {
                    Column(align = Align.left) {
                        for (t in individual) {
                            Label("Progress: $t")
                        }
                    }
                }
            }
        }
    }
}
