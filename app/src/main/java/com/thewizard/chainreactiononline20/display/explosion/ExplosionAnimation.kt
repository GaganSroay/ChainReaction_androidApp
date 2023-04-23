package com.thewizard.chainreactiononline20.display.explosion

import android.os.SystemClock
import com.thewizard.chainreactiononline20.display.grid.BoxPositions
import com.thewizard.chainreactiononline20.gameLogic.dataHolder.GameSettings
import com.thewizard.chainreactiononline20.objects_3d.Sphere.Sphere
import java.util.Vector
import kotlin.math.sin


class ExplosionAnimation(gameSettings: GameSettings) {

    var explosionPoints = Vector<Explosion>()

    val boxSize = (BoxPositions.sphereGridWidth / gameSettings.cols)
    var explosionStateListener: ExplosionStateListener? = null

    var time = 0L
    var explosionInProgress = false
    val progress: Float get() = (SystemClock.uptimeMillis() - time) / ANIMATION_DURATION
    val displacement: Float get() = boxSize * (1.0 + sin(Math.PI * (progress - 0.5))).toFloat() * 0.5f


    fun drawExplosions(sphere: Sphere, color: FloatArray) {
        if (!explosionInProgress) return
        if (progress >= 0.95f) return endAnimation()

        for (e in explosionPoints) {
            e.draw(sphere, displacement, color)
        }

    }

    fun endAnimation() {
        explosionInProgress = false
        removeAllElements()
        explosionStateListener?.onExplosionFinish(explosionPoints)
    }

    fun startExplosions(explosionPoints: Vector<Explosion>) {
        this.explosionPoints = explosionPoints
        time = SystemClock.uptimeMillis()
        explosionInProgress = true

    }

    fun startExplosions(
        explosionPoints: Vector<Explosion>,
        explosionStateListener: ExplosionStateListener
    ) {
        this.explosionStateListener = explosionStateListener
        explosionStateListener.onExplosionStart(explosionPoints)
        startExplosions(explosionPoints)
    }

    fun removeAllElements() {
        explosionPoints = Vector<Explosion>()
    }

    companion object {
        val ANIMATION_DURATION = 350f
    }

}