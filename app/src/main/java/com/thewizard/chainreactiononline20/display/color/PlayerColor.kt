package com.thewizard.chainreactiononline20.display.color

object PlayerColor {
    fun getColor(color: BasicColor): FloatArray {
        return when (color) {
            BasicColor.RED -> floatArrayOf(1f, 0f, 0f, 1f)
            BasicColor.GREEN -> floatArrayOf(0f, 1f, 0f, 1f)
            BasicColor.BLUE -> floatArrayOf(0.2f, 0.2f, 1f, 1f)
            BasicColor.YELLOW -> floatArrayOf(1f, 1f, 0f, 1f)
            BasicColor.MAGENTA -> floatArrayOf(1f, 0f, 1f, 1f)
            BasicColor.CYAN -> floatArrayOf(0f, 1f, 1f, 1f)
            BasicColor.WHITE -> floatArrayOf(1f, 1f, 1f, 1f)
            BasicColor.ORANGE -> floatArrayOf(1f, 0.5f, 0.05f, 1f)
            else -> floatArrayOf(0f, 0f, 0f, 1f)
        }
    }

    fun getColorName(index: Int): BasicColor {
        return when (index) {
            0 -> BasicColor.RED
            1 -> BasicColor.GREEN
            2 -> BasicColor.BLUE
            3 -> BasicColor.YELLOW
            4 -> BasicColor.MAGENTA
            5 -> BasicColor.CYAN
            6 -> BasicColor.WHITE
            7 -> BasicColor.ORANGE
            else -> BasicColor.EMPTY
        }
    }

    val DEFAULT = getColor(BasicColor.RED)


}