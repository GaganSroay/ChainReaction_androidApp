package com.thewizard.chainreactiononline20.objects_3d.Sphere

import android.content.Context
import com.thewizard.chainreactiononline20.R
import com.thewizard.chainreactiononline20.objects_3d.OBJ

class DoubleSphere(context: Context) : OBJ(context, R.raw.ico_double_sphere) {
    override fun draw(modelMatrix: FloatArray) {
        super.draw(modelMatrix)
    }

    fun draw(modelMatrix: FloatArray, color: FloatArray) {
        this.color = color
        super.draw(modelMatrix)
    }

}