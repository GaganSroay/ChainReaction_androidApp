package com.thewizard.chainreactiononline20.display.color

object GridColor {
    fun getColor(color: BasicColor): FloatArray {
        return when (color) {
            BasicColor.RED -> floatArrayOf(1.0f, 0.0f, 0.0f, 1f)
            BasicColor.GREEN -> floatArrayOf(0.0f, 1.0f, 0.0f, 1f)
            BasicColor.BLUE -> floatArrayOf(0.2f, 0.2f, 1.0f, 1f)
            BasicColor.YELLOW -> floatArrayOf(1.0f, 1.0f, 0.0f, 1f)
            BasicColor.MAGENTA -> floatArrayOf(1.0f, 0.0f, 1.0f, 1f)
            BasicColor.CYAN -> floatArrayOf(0.0f, 1.0f, 1.0f, 1f)
            BasicColor.WHITE -> floatArrayOf(1.0f, 1.0f, 1.0f, 1f)
            BasicColor.ORANGE -> floatArrayOf(1.0f, 0.5f, 0.05f, 1f)
            else -> floatArrayOf(0.0f, 0.0f, 0.0f, 1f)
        }
    }

    fun getBackgroundColor(color: BasicColor): FloatArray {
        return when (color) {
            BasicColor.RED -> floatArrayOf(0.08f, 0.02f, 0.02f, 1f)
            BasicColor.GREEN -> floatArrayOf(0.02f, 0.08f, 0.02f, 1f)
            BasicColor.BLUE -> floatArrayOf(0.02f, 0.02f, 0.15f, 1f)
            BasicColor.YELLOW -> floatArrayOf(0.08f, 0.08f, 0.02f, 1f)
            BasicColor.MAGENTA -> floatArrayOf(0.08f, 0.02f, 0.08f, 1f)
            BasicColor.CYAN -> floatArrayOf(0.02f, 0.08f, 0.08f, 1f)
            BasicColor.WHITE -> floatArrayOf(0.04f, 0.04f, 0.04f, 1f)
            BasicColor.ORANGE -> floatArrayOf(0.08f, 0.05f, 0.01f, 1f)
            else -> floatArrayOf(0f, 0f, 0f, 1f)
        }
    }

    val DEFAULT_GRID = getColor(BasicColor.RED)
    val DEFAULT_BACKGROUND = getBackgroundColor(BasicColor.RED)

}