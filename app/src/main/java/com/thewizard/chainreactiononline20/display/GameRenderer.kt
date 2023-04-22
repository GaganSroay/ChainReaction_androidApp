package com.thewizard.chainreactiononline20.display

import android.content.Context
import android.graphics.Color
import android.opengl.GLES20.GL_COLOR_BUFFER_BIT
import android.opengl.GLES20.GL_CULL_FACE
import android.opengl.GLES20.GL_DEPTH_BUFFER_BIT
import android.opengl.GLES20.GL_DEPTH_TEST
import android.opengl.GLES20.glClear
import android.opengl.GLES20.glClearColor
import android.opengl.GLES20.glEnable
import android.opengl.GLES20.glViewport
import android.opengl.GLSurfaceView
import android.view.Window
import android.view.WindowManager
import com.thewizard.chainreactiononline20.MainActivity
import com.thewizard.chainreactiononline20.R
import com.thewizard.chainreactiononline20.display.color.BasicColor
import com.thewizard.chainreactiononline20.display.color.GridColor
import com.thewizard.chainreactiononline20.display.color.PlayerColor
import com.thewizard.chainreactiononline20.gameLogic.GameSettings
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameState
import com.thewizard.chainreactiononline20.objects_3d.GameGridRenderer
import com.thewizard.chainreactiononline20.objects_3d.Light
import com.thewizard.chainreactiononline20.objects_3d.OBJ
import com.thewizard.chainreactiononline20.objects_3d.Sphere.DoubleSphere
import com.thewizard.chainreactiononline20.objects_3d.Sphere.Sphere
import com.thewizard.chainreactiononline20.objects_3d.Sphere.TrippleSphere
import com.thewizard.chainreactiononline20.utils.ShaderUtil.Shader
import com.thewizard.chainreactiononline20.utils.objUtils.Point3D
import com.thewizard.chainreactiononline20.utils.openGlUtils.ProjectionMatrix
import com.thewizard.chainreactiononline20.utils.openGlUtils.ProjectionType
import com.thewizard.chainreactiononline20.utils.openGlUtils.ViewMatrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class GameRenderer(
    val context: Context,
    gameSettings: GameSettings
) : GLSurfaceView.Renderer {

    private lateinit var viewMatrix: ViewMatrix
    private lateinit var gridProjectionMatrix: ProjectionMatrix
    private lateinit var sphereProjectionMatrix: ProjectionMatrix

    private val sphere = Sphere(context)
    private val doubleSphere = DoubleSphere(context)
    private val tripleSphere = TrippleSphere(context)
    private val light = Light()
    private lateinit var gridRenderer: GameGridRenderer

    private lateinit var sphereShader: Shader
    private lateinit var gridShader: Shader

    lateinit var gridColor: FloatArray
    lateinit var spherePositions: Array<Array<Point3D>>

    init {
        gameSettings.apply {
            addListener {
                gridRenderer = GameGridRenderer(context, rows, cols)
                spherePositions = GameGridRenderer.calculateSpherePositions(rows, cols)
            }
        }

    }


    val window: Window = (context as MainActivity).window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    var backgroundColor: FloatArray = floatArrayOf(0.05f, 0.05f, 0.08f, 1.0f)
        set(value) {
            field = value
            window.statusBarColor = convertToColor(value)
        }

    var gameState: GameState? = null
        set(value) {
            field = value
            gameState!!.apply {
                loop { i, j, box -> box.position = spherePositions[i][j] }
                addTurnChangeListener { turnPlayer ->
                    gridColor = turnPlayer.colorValue
                    backgroundColor = turnPlayer.backgroundColorValue
                }
            }
        }

    fun convertToColor(colorArray: FloatArray): Int = Color.rgb(
        (colorArray[0] * 255).toInt(),
        (colorArray[1] * 255).toInt(),
        (colorArray[2] * 255).toInt()
    )


    override fun onSurfaceCreated(glUnused: GL10, config: EGLConfig) {
        glClearColor(0.05f, 0.05f, 0.08f, 1.0f)
        glEnable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)

        sphereShader = Shader(context, R.raw.light_vertex_shader, R.raw.light_fragment_shader)
            .createProgram(OBJ.ATTRIBUTES)

        gridShader = Shader(context, R.raw.grid_vertex_shader, R.raw.grid_fragment_shader)
            .createProgram(GameGridRenderer.ATTRIBUTES)

        light.setShader(sphereShader)
        sphere.setShader(sphereShader)
        doubleSphere.setShader(sphereShader)
        tripleSphere.setShader(sphereShader)
        gridRenderer.addShader(gridShader)

    }

    override fun onSurfaceChanged(glUnused: GL10, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        viewMatrix = ViewMatrix().apply {
            eye(0f, 0f, -0.5f)
            look(0f, 0f, -5f)
            up(0f, 1f, 0f)
        }

        gridProjectionMatrix = ProjectionMatrix(ProjectionType.PRESPECTIVE, width, height, 1f, 100f)
        sphereProjectionMatrix =
            ProjectionMatrix(ProjectionType.ORTHOGONAL, width, height, 1f, 100f)

        gridRenderer.addViewAndProjectionMatrix(viewMatrix.get(), gridProjectionMatrix.get())

        sphere.addViewAndProjectionMatrix(viewMatrix.get(), gridProjectionMatrix.get())
        doubleSphere.addViewAndProjectionMatrix(viewMatrix.get(), gridProjectionMatrix.get())
        tripleSphere.addViewAndProjectionMatrix(viewMatrix.get(), gridProjectionMatrix.get())

        gridColor = PlayerColor.getColor(BasicColor.RED)
        backgroundColor = GridColor.getBackgroundColor(BasicColor.RED)
    }


    override fun onDrawFrame(glUnused: GL10) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        drawGrid()
        if (gameState != null) {
            drawExplosions()
            drawMolecules()
        }

    }

    private fun drawGrid() {
        glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0f)
        gridRenderer.draw(gridColor)
    }

    private fun drawExplosions() {
        gameState!!.explosionAnimation.apply {
            if (explosionInProgress)
                drawExplosions(gridRenderer.spherePositions, sphere, gridColor)
        }
    }

    private fun drawMolecules() {
        gameState?.loop { i, j, box ->
            box.apply {
                when (value) {
                    1 -> sphere.draw(model, player.colorValue)
                    2 -> doubleSphere.draw(model, player.colorValue)
                    3 -> tripleSphere.draw(model, player.colorValue)
                }
            }

        }
        light.drawLight(viewMatrix.get())
    }


}