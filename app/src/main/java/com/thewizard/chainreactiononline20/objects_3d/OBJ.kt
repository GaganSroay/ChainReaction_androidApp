package com.thewizard.chainreactiononline20.objects_3d

import android.content.Context
import com.thewizard.chainreactiononline20.display.color.PlayerColor
import com.thewizard.chainreactiononline20.utils.ShaderUtil.Shader
import com.thewizard.chainreactiononline20.utils.objUtils.OBJloader
import com.thewizard.chainreactiononline20.utils.openGlUtils.Mat
import com.thewizard.chainreactiononline20.utils.openGlUtils.OpenGL
import com.thewizard.chainreactiononline20.utils.otherUtils.BufferHelper
import java.nio.FloatBuffer

open class OBJ(context: Context, res: Int) : OpenGL() {

    var vBuff: FloatBuffer
    var nBuff: FloatBuffer

    var vNum = 0

    var color = PlayerColor.DEFAULT
    var ambientColor = PlayerColor.DEFAULT

    init {

        val objLoader = OBJloader(context, res)

        vNum = objLoader.positions.size / 3
        vBuff = BufferHelper.getFloatBuffer(objLoader.positions)
        nBuff = BufferHelper.getFloatBuffer(objLoader.normals)


    }

    fun setShader(shader: Shader) {
        program = shader.program
    }

    open fun draw(model: Mat) {

        program.use()

        U_MVMMATRIX.setMatrix(viewMatrix * model)
        U_MVPMATRIX.setMatrix(projectionMatrix * viewMatrix * model)

        A_POSITION.setBuffer(vBuff, 3)
        A_NORMAL.setBuffer(nBuff, 3)

        U_COLOR.setArray(color)
        U_AMBIENT_COLOR.setArray(ambientColor)

        drawTriangles(vNum)
    }


    companion object {
        const val A_POSITION = "a_Position"
        const val U_COLOR = "u_Color"
        const val A_NORMAL = "a_Normal"
        const val U_AMBIENT_COLOR = "u_AmbientColor"

        const val U_LIGHTPOSITION = "u_LightPos"
        const val U_MVPMATRIX = "u_MVPMatrix"
        const val U_MVMMATRIX = "u_MVMatrix"

        const val V_UV = "a_TexCoordinate"

        val ATTRIBUTES = arrayOf(
            A_POSITION,
            U_COLOR,
            A_NORMAL,
            U_AMBIENT_COLOR,
            U_LIGHTPOSITION,
            U_MVPMATRIX,
            U_MVMMATRIX,
            V_UV
        )
    }
}