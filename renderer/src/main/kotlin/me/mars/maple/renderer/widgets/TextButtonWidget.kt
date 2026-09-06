package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.ui.TextButton
import me.mars.maple.schema.widget.TextButtonP

class TextButtonWidget(override val value: TextButton) : TextButtonP<Element> {
    override var modifier: Modifier = Modifier
    private var _onClick: (() -> Unit)? = null

    init {
        value.clicked { _onClick?.invoke() }
    }

    override fun text(text: String) {
        value.setText(text)
    }

    override fun onClick(onClick: () -> Unit) {
        _onClick = onClick
    }

    override fun buttonStyle(buttonStyle: TextButton.TextButtonStyle) {
        value.style = buttonStyle
    }

    override fun disabled(disabled: Boolean) {
        value.setDisabled(disabled)
    }
}