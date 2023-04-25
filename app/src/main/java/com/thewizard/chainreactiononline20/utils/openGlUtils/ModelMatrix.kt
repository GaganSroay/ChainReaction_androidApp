package com.thewizard.chainreactiononline20.utils.openGlUtils

import com.thewizard.chainreactiononline20.utils.objUtils.Point3D

open class ModelMatrix {

    var positionMatrix = Mat()
    var rotationMatrix = Mat()
    var scaleMatrix = Mat()

    var viewMatrix = Mat()
    var projectionMatrix = Mat()

    val modelMatrix: Mat get() = positionMatrix * scaleMatrix * rotationMatrix

    val viewProjectionMatrix: Mat get() = viewMatrix * projectionMatrix
    val modelViewMatrix: Mat get() = viewMatrix * modelMatrix

    val modelViewProjectionMatrix: Mat get() = projectionMatrix * viewMatrix * modelMatrix

    var position: Point3D = Point3D()
        set(point) {
            field = point
            positionMatrix.translate(point.x, point.y, point.z)
        }

    val X: Float get() = position.x
    val Y: Float get() = position.y
    val Z: Float get() = position.z

    fun scale(s: Float) = scale(s, s, s)
    fun scale(x: Float, y: Float, z: Float) = scaleMatrix.scale(x, y, z)

    fun translate(x: Float, y: Float, z: Float) = positionMatrix.translate(x, y, z)
    fun pos(x: Float, y: Float, z: Float) = positionMatrix.pos(x, y, z)
    fun pos(point: Point3D) = positionMatrix.pos(point)

    fun moveRelativeToOrigin(x: Float, y: Float, z: Float) = positionMatrix.pos(X + x, Y + y, Z + z)
    fun rotate(angle: Float, x: Float, y: Float, z: Float) = rotationMatrix.angle(angle, x, y, z)
    fun defaultPosition() = positionMatrix.pos(position.x, position.y, position.z)

    fun addViewAndProjectionMatrix(viewMatrix: ViewMatrix, projectionMatrix: ProjectionMatrix) {
        this.viewMatrix = viewMatrix
        this.projectionMatrix = projectionMatrix
    }

    fun addCamera(camera: Camera) {
        viewMatrix = camera.viewMatrix
        projectionMatrix = camera.projectionMatrix
    }


}