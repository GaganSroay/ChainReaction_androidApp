package com.thewizard.chainreactiononline20.utils.openGlUtils

import android.opengl.Matrix

class ProjectionMatrix(
    type: ProjectionType,
    viewWidth: Int,
    viewHeight: Int,
    near: Float,
    far: Float
) {

    private val projectionMatrix = FloatArray(16)

    init {
        val ratio = viewWidth.toFloat() / viewHeight
        val left = -ratio
        val bottom = -1.0f
        val top = 1.0f

        when (type) {
            ProjectionType.ORTHOGONAL ->
                Matrix.orthoM(projectionMatrix, 0, left, ratio, bottom, top, near, far)

            ProjectionType.PRESPECTIVE ->
                Matrix.frustumM(projectionMatrix, 0, left, ratio, bottom, top, near, far)
        }

    }

    fun get(): FloatArray {
        return projectionMatrix
    }


}