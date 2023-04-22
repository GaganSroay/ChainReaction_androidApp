package com.thewizard.chainreactiononline20.gameLogic.dataHolder

import com.thewizard.chainreactiononline20.display.color.BasicColor
import com.thewizard.chainreactiononline20.display.color.GridColor
import com.thewizard.chainreactiononline20.display.color.PlayerColor

class Player {

    lateinit var color: BasicColor
    lateinit var backgroundColorValue: FloatArray
    lateinit var colorValue: FloatArray

    var qualified = true
    var inGame = false
    var playerIndex = -1

    constructor(color: BasicColor, playerIndex: Int) {
        inGame = true
        this.playerIndex = playerIndex
        color(color)
    }

    constructor(playerIndex: Int) {
        inGame = true
        this.playerIndex = playerIndex
        if (playerIndex != -1)
            color(PlayerColor.getColorName(playerIndex))
    }

    fun color(color: BasicColor) {
        this.color = color
        colorValue = PlayerColor.getColor(color)
        backgroundColorValue = GridColor.getBackgroundColor(color)
    }


}