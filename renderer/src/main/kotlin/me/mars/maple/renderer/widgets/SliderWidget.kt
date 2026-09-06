package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.ui.Slider
import me.mars.maple.schema.api.SliderRange
import me.mars.maple.schema.widget.SliderP

class SliderWidget(override val value: Slider) : SliderP<Element> {
    override var modifier: Modifier = Modifier
    private var suppress: Boolean = false
    private var _onValueChanged: ((Float) -> Unit)? = null

    init {
        value.changed {
            if (suppress) return@changed
            _onValueChanged?.invoke(value.value)
        }
    }

    override fun value(value: Float) {
        suppress = true
        this.value.value = value
        suppress = false
    }

    override fun range(range: SliderRange) {
        suppress = true
        value.setRange(range.min, range.max)
        suppress = false
    }

    override fun stepSize(stepSize: Float) {
        value.stepSize = stepSize
    }

    override fun onValueChanged(onValueChanged: (Float) -> Unit) {
        _onValueChanged = onValueChanged
    }

    override fun sliderStyle(sliderStyle: Slider.SliderStyle) {
        value.style = sliderStyle
    }
}