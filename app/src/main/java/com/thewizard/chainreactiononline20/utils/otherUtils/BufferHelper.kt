package com.thewizard.chainreactiononline20.utils.otherUtils

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

object BufferHelper {
    fun getFloatBuffer(array: FloatArray): FloatBuffer {
        val buff = ByteBuffer.allocateDirect(array.size * 4)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
        buff.put(array)
        buff.position(0)
        return buff
    }

    fun printArray(array: FloatArray) {
        for (i in array)
            print("" + i + " ,")
        println("")
    }
}