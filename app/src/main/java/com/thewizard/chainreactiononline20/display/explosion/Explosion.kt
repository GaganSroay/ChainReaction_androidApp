package com.thewizard.chainreactiononline20.display.explosion

import com.thewizard.chainreactiononline20.gameLogic.dataHolder.Box
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameSettings
import com.thewizard.chainreactiononline20.objects_3d.Sphere.Sphere
import com.thewizard.chainreactiononline20.utils.openGlUtils.ModelMatrix

open class Explosion(
    box: Box,
    gameSettings: GameSettings,
) {

    val i = box.i
    val j = box.j

    var upModel = ModelMatrix()
    var downModel = ModelMatrix()
    var leftModel = ModelMatrix()
    var rightModel = ModelMatrix()

    var up = i != 0
    var down = i != gameSettings.rows - 1
    var left = j != 0
    var right = j != gameSettings.cols - 1

    init {
        upModel.position = box.position
        downModel.position = box.position
        leftModel.position = box.position
        rightModel.position = box.position
    }


    fun draw(sphere: Sphere, d: Float, color: FloatArray) {

        if (up) {
            upModel.moveRelativeToOrigin(0f, d, 0f)
            sphere.draw(upModel.modelMatrix, color)
        }

        if (down) {
            downModel.moveRelativeToOrigin(0f, -d, 0f)
            sphere.draw(downModel.modelMatrix, color)
        }

        if (left) {
            leftModel.moveRelativeToOrigin(-d, 0f, 0f)
            sphere.draw(leftModel.modelMatrix, color)
        }

        if (right) {
            rightModel.moveRelativeToOrigin(d, 0f, 0f)
            sphere.draw(rightModel.modelMatrix, color)
        }


    }
}