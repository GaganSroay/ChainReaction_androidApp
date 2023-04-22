package com.thewizard.chainreactiononline20.utils.openGlUtils

import android.opengl.Matrix
import com.thewizard.chainreactiononline20.utils.objUtils.Point3D

open class ModelMatrix {

    var positionMatrix = FloatArray(16)
    val rotationMatrix = FloatArray(16)
    val scaleMatrix = FloatArray(16)
    val temp = FloatArray(16)

    val modelMatrix: FloatArray get() = multiplyMatrix(positionMatrix, scaleMatrix, rotationMatrix)

    var position: Point3D = Point3D()
        set(point) {
            field = point
            Matrix.setIdentityM(positionMatrix, 0)
            translate(point.x, point.y, point.z)
        }

    init {
        Matrix.setIdentityM(scaleMatrix, 0)
        Matrix.setIdentityM(rotationMatrix, 0)
        Matrix.setIdentityM(positionMatrix, 0)
        Matrix.setIdentityM(modelMatrix, 0)
        setScale(0.45f)
    }

    open fun setScale(x: Float, y: Float, z: Float) {
        Matrix.scaleM(scaleMatrix, 0, x, y, z)
    }

    open fun translate(x: Float, y: Float, z: Float) {
        Matrix.translateM(positionMatrix, 0, x, y, z)
    }

    fun rotate(angle: Float, x: Float, y: Float, z: Float) {
        Matrix.setRotateM(rotationMatrix, 0, angle, x, y, z)
    }

    fun setPos(x: Float, y: Float, z: Float) {
        Matrix.setIdentityM(positionMatrix, 0)
        translate(x, y, z)
    }

    fun setPositionArray(position: Point3D) {
        this.position = position
        Matrix.setIdentityM(positionMatrix, 0)
        translate(position.x, position.y, position.z)
    }

    fun moveRelativeToOrigin(x: Float, y: Float, z: Float) {
        Matrix.setIdentityM(positionMatrix, 0)
        translate(position.x + x, position.y + y, position.z + z)
    }

    fun defaultPosition() = setPositionArray(position)

    fun setScale(s: Float) = setScale(s, s, s)

    fun makeCopy(): FloatArray = modelMatrix.clone()


    fun multiplyMatrix(m1: FloatArray, m2: FloatArray, m3: FloatArray): FloatArray {
        Matrix.setIdentityM(temp, 0)
        Matrix.multiplyMM(temp, 0, m1, 0, m2, 0)
        Matrix.multiplyMM(temp, 0, temp, 0, m3, 0)
        return temp
    }


}