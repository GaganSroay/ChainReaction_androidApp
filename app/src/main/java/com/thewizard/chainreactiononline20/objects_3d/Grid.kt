package com.thewizard.chainreactiononline20.objects_3d

import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLES20.GL_POINTS
import android.opengl.GLES20.glDrawArrays
import android.opengl.GLES20.glEnableVertexAttribArray
import android.opengl.GLES20.glGetAttribLocation
import android.opengl.GLES20.glGetUniformLocation
import android.opengl.GLES20.glLineWidth
import android.opengl.GLES20.glUniform4fv
import android.opengl.GLES20.glUniformMatrix4fv
import android.opengl.GLES20.glUseProgram
import android.opengl.GLES20.glVertexAttribPointer
import android.opengl.Matrix
import com.thewizard.chainreactiononline20.display.color.GridColor
import com.thewizard.chainreactiononline20.display.grid.GridObjectData
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameSettings
import com.thewizard.chainreactiononline20.utils.ShaderUtil.Shader
import com.thewizard.chainreactiononline20.utils.otherUtils.BufferHelper
import java.nio.FloatBuffer

class Grid(
    val gameSettings: GameSettings
) {

    private var program = 0
    private var mvMatrixHandle = 0
    private var positionHandle = 0
    private var colorHandle = 0

    private lateinit var vBuff: FloatBuffer
    private val vpMatrix = FloatArray(16)
    private var numberOfVeritices: Int = 0

    var color = GridColor.DEFAULT_GRID

    lateinit var viewMatrix: FloatArray
    lateinit var projectionMatrix: FloatArray

    init {
        gameSettings.addListener {
            val vary = GridObjectData.calculateGridPositions(gameSettings)
            numberOfVeritices = vary.size / 3
            vBuff = BufferHelper.getFloatBuffer(vary)
            vBuff.position(0)
        }

    }

    fun addShader(shader: Shader) {
        program = shader.program

        mvMatrixHandle = glGetUniformLocation(program, U_MVPMATRIX)
        positionHandle = glGetAttribLocation(program, A_POSITION)
        colorHandle = glGetUniformLocation(program, U_COLOR)
    }

    fun addViewAndProjectionMatrix(viewMatrix: FloatArray, projectionMatrix: FloatArray) {
        this.viewMatrix = viewMatrix
        this.projectionMatrix = projectionMatrix
    }

    fun draw(color: FloatArray) {
        this.color = color
        draw()
    }


    fun draw() {
        glUseProgram(program)
        glLineWidth(2f)

        Matrix.multiplyMM(vpMatrix, 0, viewMatrix, 0, projectionMatrix, 0)
        glUniformMatrix4fv(mvMatrixHandle, 1, false, vpMatrix, 0)

        vBuff.position(0)
        glVertexAttribPointer(positionHandle, 3, GL_FLOAT, false, 0, vBuff)
        glEnableVertexAttribArray(positionHandle)

        glUniform4fv(colorHandle, 1, color, 0)

        glDrawArrays(GL_POINTS, 0, numberOfVeritices)
    }


    companion object {

        const val A_POSITION = "a_Position"
        const val U_MVPMATRIX = "u_MVPMatrix"
        const val U_COLOR = "u_Color"

        val ATTRIBUTES = arrayOf(A_POSITION, U_COLOR, U_MVPMATRIX)

    }


}