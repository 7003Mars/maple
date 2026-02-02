package me.mars.maple.renderer.elem

import app.cash.redwood.widget.Widget
import arc.scene.Element
import arc.util.Reflect

interface ChildrenAware {
    fun insert(index: Int, widget: Widget<Element>)

    fun move(fromIndex: Int, toIndex: Int, count: Int)

    fun remove(index: Int, count: Int)

    fun onModifierUpdated(index: Int, widget: Widget<Element>)

//    fun detach() // TODO I don't think we need this, even if its for memory cleanup or anything.
}

fun Element.onRemoved() {
    scene.unfocus(this)
    Reflect.set(Element::class.java, this, "stage", null)
    parent = null
}