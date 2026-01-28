package me.mars.maple.renderer

import arc.struct.Seq

// https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/runtime/runtime/src/commonMain/kotlin/androidx/compose/runtime/Applier.kt

fun <T> Seq<T>.move(from: Int, to: Int, count: Int) {
    val dest = if (from > to) to else to - count
    if (count == 1) {
        if (from == to + 1 || from == to - 1) {
            // Adjacent elements, perform swap to avoid backing array manipulations.
            swap(from, to)
        } else {
            val fromEl = remove(from)
            insert(dest, fromEl)
        }
    } else {
        // TODO Ai generated, verify
        val copy = items.copyOfRange(from, from + count)
        if (from < to) {
            // Moving forward: shift elements between from+count and to+count to the left
            System.arraycopy(items, from + count, items, from, to - from)
        } else {
            // Moving backward: shift elements between to and from to the right
            System.arraycopy(items, to, items, to + count, from - to)
        }

        // 5. Place the temp elements into the destination
        System.arraycopy(copy, 0, items, to, count)
    }
}

fun <T> Seq<T>.remove(index: Int, count: Int) {
    removeRange(index, index+count-1)
}