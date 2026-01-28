package me.mars.maple.renderer.elem

import arc.scene.Element
import arc.scene.utils.Elem
import me.mars.maple.schema.modifier.Weight

class WeightedLayoutCell(elem: Element) : LayoutCell(elem) {
    var weight: Float = 0f
}

fun WeightedLayoutCell.applyWeight(modifier: Weight) {
    weight = modifier.weight
}