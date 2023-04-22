package com.thewizard.chainreactiononline20.display.explosion

import com.thewizard.chainreactiononline20.gameLogic.GameSettings
import com.thewizard.chainreactiononline20.objects_3d.Sphere.Sphere
import com.thewizard.chainreactiononline20.utils.objUtils.Point3D
import com.thewizard.chainreactiononline20.utils.openGlUtils.ModelMatrix

open class Explosion(
    val i: Int,
    val j: Int,
    gameSettings: GameSettings,
) {

    var upModel = ModelMatrix()
    var downModel = ModelMatrix()
    var leftModel = ModelMatrix()
    var rightModel = ModelMatrix()

    var up = i != 0
    var down = i != gameSettings.rows - 1
    var left = j != 0
    var right = j != gameSettings.cols - 1

    var gotLocation = false


    fun draw(location: Point3D, sphere: Sphere, d: Float, color: FloatArray) {
        if (!gotLocation) {
            upModel.position = location
            downModel.position = location
            leftModel.position = location
            rightModel.position = location
            gotLocation = true
        }

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