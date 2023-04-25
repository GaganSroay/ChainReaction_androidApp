package com.thewizard.chainreactiononline20.utils.openGlUtils

import android.opengl.Matrix

class ViewMatrix : Mat() {

    init {
        update()
    }

    override var array: FloatArray
        get() = super.array
        set(value) {
            super.array = value
        }

    fun eye(x: Float, y: Float, z: Float) {
        eyeX = x
        eyeY = y
        eyeZ = z
        update()
    }

    fun look(x: Float, y: Float, z: Float) {
        lookX = x
        lookY = y
        lookZ = z
        update()
    }

    fun up(x: Float = 0f, y: Float = 1f, z: Float = 0f) {
        upX = x
        upY = y
        upZ = z
        update()
    }

    fun update() = Matrix.setLookAtM(array, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ)

    private var eyeX = 0.0f
    private var eyeY = 0.0f
    private var eyeZ = -0.5f
    private var lookX = 0.0f
    private var lookY = 0.0f
    private var lookZ = -5.0f
    private var upX = 0.0f
    private var upY = 1.0f
    private var upZ = 0.0f
}