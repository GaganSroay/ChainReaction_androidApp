package com.thewizard.chainreactiononline20.utils.otherUtils

import android.util.Log

class Logger {

    constructor(message: String) {
        log(message)
    }

    constructor(message: Int) {
        log(message)
    }

    constructor(message: Float) {
        log(message)
    }

    constructor(message: FloatArray) {
        logArray(message)
    }

    constructor(message: IntArray) {
        logArray(message)
    }

    constructor(message: Array<String>) {
        logArray(message)
    }


    companion object {
        const val ON = true

        fun logArray(array: Array<String>) {
            var msg = ""
            for (m in array) msg = "$msg, $m"
            log(msg)
        }


        fun logArray(array: IntArray) {
            var msg = ""
            for (m in array) msg = "$msg, $m"
            log(msg)


        }

        fun logArray(array: FloatArray) {
            var msg = ""
            for (m in array) msg = "$msg, $m"
            log(msg)
        }

        fun log(vararg messages: Float) {
            var msg = ""
            for (m in messages) msg = "$msg, $m"
            log(msg)
        }

        fun log(vararg messages: Int) {
            var msg = ""
            for (m in messages) msg = "$msg, $m"
            log(msg)
        }

        fun log(vararg messages: String) {
            var msg = ""
            for (m in messages) msg = "$msg, $m"
            log(msg)
        }

        fun log(message: String) {
            Log.d("ALL IS WELL", message)
        }
    }

}