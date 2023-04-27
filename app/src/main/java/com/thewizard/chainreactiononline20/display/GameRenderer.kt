package com.thewizard.chainreactiononline20.display

import android.content.Context
import android.opengl.GLES20.glViewport
import android.opengl.GLSurfaceView
import android.view.Window
import android.view.WindowManager
import com.thewizard.chainreactiononline20.MainActivity
import com.thewizard.chainreactiononline20.R
import com.thewizard.chainreactiononline20.display.color.ColorArray
import com.thewizard.chainreactiononline20.display.color.GridColor
import com.thewizard.chainreactiononline20.display.grid.BoxPositions
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameSettings
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameState
import com.thewizard.chainreactiononline20.objects_3d.Grid
import com.thewizard.chainreactiononline20.objects_3d.Light
import com.thewizard.chainreactiononline20.objects_3d.OBJ
import com.thewizard.chainreactiononline20.objects_3d.Plain
import com.thewizard.chainreactiononline20.objects_3d.Sphere.DoubleSphere
import com.thewizard.chainreactiononline20.objects_3d.Sphere.Sphere
import com.thewizard.chainreactiononline20.objects_3d.Sphere.TrippleSphere
import com.thewizard.chainreactiononline20.utils.ShaderUtil.Shader
import com.thewizard.chainreactiononline20.utils.openGlUtils.Camera
import com.thewizard.chainreactiononline20.utils.openGlUtils.OpenGL
import com.thewizard.chainreactiononline20.utils.openGlUtils.ProjectionType
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class GameRenderer(
    val context: Context,
    gameSettings: GameSettings
) : GLSurfaceView.Renderer, OpenGL() {

    private lateinit var camera: Camera

    private val sphere = Sphere(context)
    private val doubleSphere = DoubleSphere(context)
    private val tripleSphere = TrippleSphere(context)
    private val light = Light()
    private var grid = Grid(gameSettings)
    private var plain = Plain(context)

    private var boxPositions = BoxPositions(gameSettings)

    private lateinit var sphereShader: Shader
    private lateinit var gridShader: Shader
    private lateinit var plainShader: Shader

    private lateinit var gridColor: FloatArray

    private val window: Window = (context as MainActivity).window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    }

    private var backgroundColor: FloatArray = GridColor.DEFAULT_BACKGROUND
        set(value) {
            field = value
            window.statusBarColor = ColorArray.toColor(value)
        }

    var gameState: GameState? = null
        set(value) {
            field = value
            gameState!!.apply {
                loop { box -> box.position = boxPositions.data[box.i][box.j] }
                addTurnChangeListener { turnPlayer ->
                    gridColor = turnPlayer.gridColorValue
                    backgroundColor = turnPlayer.backgroundColorValue
                    plain.color = turnPlayer.backgroundColorValue
                }
            }
        }



    override fun onSurfaceCreated(glUnused: GL10, config: EGLConfig) {
        clearColor = GridColor.DEFAULT_BACKGROUND
        enableCullFace()
        enableDepthText()

        sphereShader = Shader(context, R.raw.light_vertex_shader, R.raw.light_fragment_shader)
            .createProgram(OBJ.ATTRIBUTES)

        gridShader = Shader(context, R.raw.grid_vertex_shader, R.raw.grid_fragment_shader)
            .createProgram(Grid.ATTRIBUTES)

        plainShader = Shader(context, R.raw.plain_vertex_shader, R.raw.plain_fragment_shader)
            .createProgram(Plain.ATTRIBUTES)

        light.setShader(sphereShader)
        sphere.setShader(sphereShader)
        doubleSphere.setShader(sphereShader)
        tripleSphere.setShader(sphereShader)
        grid.addShader(gridShader)
        plain.addShader(plainShader)
        plain.generateTextures()


        gridColor = gameState?.turnPlayer?.colorValue ?: GridColor.DEFAULT_GRID
        backgroundColor =
            gameState?.turnPlayer?.backgroundColorValue ?: GridColor.DEFAULT_BACKGROUND
    }

    override fun onSurfaceChanged(glUnused: GL10, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        camera = Camera().apply {
            eye(0f, 0f, -0.5f)
            look(0f, 0f, -5f)
            projectionMatrix = simpleProjection(ProjectionType.PRESPECTIVE, width, height)
        }

        grid.addCamera(camera)

        sphere.addCamera(camera)
        doubleSphere.addCamera(camera)
        tripleSphere.addCamera(camera)

        plain.addCamera(camera)
    }


    override fun onDrawFrame(glUnused: GL10) {
        clear()

        plain.draw()
        grid.draw(gridColor)
        if (gameState != null) {
            drawExplosions()
            drawMolecules()
        }

    }

    private fun drawExplosions() {
        if (gameState?.explosionAnimation?.explosionInProgress!!)
            gameState?.explosionAnimation?.drawExplosions(sphere, gridColor)

    }

    private fun drawMolecules() {
        gameState?.loop { box ->
            box.apply {
                if (value == 0) return@loop
                val ambientCol = box.player.backgroundColorValue
                when (value) {
                    1 -> sphere.draw(model, player.colorValue, ambientCol)
                    2 -> doubleSphere.draw(model, player.colorValue, ambientCol)
                    3 -> tripleSphere.draw(model, player.colorValue, ambientCol)
                }
            }

        }
        light.drawLight(camera.viewMatrix.array)
    }


}