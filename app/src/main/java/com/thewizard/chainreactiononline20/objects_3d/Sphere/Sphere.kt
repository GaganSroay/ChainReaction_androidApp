package com.thewizard.chainreactiononline20.objects_3d.Sphere

import android.content.Context
import com.thewizard.chainreactiononline20.R
import com.thewizard.chainreactiononline20.objects_3d.OBJ

class Sphere(context: Context) : OBJ(context, R.raw.ico_sphere) {
    override fun draw(modelMatrix: FloatArray) {
        super.draw(modelMatrix)
    }

    public fun draw(modelMatrix: FloatArray, color: FloatArray) {
        this.color = color
        super.draw(modelMatrix)
    }


}