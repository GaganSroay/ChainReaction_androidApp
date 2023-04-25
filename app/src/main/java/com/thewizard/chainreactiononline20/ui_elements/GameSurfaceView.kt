package com.thewizard.chainreactiononline20.ui_elements

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet


class GameSurfaceView : GLSurfaceView {


    constructor(context: Context) : super(context) {
        setEGLContextClientVersion(2)
        preserveEGLContextOnPause = true
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        setEGLContextClientVersion(2)
        preserveEGLContextOnPause = true
        setEGLConfigChooser(8, 8, 8, 0, 16, 0)
    }


    override fun setRenderer(renderer: Renderer) {
        super.setRenderer(renderer)
    }
}