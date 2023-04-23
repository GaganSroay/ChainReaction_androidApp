package com.thewizard.chainreactiononline20.display.color

import android.graphics.Color

object ColorArray {
    fun toColor(array: FloatArray): Int = Color.rgb(
        (array[0] * 255).toInt(),
        (array[1] * 255).toInt(),
        (array[2] * 255).toInt()
    )
}