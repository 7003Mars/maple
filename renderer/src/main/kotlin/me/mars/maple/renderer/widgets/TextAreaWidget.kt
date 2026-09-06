package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.ui.TextArea
import arc.scene.ui.TextField
import me.mars.maple.schema.widget.TextAreaP

class TextAreaWidget(override val value: TextArea) : TextAreaP<Element> {
    override var modifier: Modifier = Modifier
    private var _onChange: ((String) -> Unit)? = null

    init {
        value.changed { _onChange?.invoke(value.text) }
    }

    override fun text(text: String) {
        val cursor = value.cursorPosition
        value.text = text
        value.cursorPosition = cursor
    }

    override fun onChange(onChange: (String) -> Unit) {
        _onChange = onChange
    }

    override fun prefRows(prefRows: Int) {
        value.setPrefRows(prefRows.toFloat()) // TODO Float???
    }

    override fun textAreaStyle(textAreaStyle: TextField.TextFieldStyle) {
        value.style = textAreaStyle
    }
}