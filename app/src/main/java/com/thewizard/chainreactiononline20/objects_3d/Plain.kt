package com.thewizard.chainreactiononline20.objects_3d

import android.content.Context
import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLES20.GL_TEXTURE_2D
import android.opengl.GLES20.GL_TRIANGLES
import android.opengl.GLES20.glBindTexture
import android.opengl.GLES20.glDrawArrays
import android.opengl.GLES20.glEnableVertexAttribArray
import android.opengl.GLES20.glGetAttribLocation
import android.opengl.GLES20.glGetUniformLocation
import android.opengl.GLES20.glUniform4fv
import android.opengl.GLES20.glUniformMatrix4fv
import android.opengl.GLES20.glUseProgram
import android.opengl.GLES20.glVertexAttribPointer
import com.thewizard.chainreactiononline20.R
import com.thewizard.chainreactiononline20.display.color.GridColor
import com.thewizard.chainreactiononline20.utils.ShaderUtil.Shader
import com.thewizard.chainreactiononline20.utils.objUtils.OBJloader
import com.thewizard.chainreactiononline20.utils.objUtils.TextureHelper
import com.thewizard.chainreactiononline20.utils.openGlUtils.Mat
import com.thewizard.chainreactiononline20.utils.otherUtils.BufferHelper
import java.nio.FloatBuffer


class Plain(val context: Context) {

    private var program = 0
    private var mvMatrixHandle = 0
    private var positionHandle = 0
    private var textureHandle = 0
    private var texturePositionHandle = 0
    private var colorHandle = 0

    private var vBuff: FloatBuffer
    private var tBuff: FloatBuffer

    private var numberOfVeritices: Int = 0

    var color = GridColor.DEFAULT_BACKGROUND

    lateinit var viewMatrix: Mat
    lateinit var projectionMatrix: Mat

    var posMatrix = Mat.pos(0f, 0f, -3f)
    var scaleMatrix = Mat.scale(1f, 1.3f, 0.8f)

    val modelViewProjectionMatrix: Mat get() = projectionMatrix * viewMatrix * posMatrix * scaleMatrix

    var texture = 0

    init {

        val obj = OBJloader(context, R.raw.plain_surface)
        numberOfVeritices = obj.positions.size / 3

        vBuff = BufferHelper.getFloatBuffer(obj.positions)
        tBuff = BufferHelper.getFloatBuffer(obj.textureCoordinates)

    }

    fun generateTextures() {
        texture = TextureHelper.loadTexture(context, R.raw.background_vinnete_2)
    }

    fun addShader(shader: Shader) {
        program = shader.program

        mvMatrixHandle = glGetUniformLocation(program, U_MVPMATRIX)
        positionHandle = glGetAttribLocation(program, A_POSITION)
        texturePositionHandle = glGetAttribLocation(program, A_TEXTUREPOSITION)
        colorHandle = glGetUniformLocation(program, U_COLOR)
        textureHandle = glGetUniformLocation(program, U_TEXTURE)
    }

    fun addViewAndProjectionMatrix(viewMatrix: FloatArray, projectionMatrix: FloatArray) {
        this.viewMatrix = Mat(viewMatrix)
        this.projectionMatrix = Mat(projectionMatrix)
    }


    fun draw() {
        glUseProgram(program)

        glUniformMatrix4fv(mvMatrixHandle, 1, false, modelViewProjectionMatrix.array, 0)

        vBuff.position(0)
        glVertexAttribPointer(positionHandle, 3, GL_FLOAT, false, 0, vBuff)
        glEnableVertexAttribArray(positionHandle)

        tBuff.position(0)
        glVertexAttribPointer(texturePositionHandle, 2, GL_FLOAT, false, 0, tBuff)
        glEnableVertexAttribArray(texturePositionHandle)

        glUniform4fv(colorHandle, 1, color, 0)
        glBindTexture(GL_TEXTURE_2D, texture)

        glDrawArrays(GL_TRIANGLES, 0, numberOfVeritices)
    }


    companion object {

        const val A_POSITION = "a_Position"
        const val U_MVPMATRIX = "u_MVPMatrix"
        const val U_COLOR = "u_Color"
        const val A_TEXTUREPOSITION = "a_TexCoordinate"
        const val U_TEXTURE = "u_Texture"


        val ATTRIBUTES = arrayOf(A_POSITION, U_COLOR, U_MVPMATRIX, U_TEXTURE, A_TEXTUREPOSITION)


    }
}