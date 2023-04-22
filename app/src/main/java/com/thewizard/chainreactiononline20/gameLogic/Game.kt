package com.thewizard.chainreactiononline20.gameLogic

import com.thewizard.chainreactiononline20.display.explosion.Explosion
import com.thewizard.chainreactiononline20.display.explosion.ExplosionStateListener
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameState
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.Player
import java.util.Vector


class Game(
    val gameSettings: GameSettings
) : GameLogic() {

    lateinit var gameState: GameState
    private val gameOverListeners = Vector<GameOverListener>()

    private var inAnimationState = false
    private var gameInProgress = false

    init {
        gameSettings.addListener {
            gameState = GameState(gameSettings)
        }
    }

    fun startGame() {
        gameState.reset()
        gameInProgress = true
    }


    fun stopGame(player: Player?) {
        if (player == null) return

        inAnimationState = false
        gameInProgress = false
        for (lis in gameOverListeners)
            lis.onGameWon(player)
    }


    fun playerInput(i: Int, j: Int) {
        if (inAnimationState) return
        if (!gameInProgress) return
        if (!checkValidity(gameState, i, j)) return

        inAnimationState = true
        gameState.mark(i, j, gameState.turnPlayer)
        checkForExplosions()
    }

    fun checkForExplosions() {
        if (!gameInProgress) return

        val explosionLocations = getExplosionLocations(gameState)

        if (explosionLocations.size == 0) {
            readyForNextTurn()
            return
        }

        gameState.explosionAnimation
            .startExplosions(explosionLocations,
                object : ExplosionStateListener {
                    override fun onExplosionStart(explosionPoints: Vector<Explosion>) {
                        makeExplosionsStage1(gameState, explosionLocations)
                    }

                    override fun onExplosionFinish(explosionPoints: Vector<Explosion>) {
                        makeExplosionsStage2(gameState, explosionLocations)
                        if (checkWon(gameState)) {
                            val winner = getWinner(gameState)
                            stopGame(winner)
                            return
                        }
                        checkForExplosions()
                    }
                })


    }

    fun readyForNextTurn() {
        inAnimationState = false
        nextTurn(gameState)
    }


    fun addGameOverListener(gameOverListener: GameOverListener) {
        gameOverListeners.add(gameOverListener)
    }


    fun interface GameOverListener {
        fun onGameWon(player: Player)
    }
}