package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import app.cash.redwood.widget.Widget
import arc.scene.Element
import arc.scene.ui.layout.Collapser
import arc.scene.ui.layout.Table
import me.mars.maple.renderer.elem.Box
import me.mars.maple.schema.widget.CollapserP

class CollapserWidget : CollapserP<Element> {
    val container = Box()
    val table = Table()
    override val value: Collapser = Collapser(table, false)
    override var modifier: Modifier = Modifier

    init {
        table.add(container).grow() // TODO Is grow needed?
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

    override fun collapsed(collapsed: Boolean) {
        value.isCollapsed = collapsed
    }

    override fun duration(duration: Float) {
        value.setDuration(duration)
    }

    override fun enforceMinSize(enforceMinSize: Boolean) {
        value.setEnforceMinSize(enforceMinSize)
    }
}