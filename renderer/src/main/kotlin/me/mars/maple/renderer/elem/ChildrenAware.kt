package me.mars.maple.renderer.elem

import app.cash.redwood.widget.Widget
import arc.scene.Element

interface ChildrenAware {
    fun insert(index: Int, widget: Widget<Element>)

    fun move(fromIndex: Int, toIndex: Int, count: Int)

    fun remove(index: Int, count: Int)

    fun onModifierUpdated(index: Int, widget: Widget<Element>)

//    fun detach() // TODO I don't think we need this, even if its for memory cleanup or anything.
}