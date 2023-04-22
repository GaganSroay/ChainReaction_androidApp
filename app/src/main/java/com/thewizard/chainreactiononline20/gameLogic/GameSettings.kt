package com.thewizard.chainreactiononline20.gameLogic

import com.thewizard.chainreactiononline20.utils.otherUtils.ChangeHandler

class GameSettings : ChangeHandler {

    var rows: Int = default_rows
    var cols: Int = default_cols
    var numberOfPLayers: Int = default_numberOfPLayers

    constructor()
    constructor(rows: Int, cols: Int, numberOfPlayer: Int) {
        setGameData(rows, cols, numberOfPlayer)
    }

    fun setGameData(rows: Int, cols: Int, numberOfPlayer: Int) {
        this.rows = rows
        this.cols = cols
        this.numberOfPLayers = numberOfPlayer
        onChange()
    }

    companion object {
        const val default_rows = 9
        const val default_cols = 6
        const val default_numberOfPLayers = 3

        val DEFAULT = GameSettings(default_rows, default_cols, default_numberOfPLayers)
        fun defaultBoard(numberOfPlayer: Int) =
            GameSettings(default_rows, default_cols, numberOfPlayer)
    }

}
