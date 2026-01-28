package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.ui.Label
import me.mars.maple.schema.widget.LabelP

class LabelWidget(override val value: Label) : LabelP<Element> {
    override var modifier: Modifier = Modifier

    override fun text(text: String) {
        value.setText(text) // TODO As per docs, strings starting with @ or $ will be treated as a bundle key. Do we want this?
    }

    override fun labelStyle(labelStyle: Label.LabelStyle) {
        value.style = labelStyle
    }

    override fun wrap(wrap: Boolean) {
        value.setWrap(wrap)
    }

    override fun ellipsis(ellipsis: Boolean) {
        value.setEllipsis(ellipsis)
    }
}