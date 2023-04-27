package com.thewizard.chainreactiononline20.utils.openGlUtils

import android.opengl.GLES20.GL_COLOR_BUFFER_BIT
import android.opengl.GLES20.GL_CULL_FACE
import android.opengl.GLES20.GL_DEPTH_BUFFER_BIT
import android.opengl.GLES20.GL_DEPTH_TEST
import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLES20.GL_POINTS
import android.opengl.GLES20.GL_TRIANGLES
import android.opengl.GLES20.glClear
import android.opengl.GLES20.glClearColor
import android.opengl.GLES20.glDrawArrays
import android.opengl.GLES20.glEnable
import android.opengl.GLES20.glEnableVertexAttribArray
import android.opengl.GLES20.glGetAttribLocation
import android.opengl.GLES20.glGetUniformLocation
import android.opengl.GLES20.glLineWidth
import android.opengl.GLES20.glUniform4fv
import android.opengl.GLES20.glUniformMatrix4fv
import android.opengl.GLES20.glUseProgram
import android.opengl.GLES20.glVertexAttribPointer
import java.nio.FloatBuffer

open class OpenGL : ModelMatrix() {

    var program = 0

    var clearColor: FloatArray = FloatArray(4)
        set(value) {
            glClearColor(value[0], value[1], value[2], value[3])
            field = value
        }

    fun clear() = glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    fun clear(color: FloatArray) {
        clearColor = color
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    fun String.setArray(array: FloatArray) = uniformArray(array, this.handle)
    fun String.setMatrix(matrix: FloatArray) = uniformMatrix(matrix, this.handle)
    fun String.setMatrix(matrix: Mat) = uniformMatrix(matrix, this.handle)
    fun String.setBuffer(buff: FloatBuffer, size: Int) = useBuffer(buff, size, this.handle)

    fun Int.setArray(array: FloatArray) = uniformArray(array, this)
    fun Int.setMatrix(matrix: FloatArray) = uniformMatrix(matrix, this)
    fun Int.setMatrix(matrix: Mat) = uniformMatrix(matrix, this)
    fun Int.setBuffer(buff: FloatBuffer, size: Int) = useBuffer(buff, size, this)


    fun useColor(color: FloatArray, colorHandle: Int) = glUniform4fv(colorHandle, 1, color, 0)
    fun useMatrix(matrix: FloatArray, handle: Int) = glUniformMatrix4fv(handle, 1, false, matrix, 0)

    var lineWidth: Float = 0f
        set(value) {
            glLineWidth(value)
        }

    fun useProgram(program: Int) {
        glUseProgram(program)
    }

    fun Int.use() = glUseProgram(this)

    fun uniformArray(array: FloatArray, handle: Int) = glUniform4fv(handle, 1, array, 0)
    fun uniformMatrix(matrix: FloatArray, handle: Int) =
        glUniformMatrix4fv(handle, 1, false, matrix, 0)

    fun uniformMatrix(matrix: Mat, handle: Int) =
        glUniformMatrix4fv(handle, 1, false, matrix.array, 0)

    fun useBuffer(buff: FloatBuffer, size: Int, handle: Int) {
        buff.position(0)
        glVertexAttribPointer(handle, size, GL_FLOAT, false, 0, buff)
        glEnableVertexAttribArray(handle)
    }

    fun drawPoints(numberOfVeritices: Int) = glDrawArrays(GL_POINTS, 0, numberOfVeritices)
    fun drawTriangles(numberOfVeritices: Int) = glDrawArrays(GL_TRIANGLES, 0, numberOfVeritices)

    val String.handle get() = if (this.attribute != -1) this.attribute else this.uniform
    val String.attribute get() = glGetAttribLocation(program, this)
    val String.uniform get() = glGetUniformLocation(program, this)

    fun enableCullFace() = glEnable(GL_CULL_FACE)
    fun enableDepthText() = glEnable(GL_DEPTH_TEST)

}