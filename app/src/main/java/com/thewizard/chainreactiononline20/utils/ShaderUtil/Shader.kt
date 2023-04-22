package com.thewizard.chainreactiononline20.utils.ShaderUtil

import android.content.Context
import android.opengl.GLES20
import android.util.Log

class Shader {
    var program = 0
    private var vertexShaderHandle = 0
    private var fragmentShaderHandle = 0

    constructor(vertexShader: String, fragmentShader: String) {
        vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader)
        fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader)
    }

    constructor(context: Context, verShaderRes: Int, fragShaderRes: Int) {
        val vertexShader = TextResourceReader.readTextFileFromResource(context, verShaderRes)
        val fragmentShader = TextResourceReader.readTextFileFromResource(context, fragShaderRes)
        vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader)
        fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader)
    }

    fun createProgram(arrtibutes: Array<String>): Shader {
        program = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle, arrtibutes)
        return this
    }

    companion object {
        private const val TAG = "ShaderHelper"
        fun compileShader(shaderType: Int, shaderSource: String): Int {
            var shaderHandle = GLES20.glCreateShader(shaderType)
            if (shaderHandle != 0) {
                GLES20.glShaderSource(shaderHandle, shaderSource)
                GLES20.glCompileShader(shaderHandle)
                val compileStatus = IntArray(1)
                GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0)
                if (compileStatus[0] == 0) {
                    Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle))
                    GLES20.glDeleteShader(shaderHandle)
                    shaderHandle = 0
                }
            }
            if (shaderHandle == 0) {
                throw RuntimeException("Error creating shader.")
            }
            return shaderHandle
        }

        fun createAndLinkProgram(
            vertexShaderHandle: Int,
            fragmentShaderHandle: Int,
            attributes: Array<String>
        ): Int {
            var programHandle = GLES20.glCreateProgram()
            if (programHandle != 0) {
                GLES20.glAttachShader(programHandle, vertexShaderHandle)
                GLES20.glAttachShader(programHandle, fragmentShaderHandle)
                val size = attributes.size
                for (i in 0 until size)
                    GLES20.glBindAttribLocation(programHandle, i, attributes[i])

                GLES20.glLinkProgram(programHandle)
                val linkStatus = IntArray(1)
                GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0)
                if (linkStatus[0] == 0) {
                    Log.e(
                        TAG,
                        "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle)
                    )
                    GLES20.glDeleteProgram(programHandle)
                    programHandle = 0
                }
            }
            if (programHandle == 0) {
                throw RuntimeException("Error creating program.")
            }
            return programHandle
        }
    }
}