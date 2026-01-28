package me.mars.maple.renderer.elem

import arc.struct.FloatSeq
import arc.struct.Seq

/**
 * Used to determine the main axis sizes of cells for a row or column.
 * The resultant cell sizes are stored in [cellSizes]
 * @param curSize The current main axis size of the Row/Column, as set by its parent
 * @param prefSize The sum of preferred sizes of the cells
 * @param minSize The sum of minimum sizes of the cells
 * @param cellPrefSize The main axis preferred size of a cell
 * @param cellMinsize The main axis min size of a cell
 * @return The amount of space used for all cells. May be greater than [curSize]
 */
fun sizeMainAxis(
    curSize: Float, prefSize: Float, minSize: Float,
    cells: Seq<WeightedLayoutCell>, cellPrefSize: WeightedLayoutCell.() -> Float, cellMinsize: WeightedLayoutCell.() -> Float, cellSizes: FloatSeq
): Float {
    cellSizes.setSize(cells.size) // TODO Should the caller be responsible for setting this size?
    for (i in 0 until cells.size) {
        cellSizes[i] = cells[i].cellPrefSize()
    }
    if (curSize < prefSize) {
        // We have to shrink cells down as they exceed the row/column's given width/height. Weights are useless in this case.
        // How much we can shrink by in total
        val totalShrinkable = prefSize - minSize // TODO We assume those are the sum of pref and min cell widths for now, might have to introduce new variables for spacing later
        // If we are unable to shrink anything, just keep sizes as they are and return curSize.
        // TODO We might need to consider edge cases where a cell improperly reports a minSize more than prefSize. This is UB and we may want a debug flag that validates all cells.
        if (totalShrinkable <= 0) {
            return curSize
        }
        // How much we need to shrink by
        val totalRequiredShrink = prefSize - curSize
        for (i in 0 until cells.size) {
            val cell = cells[i]
            cellSizes.incr(i, -totalRequiredShrink * (cell.cellPrefSize() - cell.cellMinsize())/totalShrinkable)
        }
        return minSize
    } else {
        // We have extra free space, we can distribute extra space to weighted cells.
        val totalExpandable = curSize - prefSize
        val totalWeight = cells.sumf(WeightedLayoutCell::weight)
        if (totalWeight > 0f) {
            for (i in 0 until cells.size) {
                cellSizes.incr(i, totalExpandable * cells.get(i).weight/totalWeight)
            }
        }
        return curSize
    }
}