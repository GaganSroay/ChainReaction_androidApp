package com.thewizard.chainreactiononline20.utils.objUtils

object MyColorArray {
    fun generateUniformColorArray(
        numberOfVertices: Int,
        r: Float, g: Float, b: Float, alpha: Float
    ): FloatArray {
        val colArray = FloatArray(numberOfVertices * 4);
        for (i in colArray.indices step 4) {
            colArray[i] = r
            colArray[i + 1] = g
            colArray[i + 2] = b
            colArray[i + 3] = alpha
        }
        return colArray
    }

    fun generateAlternateColorsArrayForMe(
        numberOfVertices: Int,
        r: Float, g: Float, b: Float,
        l: Float, m: Float, n: Float
    ): FloatArray {
        val colArray = FloatArray(numberOfVertices * 4);
        var p = 1
        var q = true;
        for (i in colArray.indices step 4) {

            if (p > 1) {
                q = !q
                p = 0
            }

            if (q) {
                colArray[i] = r
                colArray[i + 1] = g
                colArray[i + 2] = b
                colArray[i + 3] = 1.0f
            } else {
                colArray[i] = l
                colArray[i + 1] = m
                colArray[i + 2] = n
                colArray[i + 3] = 1.0f
            }
            p++


        }
        return colArray
    }
}