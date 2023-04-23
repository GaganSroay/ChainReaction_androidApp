package com.thewizard.chainreactiononline20.utils.objUtils

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import com.thewizard.chainreactiononline20.utils.otherUtils.Logger

object TextureHelper {
    fun loadTexture(context: Context, resourceId: Int): Int {
        val textureHandle = IntArray(1)
        GLES20.glGenTextures(1, textureHandle, 0)
        Logger("GEN HERE   " + textureHandle[0])
        if (textureHandle[0] != 0) {

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0])
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_NEAREST.toFloat()
            )
            GLES20.glTexParameterf(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR.toFloat()
            )
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT)

            val inputStream = context.resources.openRawResource(resourceId)
            val bitmapTexture = BitmapFactory.decodeStream(inputStream)
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmapTexture, 0)

            bitmapTexture.recycle()
        }
        if (textureHandle[0] == 0) {

            throw RuntimeException("Error loading texture.    " + GLES20.glGetError())
        }
        return textureHandle[0]
    }
}