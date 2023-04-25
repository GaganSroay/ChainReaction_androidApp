package com.thewizard.chainreactiononline20.utils.openGlUtils

import android.opengl.Matrix.multiplyMM
import android.opengl.Matrix.rotateM
import android.opengl.Matrix.scaleM
import android.opengl.Matrix.setIdentityM
import android.opengl.Matrix.translateM
import com.thewizard.chainreactiononline20.utils.objUtils.Point3D
import com.thewizard.chainreactiononline20.utils.objUtils.Vec3

open class Mat {

    open var array = FloatArray(16)

    constructor() {
        setIdentity(array)
    }

    constructor(array: FloatArray) {
        this.array = array
    }

    fun setIdentity() = setIdentityM(array, 0)
    fun setIdentity(array: FloatArray) = setIdentityM(array, 0)

    fun pos(point: Point3D): Mat {
        pos(point.x, point.y, point.z)
        return this
    }

    fun angle(angle: Float, direction: Vec3): Mat {
        angle(angle, direction.i, direction.j, direction.k)
        return this
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
        scale(scale, scale, scale)
        return this
    }

    fun scale(x: Float, y: Float, z: Float): Mat {
        setIdentity()
        scaleM(array, 0, x, y, z)
        return this
    }

    fun angle(angle: Float, x: Float, y: Float, z: Float): Mat {
        setIdentity()
        rotateM(array, 0, angle, x, y, z)
        return this
    }

    companion object {
        fun setIdentity(array: FloatArray) = setIdentityM(array, 0)
        fun pos(x: Float, y: Float, z: Float): Mat = Mat().translate(x, y, z)
        fun pos(point: Point3D): Mat = Mat().pos(point)

        fun scale(scale: Float): Mat = Mat().scale(scale)
        fun scale(x: Float, y: Float, z: Float): Mat = Mat().scale(x, y, z)
        fun angle(angle: Float, x: Float, y: Float, z: Float): Mat = Mat().angle(angle, x, y, z)
    }


    operator fun times(mat: Mat): Mat {
        val result = FloatArray(16)
        multiplyMM(result, 0, array, 0, mat.array, 0)
        return Mat(result)
    }

    operator fun times(array: FloatArray): FloatArray {
        val result = FloatArray(16)
        multiplyMM(result, 0, this.array, 0, array, 0)
        return result
    }

}