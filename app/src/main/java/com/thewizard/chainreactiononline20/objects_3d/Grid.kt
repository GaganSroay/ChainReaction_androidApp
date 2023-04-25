package com.thewizard.chainreactiononline20.objects_3d

import com.thewizard.chainreactiononline20.display.color.GridColor
import com.thewizard.chainreactiononline20.display.grid.GridObjectData
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameSettings
import com.thewizard.chainreactiononline20.utils.ShaderUtil.Shader
import com.thewizard.chainreactiononline20.utils.openGlUtils.OpenGL
import com.thewizard.chainreactiononline20.utils.otherUtils.BufferHelper
import java.nio.FloatBuffer

class Grid(
    val gameSettings: GameSettings
) : OpenGL() {

    private lateinit var vBuff: FloatBuffer
    private var numberOfVeritices: Int = 0

    var color = GridColor.DEFAULT_GRID

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
    }

    fun draw(color: FloatArray) {
        this.color = color
        draw()
    }


    fun draw() {

        program.use()
        lineWidth = 2f

        U_MVPMATRIX.setMatrix(viewMatrix * projectionMatrix.array)
        A_POSITION.setBuffer(vBuff, 3)
        U_COLOR.setArray(color)

        drawPoints(numberOfVeritices)

    }


    companion object {

        const val A_POSITION = "a_Position"
        const val U_MVPMATRIX = "u_MVPMatrix"
        const val U_COLOR = "u_Color"

        val ATTRIBUTES = arrayOf(A_POSITION, U_COLOR, U_MVPMATRIX)

    }


}