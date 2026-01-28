package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import app.cash.redwood.widget.Widget
import arc.scene.Element
import arc.scene.style.Drawable
import me.mars.maple.renderer.elem.Column
import me.mars.maple.schema.api.Arrangement
import me.mars.maple.schema.widget.ColumnP

class ColumnWidget(override val value: Column) : ColumnP<Element> {
    override var modifier: Modifier = Modifier

    override val children: Widget.Children<Element> = object : Widget.Children<Element>  {
        override val widgets: List<Widget<Element>> = mutableListOf()

        override fun insert(index: Int, widget: Widget<Element>) {
            value.insert(index, widget)
        }

        override fun move(fromIndex: Int, toIndex: Int, count: Int) {
            value.move(fromIndex, toIndex, count)
        }

        override fun remove(index: Int, count: Int) {
            value.remove(index, count)
        }

        override fun onModifierUpdated(index: Int, widget: Widget<Element>) {
            value.onModifierUpdated(index, widget)
        }

        override fun detach() {
            // TODO I dont actually know what this is for
        }

    }

    override fun align(align: Int) {
        value.defaultAlign = align
    }

    override fun arrangement(arrangement: Arrangement) {
        value.arrangement = arrangement
    }

    override fun background(background: Drawable?) {
        value.background = background
    }
}