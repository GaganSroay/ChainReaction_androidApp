package com.thewizard.chainreactiononline20.objects_3d

import android.content.Context
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
import com.thewizard.chainreactiononline20.utils.ShaderUtil.Shader
import com.thewizard.chainreactiononline20.utils.objUtils.Point3D
import com.thewizard.chainreactiononline20.utils.otherUtils.BufferHelper
import java.nio.FloatBuffer
import java.util.Vector

class GameGridRenderer(
    context: Context,
    numberOfRows: Int,
    numberOfColumns: Int
) {

    private var program = 0
    private var mvMatrixHandle = 0
    private var positionHandle = 0
    private var colorHandle = 0

    private var vBuff: FloatBuffer
    private val vpMatrix = FloatArray(16)
    private var numberOfVeritices: Int
    var color = floatArrayOf(1f, 0f, 0f, 1f)

    var spherePositions: Array<Array<Point3D>>

    lateinit var viewMatrix: FloatArray
    lateinit var projectionMatrix: FloatArray

    init {
        val vary = calculateGridPositions(numberOfRows, numberOfColumns)
        numberOfVeritices = vary.size / 3
        vBuff = BufferHelper.getFloatBuffer(vary)

        spherePositions = calculateSpherePositions(numberOfRows, numberOfColumns)
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
        glUseProgram(program);

        Matrix.multiplyMM(vpMatrix, 0, viewMatrix, 0, projectionMatrix, 0)
        glLineWidth(2f)

        vBuff.position(0)
        glVertexAttribPointer(positionHandle, 3, GL_FLOAT, false, 0, vBuff);
        glEnableVertexAttribArray(positionHandle);

        glUniform4fv(colorHandle, 1, color, 0)

        glUniformMatrix4fv(mvMatrixHandle, 1, false, vpMatrix, 0);
        glDrawArrays(GL_POINTS, 0, numberOfVeritices);
    }


    companion object {
        const val A_POSITION = "a_Position"
        const val U_MVPMATRIX = "u_MVPMatrix";
        const val U_COLOR = "u_Color"

        val ATTRIBUTES = arrayOf(A_POSITION, U_COLOR, U_MVPMATRIX)


        private val multi = 1.1f;
        private val startZ = -2f

        val sphereGridWidth = 1.39f

        fun calculateSpherePositions(
            numberOfRows: Int,
            numberOfColumns: Int
        ): Array<Array<Point3D>> {
            val spherePositions = Array(numberOfRows) { Array(numberOfColumns) { Point3D() } }

            val startX = -sphereGridWidth / 2
            val startY = (numberOfRows * sphereGridWidth) / (numberOfColumns * 2)

            val boxSize = sphereGridWidth / numberOfColumns
            val halfSize = boxSize / 2f

            val bx = startX + halfSize
            val by = startY - halfSize
            val bz = startZ - halfSize

            for (i in 0 until numberOfRows)
                for (j in 0 until numberOfColumns)
                    spherePositions[i][j] = Point3D(bx + j * boxSize, by - i * boxSize, bz)

            return spherePositions
        }


        fun calculateGridPositions(numberOfRows: Int, numberOfColumns: Int): FloatArray {
            val vlist = Vector<Float>()

            val gridWidth = 0.9f * multi;
            val startX = -gridWidth / 2
            val startZ = -1.1f

            val startY = (numberOfRows * gridWidth) / (numberOfColumns * 2)

            val bs = gridWidth / numberOfColumns

            val newZ = bs * 0.5f;
            val endZ = startZ - newZ


            for (i in 0..numberOfColumns) {
                val x = startX + i * bs
                for (j in 0..numberOfRows) {
                    val y = startY - j * bs
                    if (j < numberOfRows) {
                        addPoint(
                            vlist,
                            x, y, startZ,
                            x, y - bs, startZ
                        )
                        addPoint(
                            vlist,
                            x, y, endZ,
                            x, y - bs, endZ
                        )
                    }

                    addPoint(
                        vlist,
                        x, y, startZ,
                        x, y, startZ - newZ,
                    )
                }

            }

            for (i in 0..numberOfRows) {
                val y = startY - i * bs
                for (j in 0 until numberOfColumns) {
                    val x = startX + j * bs
                    addPoint(
                        vlist,
                        x, y, startZ,
                        x + bs, y, startZ
                    )
                    addPoint(
                        vlist,
                        x, y, endZ,
                        x + bs, y, endZ
                    )
                }
            }
            return vlist.toFloatArray()
        }

        fun addPoint(
            list: Vector<Float>,
            x1: Float, y1: Float, z1: Float,
            x2: Float, y2: Float, z2: Float
        ) {
            val sections = 30
            var px = x1
            var py = y1
            var pz = z1

            for (i in 0..sections) {
                val m: Float = i.toFloat() / sections.toFloat()
                val x = sectionFormula(x1, x2, m)
                val y = sectionFormula(y1, y2, m)
                val z = sectionFormula(z1, z2, m)

                if (i % 2 == 1) {
                    list.add(x)
                    list.add(y)
                    list.add(z)

                    list.add(px)
                    list.add(py)
                    list.add(pz)
                }

                px = x
                py = y
                pz = z
            }

        }

        fun sectionFormula(p1: Float, p2: Float, m: Float): Float {
            val n = 1f - m
            return (m * p2 + n * p1) / (n + m)
        }
    }


}