package com.thewizard.chainreactiononline20.utils.openGlUtils


class Camera {

    var near = 1f
    var far = 100f

    var viewMatrix: ViewMatrix = ViewMatrix()
    lateinit var projectionMatrix: ProjectionMatrix

    fun eye(x: Float, y: Float, z: Float) = viewMatrix.eye(x, y, z)
    fun look(x: Float, y: Float, z: Float) = viewMatrix.look(x, y, z)
    fun up(x: Float = 0f, y: Float = 1f, z: Float = 0f) = viewMatrix.up(x, y, z)

    fun simpleProjection(type: ProjectionType, viewWidth: Int, viewHeight: Int): ProjectionMatrix {
        return ProjectionMatrix(type, viewWidth, viewHeight, near, far)
    }

}