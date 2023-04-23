package com.thewizard.chainreactiononline20.utils.openGlUtils

import android.opengl.Matrix.multiplyMM
import android.opengl.Matrix.rotateM
import android.opengl.Matrix.scaleM
import android.opengl.Matrix.setIdentityM
import android.opengl.Matrix.translateM

class Mat {
    var array = FloatArray(16)

    constructor() {
        setIdentity(array)
    }

    constructor(array: FloatArray) {
        this.array = array
    }

    fun setIdentity() {
        setIdentityM(array, 0)
    }

    fun setIdentity(array: FloatArray) {
        setIdentityM(array, 0)
    }

    fun translate(x: Float, y: Float, z: Float): Mat {
        translateM(array, 0, x, y, z)
        return this
    }

    fun pos(x: Float, y: Float, z: Float): Mat {
        setIdentity()
        translate(x, y, z)
        return this
    }

    fun scale(scale: Float): Mat {
        scaleM(array, 0, scale, scale, scale)
        return this
    }

    fun scale(x: Float, y: Float, z: Float): Mat {
        scaleM(array, 0, x, y, z)
        return this
    }

    fun angle(angle: Float, x: Float, y: Float, z: Float): Mat {
        rotateM(array, 0, angle, x, y, z)
        return this
    }

    companion object {
        fun setIdentity(array: FloatArray) = setIdentityM(array, 0)

        fun pos(x: Float, y: Float, z: Float): Mat = Mat().translate(x, y, z)
        fun scale(scale: Float): Mat = Mat().scale(scale)
        fun scale(x: Float, y: Float, z: Float): Mat = Mat().scale(x, y, z)
        fun angle(angle: Float, x: Float, y: Float, z: Float): Mat = Mat().angle(angle, x, y, z)
    }


    operator fun times(mat: Mat): Mat {
        val result = FloatArray(16)
        multiplyMM(result, 0, array, 0, mat.array, 0)
        return Mat(result)
    }

}