package com.thewizard.chainreactiononline20.display.explosion

import java.util.Vector

interface ExplosionStateListener {
    fun onExplosionStart(explosionPoints: Vector<Explosion>)
    fun onExplosionFinish(explosionPoints: Vector<Explosion>)
}