package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.ui.ImageButton
import arc.util.Scaling
import me.mars.maple.schema.widget.ImageButtonP

class ImageButtonWidget(override val value: ImageButton) : ImageButtonP<Element>{
    override var modifier: Modifier = Modifier
    private var _onClick: (() -> Unit)? = null

    init {
        value.clicked { _onClick?.invoke() }
    }

    override fun onClick(onClick: () -> Unit) {
        _onClick = onClick
    }

    override fun disabled(disabled: Boolean) {
        value.isDisabled = disabled
    }

    override fun align(align: Int) {
        value.image.setAlign(align)
    }

    override fun scaling(scaling: Scaling) {
        value.image.setScaling(scaling)
    }

    override fun buttonStyle(buttonStyle: ImageButton.ImageButtonStyle) {
        value.style = buttonStyle
    }


}