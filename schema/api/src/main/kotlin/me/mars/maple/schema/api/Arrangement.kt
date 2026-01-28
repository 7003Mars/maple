package me.mars.maple.schema.api

import arc.struct.FloatSeq

// Code structure was copied from https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/foundation/foundation-layout/src/commonMain/kotlin/androidx/compose/foundation/layout/Arrangement.kt
// We don't need LTR support so just unify both Vertical and Horizontal
interface Arrangement {
    // TODO We can mimic spacing with padding, maybe not so important rn
//    val spacing: Int get() = 0

    fun arrange(totalSize: Float, sizes: FloatSeq, outPositions: FloatSeq)

    companion object {
        val Start: Arrangement = object : Arrangement {
            override fun arrange(totalSize: Float, sizes: FloatSeq, outPositions: FloatSeq) {
                var pos = 0f
                for (i in 0 until sizes.size) {
                    val size = sizes[i]
                    outPositions[i] = pos
                    pos += size
                }
            }
        }

        val End: Arrangement = object : Arrangement {
            override fun arrange(totalSize: Float, sizes: FloatSeq, outPositions: FloatSeq) {
                var pos = totalSize
                for (i in sizes.size-1 downTo 0) {
                    val size = sizes[i]
                    pos -= size
                    outPositions[i] = pos
                }
            }
        }

        val Center: Arrangement = object : Arrangement {
            override fun arrange(totalSize: Float, sizes: FloatSeq, outPositions: FloatSeq) {
                var pos: Float = totalSize/2 - sizes.sum()/2
                for (i in 0 until sizes.size) {
                    val size = sizes[i]
                    outPositions[i] = pos
                    pos += size
                }
            }
        }

        val SpaceAround: Arrangement = object : Arrangement {
            override fun arrange(totalSize: Float, sizes: FloatSeq, outPositions: FloatSeq) {
                val freeSpace = totalSize - sizes.sum()
                val spacing = freeSpace / (sizes.size-1)
                var pos = 0f
                for (i in 0 until sizes.size) {
                    val size = sizes[i]
                    outPositions[i] = pos
                    pos += size + spacing
                }
            }
        }

        val SpaceBetween: Arrangement = object : Arrangement {
            override fun arrange(totalSize: Float, sizes: FloatSeq, outPositions: FloatSeq) {
                val freeSpace = totalSize - sizes.sum()
                val spacing = freeSpace / (sizes.size*2)
                var pos = 0f
                for (i in 0 until sizes.size) {
                    val size = sizes[i]
                    pos += spacing
                    outPositions[i] = pos
                    pos += size + spacing
                }
            }
        }

        val SpaceEvenly: Arrangement = object : Arrangement {
            override fun arrange(totalSize: Float, sizes: FloatSeq, outPositions: FloatSeq) {
                val freeSpace = totalSize - sizes.sum()
                val spacing = freeSpace / (sizes.size+1)
                var pos = spacing
                for (i in 0 until sizes.size) {
                    val size = sizes[i]
                    outPositions[i] = pos
                    pos += size + spacing
                }
            }
        }
    }
}