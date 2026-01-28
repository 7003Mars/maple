package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import app.cash.redwood.widget.Widget
import arc.scene.Element
import arc.scene.style.Drawable
import me.mars.maple.schema.widget.BoxP

class BoxWidget(override val value: me.mars.maple.renderer.elem.Box) : BoxP<Element> {
    override var modifier: Modifier = Modifier

    override val children: Widget.Children<Element>
        get() = object : Widget.Children<Element> {
            override val widgets: List<Widget<Element>> = mutableListOf()

            override fun insert(index: Int, widget: Widget<Element>) {
                value.insert(index, widget)
            }

            override fun move(fromIndex: Int, toIndex: Int, count: Int) {
                value.move(fromIndex, toIndex, count)
            }

            override fun remove(index: Int, count: Int) {
                // TODO we need to call removeChild on the parent.
                value.remove(index, count)
            }

            override fun onModifierUpdated(
                index: Int,
                widget: Widget<Element>
            ) {
                value.onModifierUpdated(index, widget)
            }

            override fun detach() {
                // TODO I dont actually know what this is for
            }
        }

    override fun background(background: Drawable?) {
        value.background = background
    }
}

