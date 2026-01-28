package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import app.cash.redwood.widget.Widget
import arc.scene.Element
import arc.scene.ui.ScrollPane
import me.mars.maple.renderer.elem.Box
import me.mars.maple.schema.widget.ScrollPaneP

class ScrollPaneWidget() : ScrollPaneP<Element> {
    val container: Box = Box()
    override val value: ScrollPane = ScrollPane(container)
    override var modifier: Modifier = Modifier

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

            override fun onModifierUpdated(
                index: Int,
                widget: Widget<Element>
            ) {
                container.onModifierUpdated(index, widget)
            }

            override fun detach() {

            }

        }

    override fun xScrollingDisabled(xScrollingDisabled: Boolean) {
        value.isScrollingDisabledX = xScrollingDisabled
    }

    override fun yScrollingDisabled(yScrollingDisabled: Boolean) {
        value.isScrollingDisabledY = yScrollingDisabled
    }

    override fun scrollbarsOnTop(scrollbarsOnTop: Boolean) {
        value.setScrollbarsOnTop(scrollbarsOnTop)
    }

    override fun style(style: ScrollPane.ScrollPaneStyle) {
        value.style = style
    }
}