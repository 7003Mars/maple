package me.mars.maple.renderer.elem

import app.cash.redwood.widget.Widget
import arc.scene.Element
import arc.scene.event.Touchable
import arc.scene.style.Drawable
import arc.scene.ui.layout.WidgetGroup
import arc.struct.Seq
import arc.util.Align
import me.mars.maple.renderer.move
import me.mars.maple.renderer.remove
import me.mars.maple.schema.modifier.Fill
import me.mars.maple.schema.modifier.Padding
import me.mars.maple.schema.modifier.SizeIn
import kotlin.math.max
import kotlin.math.min

class Box : WidgetGroup(), ChildrenAware {
    init {
        touchable = Touchable.childrenOnly
    }

    var background: Drawable? = null

    private val cells: Seq<LayoutCell> = Seq()
    private var sizeInvalid = true

    private var minWidth = 0f
    private var minHeight = 0f
    private var maxWidth = 0f
    private var maxHeight = 0f
    private var prefWidth = 0f
    private var prefHeight = 0f

    private fun computeSize() {
        sizeInvalid = false
        minWidth = 0f
        minHeight = 0f
        maxWidth = if (cells.isEmpty) 0f else Float.POSITIVE_INFINITY
        maxHeight = if (cells.isEmpty) 0f else Float.POSITIVE_INFINITY
        prefWidth = 0f
        prefHeight = 0f
        for (cell in cells) {
            minWidth = max(minWidth, cell.getMinWidth())
            minHeight = max(minHeight, cell.getMinHeight())
            maxWidth = min(maxWidth, cell.getMaxWidth())
            maxHeight = min(maxHeight, cell.getMaxHeight())
            prefWidth = max(prefWidth, cell.getPrefWidth())
            prefHeight = max(prefHeight, cell.getPrefHeight())
        }
    }

    override fun getMinWidth(): Float {
        if (sizeInvalid) computeSize()
        return minWidth
    }
    override fun getMinHeight(): Float {
        if (sizeInvalid) computeSize()
        return minHeight
    }

    override fun getPrefWidth(): Float {
        if (sizeInvalid) computeSize()
        return prefWidth
    }
    override fun getPrefHeight(): Float {
        if (sizeInvalid) computeSize()
        return prefHeight
    }

    override fun getMaxWidth(): Float {
        if (sizeInvalid) computeSize()
        return maxWidth
    }
    override fun getMaxHeight(): Float {
        if (sizeInvalid) computeSize()
        return maxHeight
    }

    override fun invalidate() {
        super.invalidate()
        sizeInvalid = true
    }

    // REMOVEME Using this for debugger breakpoints
    override fun setBounds(x: Float, y: Float, width: Float, height: Float) {
        super.setBounds(x, y, width, height)
    }

    override fun layout() {
        if (sizeInvalid) computeSize()

        // Thanks gemini
        for (cell in cells) {
            val elem = cell.elem
            val align = cell.align

            // How much free space is left
            val areaW = width - cell.padLeft - cell.padRight
            val areaH = height - cell.padTop - cell.padBottom
            // How much space the element is able to take up
            // TODO getMaxWidth() and getMaxHeight() never seem to be overridden and are always 0?
            val elemW = if (cell.fillX) /*min(elem.maxWidth, areaW)*/ areaW else min(elem.prefWidth, areaW)
            val elemH = if (cell.fillY) /*min(elem.minHeight, areaH)*/ areaH else min(elem.prefHeight, areaH)

            var x = cell.padLeft.toFloat()
            if (align and Align.right != 0) {
                x += areaW - elemW
            } else if (align and Align.left == 0) {
                x += (areaW - elemW) / 2
            }

            var y = cell.padBottom.toFloat()
            if (align and Align.top != 0) {
                y += areaH - elemH
            } else if (align and Align.bottom == 0) {
                y += (areaH - elemH) / 2
            }

            elem.setBounds(x, y, elemW, elemH)
        }
    }

    override fun draw() {
        background?.draw(x, y, width, height)
        super.draw()
    }

    override fun insert(index: Int, widget: Widget<Element>) {
        addChildAt(index, widget.value)
        cells.insert(index, LayoutCell(widget.value))
        // TODO This is somehow very important and constantly overlooked, doc this. Also why doesn't redwood do this by default?
        onModifierUpdated(index, widget)
        childrenChanged()
    }

    override fun move(fromIndex: Int, toIndex: Int, count: Int) {
        children.move(fromIndex, toIndex, count)
        cells.move(fromIndex, toIndex, count)
        invalidate()
    }

    override fun remove(index: Int, count: Int) {
        for (i in index until index + count) {
            children[i].onRemoved()
        }
        children.remove(index, count)
        cells.remove(index, count)
        childrenChanged()
    }

    override fun onModifierUpdated(index: Int, widget: Widget<Element>) {
        val cell = cells[index]

        widget.modifier.forEachScoped {
            when (it) {
                is Padding -> {
                    cell.applyPadding(it)
                    invalidateHierarchy()
                }
                is me.mars.maple.schema.modifier.Align -> {
                    cell.applyAlign(it)
                    invalidate()
                }
                is SizeIn -> {
                    cell.applySizeIn(it)
                    invalidateHierarchy()
                }
                is Fill -> {
                    cell.applyFill(it)
                    invalidate()
                }
            }
        }
        invalidateHierarchy()
    }
}