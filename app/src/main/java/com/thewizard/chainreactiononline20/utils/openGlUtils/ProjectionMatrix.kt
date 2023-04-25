package com.thewizard.chainreactiononline20.utils.openGlUtils

import android.opengl.Matrix

class ProjectionMatrix(
    type: ProjectionType,
    viewWidth: Int,
    viewHeight: Int,
    near: Float,
    far: Float
) : Mat() {

    override var array: FloatArray
        get() = super.array
        set(value) {
            super.array = value
        }

    init {
        val ratio = viewWidth.toFloat() / viewHeight
        val left = -ratio
        val bottom = -1.0f
        val top = 1.0f

        when (type) {
            ProjectionType.ORTHOGONAL ->
                Matrix.orthoM(array, 0, left, ratio, bottom, top, near, far)

            ProjectionType.PRESPECTIVE ->
                Matrix.frustumM(array, 0, left, ratio, bottom, top, near, far)
        }

    }



}