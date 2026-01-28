package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.style.Drawable
import arc.scene.ui.Image
import arc.util.Scaling
import me.mars.maple.schema.widget.ImageP

class ImageWidget(override val value: Image) : ImageP<Element> {
    override var modifier: Modifier = Modifier

    override fun drawable(drawable: Drawable) {
        value.drawable = drawable
    }

    override fun align(align: Int) {
        value.setAlign(align)
    }

    override fun scaling(scaling: Scaling) {
        value.setScaling(scaling)
    }


}