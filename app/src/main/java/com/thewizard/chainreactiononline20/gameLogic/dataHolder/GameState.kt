package com.thewizard.chainreactiononline20.gameLogic.dataHolder

import com.thewizard.chainreactiononline20.display.explosion.ExplosionAnimation
import java.util.Vector

class GameState(val gameSettings: GameSettings) {

    var explosionAnimation = ExplosionAnimation(gameSettings)
    val turnChangeListeners = Vector<TurnChange>()

    val rows = gameSettings.rows
    val cols = gameSettings.cols
    val numberOfPLayers = gameSettings.numberOfPLayers
    var board: Array<Array<Box>> = Array(rows,
        init = { i: Int ->
            Array(cols,
                init = { j: Int ->
                    Box(i, j, gameSettings)
                }
            )
        }
    )
    var players = Array(numberOfPLayers, init = { i: Int -> Player(i) })

    var turn = 0
        set(value) {
            field = value
            turnChanged()
        }

    val turnPlayer: Player get() = players[turn]


    fun reset() {
        loop { box -> box.value = 0 }
        for (player in players)
            if (player.inGame) player.qualified = true
    }


    fun mark(i: Int, j: Int, turnPlayer: Player) {
        if (board[i][j].value == 0 || board[i][j].player == turnPlayer)
            increase(i, j, turnPlayer)
    }


    fun increase(i: Int, j: Int, turnPlayer: Player) {
        if (isInBox(i, j)) {
            board[i][j].value++
            board[i][j].player = turnPlayer
        }
    }

    private fun isCorner(i: Int, j: Int): Boolean =
        (i == 0 || i == rows - 1) && (j == 0 || j == cols - 1)

    private fun isSide(i: Int, j: Int): Boolean =
        !isCorner(i, j) && (i == 0 || j == 0 || i == rows - 1 || j == cols - 1)

    private fun isInBox(i: Int, j: Int): Boolean = i >= 0 && j >= 0 && i < rows && j < cols


    fun loop(looper: Looper) {
        for (i in 0 until rows)
            for (j in 0 until cols) {
                looper.get(board[i][j])
            }
    }

    fun turnChanged() {
        for (listener in turnChangeListeners)
            listener.onChange(turnPlayer)
    }

    fun addTurnChangeListener(turnChangeListener: TurnChange) {
        turnChangeListener.onChange(turnPlayer)
        turnChangeListeners.add(turnChangeListener)
    }

    fun interface Looper {
        fun get(box: Box)
    }

    fun interface TurnChange {
        fun onChange(turnPlayer: Player)
    }


}