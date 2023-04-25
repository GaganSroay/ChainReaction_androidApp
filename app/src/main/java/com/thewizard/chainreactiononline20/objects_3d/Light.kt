package com.thewizard.chainreactiononline20.objects_3d

import android.opengl.GLES20
import android.opengl.GLES20.glUniform3f
import android.opengl.Matrix
import com.thewizard.chainreactiononline20.utils.ShaderUtil.Shader

class Light {

    private var lightHandle: Int = 0
    private var program: Int = 0

    private val mLightPosInModelSpace = floatArrayOf(0.0f, 0.0f, 0.0f, -1.0f)
    private val mLightPosInWorldSpace = FloatArray(4)
    private val mLightPosInEyeSpace = FloatArray(4)
    private val mLightModelMatrix = FloatArray(16)

    fun setShader(shader: Shader) {
        program = shader.program
        lightHandle = GLES20.glGetUniformLocation(program, OBJ.U_LIGHTPOSITION)
    }

    private var z = -7.0f


    fun drawLight(mViewMatrix: FloatArray) {
        Matrix.setIdentityM(mLightModelMatrix, 0)
        Matrix.translateM(mLightModelMatrix, 0, 0.0f, 0.0f, z)
        Matrix.multiplyMV(mLightPosInWorldSpace, 0, mLightModelMatrix, 0, mLightPosInModelSpace, 0)
        Matrix.multiplyMV(mLightPosInEyeSpace, 0, mViewMatrix, 0, mLightPosInWorldSpace, 0)
        glUniform3f(
            lightHandle,
            mLightPosInEyeSpace[0],
            mLightPosInEyeSpace[1],
            mLightPosInEyeSpace[2]
        )
    }


}