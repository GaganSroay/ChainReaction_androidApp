package com.thewizard.chainreactiononline20.gameLogic.dataHolder

import android.os.SystemClock
import com.thewizard.chainreactiononline20.utils.objUtils.Vec3
import com.thewizard.chainreactiononline20.utils.openGlUtils.ModelMatrix
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class Box(
    val i: Int,
    val j: Int,
    gameSettings: GameSettings
) : ModelMatrix() {

    lateinit var player: Player

    private val ran = Random.nextFloat()
    var value = 0
    var maxValue = getMaxValue(i, j, gameSettings)

    var prevTime = 0L
    private var phase = 0f
    private var angle: Float = 0f
    private val delta = Random.nextFloat() * Math.PI.toFloat()
    private val angleVec = Vec3(ran * 2f - 1, ran * 2f - 1, ran * 2f - 1)
    private var vibrationVec = normalise(Vec3(angleVec.i, angleVec.j, 0f))

    val model: FloatArray
        get() {
            if (value == maxValue) vibrate()
            else defaultPosition()
            rotationAnimation()
            return modelMatrix
        }

    private fun rotationAnimation() {
        angle = if (angle < 360) angle + ROTATION_SPEED else 0f
        rotate(angle, angleVec.i, angleVec.j, angleVec.k)
    }

    private fun vibrate() {
        val time = SystemClock.uptimeMillis()

        if (phase < Math.PI) phase += (time - prevTime) * FREQUENCY
        else phase = 0f

        val x = sin(phase + delta) * vibrationVec.i * VIBRATION_AMPLITUDE
        val y = sin(phase + delta) * vibrationVec.j * VIBRATION_AMPLITUDE
        moveRelativeToOrigin(x, y, 0f)

        prevTime = time
    }


    companion object {

        const val VIBRATION_AMPLITUDE = 0.015f
        const val FREQUENCY = 0.03f
        const val ROTATION_SPEED = 2f


        fun normalise(vec3: Vec3): Vec3 {
            val base = sqrt(vec3.i * vec3.i + vec3.j * vec3.j + vec3.k * vec3.k)
            return Vec3(vec3.i / base, vec3.j / base, vec3.k / base)
        }

        private fun getMaxValue(i: Int, j: Int, gameSettings: GameSettings): Int {
            return if (isCorner(i, j, gameSettings)) 1
            else if (isSide(i, j, gameSettings)) 2
            else 3
        }

        private fun isCorner(i: Int, j: Int, gameSettings: GameSettings): Boolean =
            (i == 0 || i == gameSettings.rows - 1) && (j == 0 || j == gameSettings.cols - 1)

        private fun isSide(i: Int, j: Int, gameSettings: GameSettings): Boolean =
            !isCorner(
                i,
                j,
                gameSettings
            ) && (i == 0 || j == 0 || i == gameSettings.rows - 1 || j == gameSettings.cols - 1)

        private fun isInBox(i: Int, j: Int, gameSettings: GameSettings): Boolean =
            i >= 0 && j >= 0 && i < gameSettings.rows && j < gameSettings.cols
    }


}