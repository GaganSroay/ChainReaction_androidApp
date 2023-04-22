package com.thewizard.chainreactiononline20.display

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.thewizard.chainreactiononline20.gameLogic.GameSettings

class GridSurfaceTouchHandler(
    gameSettings: GameSettings,
    private val boxLocation: TouchResult
) : OnTouchListener {

    var downX = 0
    var downY = 0

    private var rows: Int = 0
    private var cols: Int = 0

    init {
        gameSettings.addListener {
            rows = gameSettings.rows
            cols = gameSettings.cols
        }
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x.toInt()
                downY = event.y.toInt()
            }

            MotionEvent.ACTION_UP -> {
                val boxSize = ((v.width / cols) * 0.92).toInt()

                val startX = (v.width - boxSize * cols) / 2
                val startY = (v.height - boxSize * rows) / 2

                val nX = event.x.toInt() - startX
                val nY = event.y.toInt() - startY

                if (nX < 0 || nY < 0) return true

                val i = nY / boxSize
                val j = nX / boxSize

                val pi = (downY - startY) / boxSize
                val pj = (downX - startX) / boxSize

                if (i != pi || j != pj) return true

                boxLocation.getTouchIndex(i, j)
                v.performClick()
            }
        }


        return true
    }

    fun interface TouchResult {
        fun getTouchIndex(i: Int, j: Int)
    }


}