package com.thewizard.chainreactiononline20.utils.ShaderUtil

import android.opengl.GLES20

class ShaderLocations(
    val program: Int
) {
    val String.attribute get() = GLES20.glGetAttribLocation(program, this)
    val String.uniform get() = GLES20.glGetUniformLocation(program, this)

}