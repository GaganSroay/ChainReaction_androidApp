package com.thewizard.chainreactiononline20.gameLogic.dataHolder

import android.os.SystemClock
import com.thewizard.chainreactiononline20.utils.objUtils.Vec3
import com.thewizard.chainreactiononline20.utils.openGlUtils.ModelMatrix
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class Box : ModelMatrix() {
    lateinit var player: Player

    var value = 0
    var maxValue = 3

    private var angle = 0f
    private var phase = 0f
    private val delta = Random.nextFloat() * Math.PI.toFloat()

    var prevTime = 0L

    private val angleVec = Vec3(
        Random.nextFloat() * 2f - 1,
        Random.nextFloat() * 2f - 1,
        Random.nextFloat() * 2f - 1
    )

    var vibrationVec = normalise(Vec3(angleVec.i, angleVec.j, 0f))

    val model: FloatArray
        get() {
            if (value == maxValue) vibrate()
            else defaultPosition()
            rotatetionAnimation()
            return modelMatrix
        }


    fun rotatetionAnimation() {
        angle = if (angle < 360) angle + 2f else 0f
        rotate(angle, angleVec.i, angleVec.j, angleVec.k)
    }

    fun vibrate() {
        val time = SystemClock.uptimeMillis()

        if (phase < Math.PI) phase += (time - prevTime) * 0.05f
        else phase = 0f

        val x = sin(phase + delta) * vibrationVec.i * 0.01f
        val y = sin(phase + delta) * vibrationVec.j * 0.01f
        moveRelativeToOrigin(x, y, 0f)

        prevTime = time
    }


    companion object {
        fun normalise(vec3: Vec3): Vec3 {
            val base = sqrt(vec3.i * vec3.i + vec3.j * vec3.j + vec3.k * vec3.k)
            return Vec3(vec3.i / base, vec3.j / base, vec3.k / base)
        }
    }

}