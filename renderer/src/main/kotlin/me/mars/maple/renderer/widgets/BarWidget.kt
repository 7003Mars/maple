package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.graphics.Color
import arc.scene.Element
import me.mars.maple.schema.widget.BarP
import mindustry.ui.Bar

class BarWidget : BarP<Element> {
    override val value: Bar = Bar({ name }, { color }, { fraction })
    override var modifier: Modifier = Modifier

    var name: String = ""
    var fraction = 0f
    var color = Color()

    override fun name(name: String) {
        this.name = name
    }

    override fun color(color: Color) {
        this.color = color
    }

    override fun fraction(fraction: Float) {
        this.fraction = fraction
    }


}