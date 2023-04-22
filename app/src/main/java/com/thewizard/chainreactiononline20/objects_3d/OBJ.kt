package com.thewizard.chainreactiononline20.objects_3d

import android.content.Context
import android.opengl.GLES20.GL_FLOAT
import android.opengl.GLES20.GL_TRIANGLES
import android.opengl.GLES20.glDrawArrays
import android.opengl.GLES20.glEnableVertexAttribArray
import android.opengl.GLES20.glGetAttribLocation
import android.opengl.GLES20.glGetUniformLocation
import android.opengl.GLES20.glUniform4fv
import android.opengl.GLES20.glUniformMatrix4fv
import android.opengl.GLES20.glUseProgram
import android.opengl.GLES20.glVertexAttribPointer
import android.opengl.Matrix
import com.thewizard.chainreactiononline20.utils.ShaderUtil.Shader
import com.thewizard.chainreactiononline20.utils.objUtils.OBJloader
import com.thewizard.chainreactiononline20.utils.openGlUtils.ModelMatrix
import com.thewizard.chainreactiononline20.utils.otherUtils.BufferHelper
import java.nio.FloatBuffer

open class OBJ(context: Context, res: Int) : ModelMatrix() {

    var program = 0

    var vBuff: FloatBuffer
    var nBuff: FloatBuffer

    var vNum = 0

    var scratch = FloatArray(16)

    var positionHandle: Int = 0
    var colorHandle: Int = 0
    var normalHandle: Int = 0
    var textureHandle: Int = 0
    var MVP_matrixHandle: Int = 0
    var MVM_matrixHandle: Int = 0

    var color = floatArrayOf(1.0f, 0.0f, 0f, 1f)

    lateinit var viewMatrix: FloatArray
    lateinit var projectionMatrix: FloatArray


    init {

        val objLoader = OBJloader(context, res)

        vNum = objLoader.positions.size / 3
        vBuff = BufferHelper.getFloatBuffer(objLoader.positions)
        nBuff = BufferHelper.getFloatBuffer(objLoader.normals)

    }

    fun setShader(shader: Shader) {
        program = shader.program
        positionHandle = glGetAttribLocation(program, A_POSITION)
        colorHandle = glGetUniformLocation(program, A_COLOR)
        normalHandle = glGetAttribLocation(program, A_NORMAL)
        textureHandle = glGetAttribLocation(program, V_UV)

        MVP_matrixHandle = glGetUniformLocation(program, U_MVPMATRIX)
        MVM_matrixHandle = glGetUniformLocation(program, U_MVMMATRIX)

    }


    fun addViewAndProjectionMatrix(viewMatrix: FloatArray, projectionMatrix: FloatArray) {
        this.viewMatrix = viewMatrix
        this.projectionMatrix = projectionMatrix
    }


    open fun draw(modelMatrix: FloatArray) {

        glUseProgram(program)

        Matrix.multiplyMM(scratch, 0, viewMatrix, 0, modelMatrix, 0)
        glUniformMatrix4fv(MVM_matrixHandle, 1, false, scratch, 0)
        Matrix.multiplyMM(scratch, 0, projectionMatrix, 0, scratch, 0)
        glUniformMatrix4fv(MVP_matrixHandle, 1, false, scratch, 0)

        vBuff.position(0)
        glVertexAttribPointer(positionHandle, 3, GL_FLOAT, false, 0, vBuff)
        glEnableVertexAttribArray(positionHandle)

        glUniform4fv(colorHandle, 1, color, 0);

        nBuff.position(0)
        glVertexAttribPointer(normalHandle, 3, GL_FLOAT, false, 0, nBuff)
        glEnableVertexAttribArray(normalHandle)

        glDrawArrays(GL_TRIANGLES, 0, vNum)

    }

    companion object {
        const val A_POSITION = "a_Position"
        const val A_COLOR = "a_Color"
        const val A_NORMAL = "a_Normal"

        const val U_LIGHTPOSITION = "u_LightPos"
        const val U_MVPMATRIX = "u_MVPMatrix"
        const val U_MVMMATRIX = "u_MVMatrix"

        const val V_UV = "a_TexCoordinate"

        val ATTRIBUTES =
            arrayOf(A_POSITION, A_COLOR, A_NORMAL, U_LIGHTPOSITION, U_MVPMATRIX, U_MVMMATRIX, V_UV)
    }
}