package me.mars.maple.renderer.widgets

import app.cash.redwood.Modifier
import arc.scene.Element
import arc.scene.ui.ProgressBar
import me.mars.maple.schema.widget.ProgressBarP

class ProgressBarWidget(override val value: ProgressBar) : ProgressBarP<Element> {
    override var modifier: Modifier = Modifier
    override fun progress(progress: Float) {
        value.setValue(progress)
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

    override fun progressBarStyle(progressBarStyle: ProgressBar.ProgressBarStyle) {
        value.style = progressBarStyle
    }
}