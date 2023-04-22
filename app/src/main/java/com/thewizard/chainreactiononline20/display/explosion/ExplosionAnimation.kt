package com.thewizard.chainreactiononline20.display.explosion

import android.os.SystemClock
import com.thewizard.chainreactiononline20.gameLogic.GameSettings
import com.thewizard.chainreactiononline20.objects_3d.GameGridRenderer
import com.thewizard.chainreactiononline20.objects_3d.Sphere.Sphere
import com.thewizard.chainreactiononline20.utils.objUtils.Point3D
import java.util.Vector
import kotlin.math.sin


class ExplosionAnimation(gameSettings: GameSettings) {

    var explosionPoints = Vector<Explosion>()

    val boxSize = (GameGridRenderer.sphereGridWidth / gameSettings.cols)
    var explosionStateListener: ExplosionStateListener? = null

    var time: Float = 0f
    var explosionInProgress = false;
    val progress: Float get() = (SystemClock.uptimeMillis() - time) / ANIMATION_DURATION
    val displacement: Float get() = boxSize * (1 + sin(Math.PI * (progress - 0.5))).toFloat() * 0.5f

    fun drawExplosions(spherePositions: Array<Array<Point3D>>, sphere: Sphere, color: FloatArray) {
        if (!explosionInProgress) return
        if (progress >= 0.95f) return endAnimation()

        for (e in explosionPoints) {
            e.draw(
                spherePositions[e.i][e.j],
                sphere,
                displacement,
                color
            )
        }

    }

    fun endAnimation() {
        explosionInProgress = false
        removeAllElements()
        explosionStateListener?.onExplosionFinish(explosionPoints)
    }

    fun startExplosions(explosionPoints: Vector<Explosion>) {
        this.explosionPoints = explosionPoints
        time = SystemClock.uptimeMillis().toFloat()
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