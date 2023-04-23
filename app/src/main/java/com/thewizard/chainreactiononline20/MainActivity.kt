package com.thewizard.chainreactiononline20

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.thewizard.chainreactiononline20.display.GameRenderer
import com.thewizard.chainreactiononline20.display.GridSurfaceTouchHandler
import com.thewizard.chainreactiononline20.gameLogic.Game
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameSettings
import com.thewizard.chainreactiononline20.ui_elements.GameSurfaceView
import com.thewizard.chainreactiononline20.ui_elements.NeonButton
import com.thewizard.chainreactiononline20.ui_elements.NeonImageButton
import com.thewizard.chainreactiononline20.ui_elements.NeonTextView

class MainActivity : AppCompatActivity(), GridSurfaceTouchHandler.TouchResult {
    lateinit var glsurface: GameSurfaceView
    lateinit var startButton: NeonButton

    lateinit var mainMenu: ConstraintLayout
    lateinit var gameOverMenu: ConstraintLayout
    lateinit var root: ViewGroup

    val gameSettings = GameSettings.DEFAULT

    lateinit var game: Game
    lateinit var renderer: GameRenderer


    lateinit var currentPlayerView: NeonTextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root = findViewById(android.R.id.content)
        mainMenu =
            layoutInflater.inflate(R.layout.game_start_layout, root, false) as ConstraintLayout
        gameOverMenu =
            layoutInflater.inflate(R.layout.game_over_layout, root, false) as ConstraintLayout

        gameOverMenu.visibility = GONE

        root.addView(mainMenu)
        root.addView(gameOverMenu)
        startButton = mainMenu.findViewById(R.id.startButton)
        glsurface = findViewById(R.id.glsurface)

        renderer = GameRenderer(this, gameSettings)
        glsurface.setRenderer(renderer)

        game = Game(gameSettings)
        game.addGameOverListener { runOnUiThread { stopGame() } }

        glsurface.setOnTouchListener(GridSurfaceTouchHandler(gameSettings, this))

        currentPlayerView = mainMenu.findViewById(R.id.playerNumberView)
        startButton.setOnClickListener { startGame() }
        addIncDecButtonListeners(mainMenu)
        updatePlayerView()
    }

    fun addIncDecButtonListeners(parent: ViewGroup) {

        val incButton = parent.findViewById<NeonImageButton>(R.id.increasePLayerButton)
        val decButton = parent.findViewById<NeonImageButton>(R.id.decreasePLayerButton)

        incButton.setOnClickListener {
            gameSettings.numberOfPLayers++
            updatePlayerView()
        }
        decButton.setOnClickListener {
            gameSettings.numberOfPLayers--
            updatePlayerView()
        }
    }

    fun removeIncDecButtonListeners(parent: ViewGroup) {
        val incButton = parent.findViewById<NeonImageButton>(R.id.increasePLayerButton)
        val decButton = parent.findViewById<NeonImageButton>(R.id.decreasePLayerButton)
        incButton.setOnClickListener(null)
        decButton.setOnClickListener(null)
    }

    fun updatePlayerView() {
        currentPlayerView.text = gameSettings.numberOfPLayers.toString()
    }

    fun stopGame() {
        gameOverMenu.visibility = VISIBLE
        currentPlayerView = findViewById(R.id.playerNumberView)
        updatePlayerView()

        addIncDecButtonListeners(gameOverMenu)
        gameOverMenu.findViewById<NeonButton>(R.id.restartButton).setOnClickListener {
            gameOverMenu.visibility = GONE
            removeIncDecButtonListeners(gameOverMenu)
            startGame()
        }
    }

    fun startGame() {
        mainMenu.visibility = GONE
        removeIncDecButtonListeners(mainMenu)
        root.removeView(mainMenu)
        game.startGame()
        renderer.gameState = game.gameState
    }


    override fun onStart() {
        glsurface.onResume()
        super.onStart()
    }

    override fun onPause() {
        glsurface.onPause()
        super.onPause()
    }

    override fun onResume() {
        glsurface.onResume()
        super.onResume()
    }

    override fun getTouchIndex(i: Int, j: Int) {
        game.playerInput(i, j)
    }

}