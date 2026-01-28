package me.mars.maple.renderer.elem

import arc.math.Mathf
import arc.scene.Element
import arc.util.Align
import me.mars.maple.schema.modifier.Fill
import me.mars.maple.schema.modifier.Padding
import me.mars.maple.schema.modifier.SizeIn
import kotlin.math.max
import kotlin.math.min

open class LayoutCell(elem: Element) {
    // The element this cell is holding
    val elem: Element = elem
    // Whether this cell's element will be forced to fill the width/height of the free space respectively
    var fillX: Boolean = false
    var fillY: Boolean = false
    // The additional size constraints placed on elements.
    // TODO Do we need to enforce that these values conform to the min/max sizes the element provides, so we don't get funky results in getPrefWidth/Height(?)
    var forceMinWidth: Float = 0f
    var forceMinHeight: Float = 0f
    var forceMaxWidth: Float = Float.MAX_VALUE
    var forceMaxHeight: Float = Float.MAX_VALUE

    var padLeft = 0
    var padRight = 0
    var padTop = 0
    var padBottom = 0

    var align: Int = Align.center

    fun getMinWidth(): Float = max(forceMinWidth, elem.minWidth) + padLeft + padRight
    fun getMinHeight(): Float = max(forceMinHeight, elem.minHeight) + padTop + padBottom

    fun getMaxWidth(): Float = min(forceMaxWidth, elem.maxWidth) + padLeft + padRight
    fun getMaxHeight(): Float = min(forceMaxHeight, elem.maxHeight) + padTop + padBottom

    fun getPrefWidth(): Float = Mathf.clamp(elem.prefWidth, forceMinWidth, forceMaxWidth) + padLeft + padRight // TODO Is this the behaviour we want? A cell may *prefer* this width but it should be fine with us eating up space for padding?
    fun getPrefHeight(): Float = Mathf.clamp(elem.prefHeight, forceMinHeight, forceMaxHeight) + padTop + padBottom
}

fun LayoutCell.applyPadding(modifier: Padding) {
    padLeft = modifier.left
    padRight = modifier.right
    padTop = modifier.top
    padBottom = modifier.bottom
}

fun LayoutCell.applyAlign(modifier: me.mars.maple.schema.modifier.Align) {
    align = modifier.align
}

fun LayoutCell.applySizeIn(modifier: SizeIn) {
    require(modifier.minWidth <= modifier.maxWidth) { "minWidth must be <= maxWidth" }
    require(modifier.minHeight <= modifier.maxHeight) { "minHeight must be <= maxHeight" }
    forceMinWidth = modifier.minWidth
    forceMinHeight = modifier.minHeight
    forceMaxWidth = modifier.maxWidth
    forceMaxHeight = modifier.maxHeight
}

fun LayoutCell.applyFill(modifier: Fill) {
    fillX = modifier.fillX
    fillY = modifier.fillY
}