package com.thewizard.chainreactiononline20.utils.otherUtils

import android.util.Log

object LoggerConfig {
    const val ON = true;

    fun log(vararg messages: Float) {
        var msg = "";
        for (m in messages) msg = "$msg, $m"
        log(msg)
    }

    fun log(vararg messages: Int) {
        var msg = "";
        for (m in messages) msg = "$msg, $m"
        log(msg)
    }

    fun log(vararg messages: String) {
        var msg = "";
        for (m in messages) msg = "$msg, $m"
        log(msg)
    }

    fun log(message: String) {
        Log.d("ALL IS WELL", message)
    }

}