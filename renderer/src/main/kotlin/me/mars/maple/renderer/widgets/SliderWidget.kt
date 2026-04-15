package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.ui.Slider
import me.mars.maple.schema.widget.SliderP

class SliderWidget(override val value: Slider) : SliderP<Element> {
    override var modifier: Modifier = Modifier

    override fun value(value: Float) {
        this.value.value = value
    }

    override fun min(min: Float) {
        value.setRange(min, value.maxValue)
    }

    override fun max(max: Float) {
        value.setRange(value.minValue, max)
    }

    override fun stepSize(stepSize: Float) {
        value.stepSize = stepSize
    }

    override fun onValueChanged(onValueChanged: (Float) -> Unit) {
        value.changed { onValueChanged(value.value) }
    }

    override fun sliderStyle(sliderStyle: Slider.SliderStyle) {
        value.style = sliderStyle
    }
}