package com.thewizard.chainreactiononline20.display.color

class GridColor {
    companion object {
        fun getColor(color: BasicColor): FloatArray {
            return when (color) {
                BasicColor.RED -> floatArrayOf(1f, 0f, 0f, 1f)
                BasicColor.GREEN -> floatArrayOf(0f, 1f, 0f, 1f)
                BasicColor.BLUE -> floatArrayOf(0f, 0f, 1f, 1f)
                BasicColor.YELLOW -> floatArrayOf(1f, 1f, 0f, 1f)
                BasicColor.MAGENTA -> floatArrayOf(1f, 0f, 1f, 1f)
                BasicColor.CYAN -> floatArrayOf(0f, 1f, 1f, 1f)
                BasicColor.WHITE -> floatArrayOf(1f, 1f, 1f, 1f)
                BasicColor.ORANGE -> floatArrayOf(1f, 0.5f, 0.05f, 1f)
                else -> floatArrayOf(0f, 0f, 0f, 1f)
            }
        }

        fun getBackgroundColor(color: BasicColor): FloatArray {
            return when (color) {
                BasicColor.RED -> floatArrayOf(0.08f, 0.05f, 0.05f, 1f)
                BasicColor.GREEN -> floatArrayOf(0.05f, 0.08f, 0.05f, 1f)
                BasicColor.BLUE -> floatArrayOf(0.05f, 0.05f, 0.08f, 1f)
                BasicColor.YELLOW -> floatArrayOf(1f, 1f, 0f, 1f)
                BasicColor.MAGENTA -> floatArrayOf(1f, 0f, 1f, 1f)
                BasicColor.CYAN -> floatArrayOf(0f, 1f, 1f, 1f)
                BasicColor.WHITE -> floatArrayOf(1f, 1f, 1f, 1f)
                BasicColor.ORANGE -> floatArrayOf(1f, 0.5f, 0.05f, 1f)
                else -> floatArrayOf(0f, 0f, 0f, 1f)
            }
        }
    }
}