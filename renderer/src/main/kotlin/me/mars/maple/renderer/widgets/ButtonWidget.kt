package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import app.cash.redwood.widget.Widget
import arc.scene.Element
import arc.scene.ui.Button
import me.mars.maple.renderer.elem.Box
import me.mars.maple.schema.widget.ButtonP

class ButtonWidget : ButtonP<Element> {
    val container = Box()
    override val value: Button = Button()
    override var modifier: Modifier = Modifier
    private var _onClick: (() -> Unit)? = null

    init {
        value.add(container).grow()
        value.clicked { _onClick?.invoke() }
    }

    override val children: Widget.Children<Element> = object : Widget.Children<Element> {
        override val widgets: List<Widget<Element>> = mutableListOf()

        override fun insert(index: Int, widget: Widget<Element>) {
            container.insert(index, widget)
        }

        override fun move(fromIndex: Int, toIndex: Int, count: Int) {
            container.move(fromIndex, toIndex, count)
        }

        override fun remove(index: Int, count: Int) {
            container.remove(index, count)
        }

        override fun onModifierUpdated(index: Int, widget: Widget<Element>) {
            container.onModifierUpdated(index, widget)
        }

        override fun detach() {

        }
    }

    override fun onClick(onClick: () -> Unit) {
        _onClick = onClick
    }

    override fun style(style: Button.ButtonStyle) {
        value.style = style
    }
}
