package com.thewizard.chainreactiononline20.gameLogic

import com.thewizard.chainreactiononline20.display.explosion.Explosion
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameState
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.Player
import java.util.Vector

open class GameLogic {

    fun checkValidity(gameState: GameState, i: Int, j: Int): Boolean =
        checkPlayerValidity(gameState) && checkMoveValidity(gameState, i, j)

    private fun checkPlayerValidity(gameState: GameState): Boolean =
        gameState.turn == gameState.turnPlayer.playerIndex

    private fun nextPlayer(turn: Int, gameState: GameState): Int =
        (turn + 1) % gameState.gameSettings.numberOfPLayers

    private fun checkMoveValidity(gameState: GameState, i: Int, j: Int): Boolean =
        gameState.board[i][j].value == 0
                || gameState.board[i][j].player.playerIndex == gameState.turn


    fun getExplosionLocations(gameState: GameState): Vector<Explosion> {
        val explosionLocations = Vector<Explosion>()
        gameState.loop { box ->
            if (box.value > box.maxValue)
                explosionLocations.add(Explosion(box, gameState.gameSettings))
        }
        return explosionLocations
    }


    fun makeExplosionsStage1(gameState: GameState, explosionLocations: Vector<Explosion>) {
        for (exp in explosionLocations)
            gameState.board[exp.i][exp.j].value -= (gameState.board[exp.i][exp.j].maxValue + 1)
        updatePlayers(gameState)
    }

    fun makeExplosionsStage2(gameState: GameState, explosionLocations: Vector<Explosion>) {
        for (exp in explosionLocations) {
            gameState.increase(exp.i + 1, exp.j, gameState.turnPlayer)
            gameState.increase(exp.i - 1, exp.j, gameState.turnPlayer)
            gameState.increase(exp.i, exp.j + 1, gameState.turnPlayer)
            gameState.increase(exp.i, exp.j - 1, gameState.turnPlayer)
        }
        updatePlayers(gameState)
    }

    fun nextTurn(gameState: GameState) {
        val turn = gameState.turn
        gameState.turn = nextPlayer(turn, gameState)
        if (!gameState.players[nextPlayer(turn, gameState)].qualified)
            nextTurn(gameState)
    }


    private fun updatePlayers(gameState: GameState) {
        for (p in gameState.players)
            p.qualified = false
        gameState.loop { box ->
            if (box.value > 0)
                box.player.qualified = true
        }
    }

    fun checkWon(gameState: GameState): Boolean {
        var i = 0
        for (p in gameState.players) if (p.qualified) i++
        return i <= 1
    }

    fun getWinner(gameState: GameState): Player? {
        if (checkWon(gameState))
            for (p in gameState.players)
                if (p.qualified)
                    return p
        return null
    }
}