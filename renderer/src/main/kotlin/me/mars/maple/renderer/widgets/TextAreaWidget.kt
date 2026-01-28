package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.ui.TextArea
import arc.scene.ui.TextField
import me.mars.maple.schema.widget.TextAreaP

class TextAreaWidget(override val value: TextArea) : TextAreaP<Element> {
    override var modifier: Modifier = Modifier

    override fun text(text: String) {
        val cursor = value.cursorPosition
        value.text = text
        value.cursorPosition = cursor
    }

    override fun onChange(onChange: (String) -> Unit) {
        value.changed { onChange(value.text) }
    }

    override fun prefRows(prefRows: Int) {
        value.setPrefRows(prefRows.toFloat()) // TODO Float???
    }

    override fun textAreaStyle(textAreaStyle: TextField.TextFieldStyle) {
        value.style = textAreaStyle
    }
}