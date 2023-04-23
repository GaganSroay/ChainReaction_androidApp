package com.thewizard.chainreactiononline20.display.grid

import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameSettings
import java.util.Vector

class GridObjectData {
    companion object {
        fun calculateGridPositions(gameSettings: GameSettings): FloatArray {
            val rows = gameSettings.rows
            val cols = gameSettings.cols

            val vlist = Vector<Float>()
            val gridWidth = 1f
            val startX = -gridWidth / 2
            val startZ = -1.1f

            val startY = (rows * gridWidth) / (cols * 2)

            val bs = gridWidth / cols

            val newZ = bs * 0.5f
            val endZ = startZ - newZ

            for (i in 0..cols) {
                val x = startX + i * bs
                for (j in 0..rows) {
                    val y = startY - j * bs
                    if (j < rows) {
                        addPoint(
                            vlist,
                            x, y, startZ,
                            x, y - bs, startZ
                        )
                        addPoint(
                            vlist,
                            x, y, endZ,
                            x, y - bs, endZ
                        )
                    }

                    addPoint(
                        vlist,
                        x, y, startZ,
                        x, y, startZ - newZ,
                    )
                }

            }

            for (i in 0..rows) {
                val y = startY - i * bs
                for (j in 0 until cols) {
                    val x = startX + j * bs
                    addPoint(
                        vlist,
                        x, y, startZ,
                        x + bs, y, startZ
                    )
                    addPoint(
                        vlist,
                        x, y, endZ,
                        x + bs, y, endZ
                    )
                }
            }
            return vlist.toFloatArray()
        }

        fun addPoint(
            list: Vector<Float>,
            x1: Float, y1: Float, z1: Float,
            x2: Float, y2: Float, z2: Float
        ) {
            val sections = 30
            var px = x1
            var py = y1
            var pz = z1

            for (i in 0..sections) {
                val m: Float = i.toFloat() / sections.toFloat()
                val x = sectionFormula(x1, x2, m)
                val y = sectionFormula(y1, y2, m)
                val z = sectionFormula(z1, z2, m)

                if (i % 2 == 1) {
                    list.add(x)
                    list.add(y)
                    list.add(z)

                    list.add(px)
                    list.add(py)
                    list.add(pz)
                }

                px = x
                py = y
                pz = z
            }

        }

        fun sectionFormula(p1: Float, p2: Float, m: Float): Float {
            val n = 1f - m
            return (m * p2 + n * p1) / (n + m)
        }
    }
}