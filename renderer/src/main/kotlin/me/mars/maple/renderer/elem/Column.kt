package me.mars.maple.renderer.elem

import app.cash.redwood.widget.Widget
import arc.scene.Element
import arc.scene.event.Touchable
import arc.scene.style.Drawable
import arc.scene.ui.layout.WidgetGroup
import arc.struct.FloatSeq
import arc.struct.Seq
import arc.util.Align
import me.mars.maple.renderer.move
import me.mars.maple.renderer.remove
import me.mars.maple.schema.api.Arrangement
import me.mars.maple.schema.modifier.Fill
import me.mars.maple.schema.modifier.Padding
import me.mars.maple.schema.modifier.SizeIn
import me.mars.maple.schema.modifier.Weight
import kotlin.math.max
import kotlin.math.min

class Column : WidgetGroup(), ChildrenAware {
    init {
        touchable = Touchable.childrenOnly
    }

    var background: Drawable? = null

    var defaultAlign: Int = Align.center
        set(value) {
            field = value
            invalidate()
        }
    private val cells: Seq<WeightedLayoutCell> = Seq()
    private var sizeInvalid = true

    private var minWidth = 0f
    private var minHeight = 0f
    private var maxWidth = 0f
    private var maxHeight = 0f
    private var prefWidth = 0f
    private var prefHeight = 0f

    private val cellHeights = FloatSeq()
    private val outPositions = FloatSeq()
    var arrangement: Arrangement = Arrangement.Start
        set(value) {
            field = value
            invalidate()
        }

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
            minHeight += cell.getMinHeight()
            maxWidth = min(maxWidth, cell.getMaxWidth())
            maxHeight += cell.getMaxHeight()
            prefWidth = max(prefWidth, cell.getPrefWidth())
            prefHeight += cell.getPrefHeight()
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



    override fun layout() {
        if (sizeInvalid) computeSize()
        val arrangeHeight = sizeMainAxis(height, prefHeight, minHeight, cells, { getPrefHeight() }, { getMinHeight() }, cellHeights)
        // End of where the helper function would be
        outPositions.setSize(cells.size)
        arrangement.arrange(arrangeHeight, cellHeights, outPositions)
        for ((i, cell) in cells.withIndex()) {
            val elem = cell.elem
            val align = cell.align

            // How much free space is left
            val areaW = width - cell.padLeft - cell.padRight
            val areaH = cellHeights[i] - cell.padTop - cell.padBottom
            // How much space the element is able to take up
            val elemW = if (cell.fillX) areaW else min(elem.prefWidth, areaW)
            val elemH = if (cell.fillY) areaH else min(elem.prefHeight, areaH)
            // TODO Not sure if x-axis alignment is even still required or would make sense?
            var x = cell.padLeft.toFloat()
            if (align and Align.right != 0) {
                x += areaW - elemW
            } else if (align and Align.left == 0) {
                x += (areaW - elemW) / 2
            }
            // To mimic compose style columns, we need to arrange top down.
            var y = arrangeHeight - outPositions[i] - cellHeights[i] + cell.padBottom.toFloat()
            if (align and Align.top != 0) {
                y += areaH - elemH
            } else if (align and Align.bottom == 0) {
                y += (areaH - elemH) / 2
            }
            // TODO Should we make the Cell responsible for calling setBounds? The alignment logic is somewhat shared after all.
            elem.setBounds(x, y, elemW, elemH)
        }
    }

    override fun draw() {
        validate()
        background?.draw(x, y, width, height)
        super.draw()
    }

    override fun insert(index: Int, widget: Widget<Element>) {
        addChildAt(index, widget.value)
        cells.insert(index, WeightedLayoutCell(widget.value).apply { align = defaultAlign })
        onModifierUpdated(index, widget)
        childrenChanged()
    }

    override fun move(fromIndex: Int, toIndex: Int, count: Int) {
        children.move(fromIndex, toIndex, count)
        cells.move(fromIndex, toIndex, count)
        childrenChanged()
        //
    }

    override fun remove(index: Int, count: Int) {
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
                is Fill -> {
                    cell.applyFill(it)
                    invalidate()
                }
                is SizeIn -> {
                    cell.applySizeIn(it)
                    invalidateHierarchy()
                }
                is Weight -> {
                    cell.applyWeight(it)
                    invalidate()
                }
            }
        }
    }

}