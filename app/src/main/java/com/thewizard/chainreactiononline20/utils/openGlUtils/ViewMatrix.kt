package com.thewizard.chainreactiononline20.utils.openGlUtils

import android.opengl.Matrix

class ViewMatrix {

    private var viewMatrix = FloatArray(16)
    private var changed = true

    fun eye(x: Float, y: Float, z: Float) {
        eyeX = x
        eyeY = y
        eyeZ = z
        changed = true;
    }

    fun look(x: Float, y: Float, z: Float) {
        lookX = x
        lookY = y
        lookZ = z
        changed = true;
    }

    fun up(x: Float, y: Float, z: Float) {
        upX = x
        upY = y
        upZ = z
        changed = true;
    }

    fun get(): FloatArray {
        if (changed) {
            Matrix.setLookAtM(viewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ)
            changed = false
        }
        return viewMatrix;
    }

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