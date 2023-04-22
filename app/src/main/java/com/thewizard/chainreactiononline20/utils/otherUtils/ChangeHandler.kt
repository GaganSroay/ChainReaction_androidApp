package com.thewizard.chainreactiononline20.utils.otherUtils

import java.util.Vector

open class ChangeHandler {

    val listeners = Vector<ChangeListener>()

    fun addListener(changeListener: ChangeListener) {
        listeners.add(changeListener)
        changeListener.onChange()
    }

    fun onChange() {
        for (lis in listeners)
            lis.onChange()
    }

    fun interface ChangeListener {
        fun onChange()
    }
}