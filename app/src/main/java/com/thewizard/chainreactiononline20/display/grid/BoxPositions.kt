package com.thewizard.chainreactiononline20.display.grid

import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameSettings
import com.thewizard.chainreactiononline20.utils.objUtils.Point3D

class BoxPositions(gameSettings: GameSettings) {

    var data = calculateSpherePositions(gameSettings.rows, gameSettings.cols)

    init {
        gameSettings.addListener {
            data = calculateSpherePositions(gameSettings.rows, gameSettings.cols)
        }
    }

    companion object {

        val sphereGridWidth = 1.39f

        fun calculateSpherePositions(
            numberOfRows: Int,
            numberOfColumns: Int
        ): Array<Array<Point3D>> {

            val sphereGridWidth = 1.39f

            val startZ = -2f
            val spherePositions = Array(numberOfRows) { Array(numberOfColumns) { Point3D() } }
            val startX = -sphereGridWidth / 2
            val startY = (numberOfRows * sphereGridWidth) / (numberOfColumns * 2)
            val boxSize = sphereGridWidth / numberOfColumns
            val halfSize = boxSize / 2f

            val bx = startX + halfSize
            val by = startY - halfSize
            val bz = startZ - halfSize

            for (i in 0 until numberOfRows)
                for (j in 0 until numberOfColumns)
                    spherePositions[i][j] = Point3D(bx + j * boxSize, by - i * boxSize, bz)

            return spherePositions
        }
    }
}