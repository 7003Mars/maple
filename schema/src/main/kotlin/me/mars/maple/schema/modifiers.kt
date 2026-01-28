package me.mars.maple.schema

import app.cash.redwood.schema.Modifier

@Modifier(1, BoxScope::class, RowScope::class, ColumnScope::class)
data class Padding(val left: Int = 0, val top: Int = 0, val right: Int = 0, val bottom: Int = 0)

@Modifier(2, BoxScope::class, RowScope::class, ColumnScope::class)
data class Align(val align: Int)

@Modifier(3, BoxScope::class, RowScope::class, ColumnScope::class)
data class Fill(val fillX: Boolean = false, val fillY: Boolean = false)

@Modifier(4, BoxScope::class, RowScope::class, ColumnScope::class)
data class SizeIn(val minWidth: Float = 0f, val minHeight: Float = 0f, val maxWidth: Float = Float.MAX_VALUE, val maxHeight: Float = Float.MAX_VALUE)

@Modifier(5, RowScope::class, ColumnScope::class)
data class Weight(val weight: Float)
