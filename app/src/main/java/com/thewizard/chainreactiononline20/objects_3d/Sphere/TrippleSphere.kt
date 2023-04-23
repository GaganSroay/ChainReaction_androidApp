package com.thewizard.chainreactiononline20.objects_3d.Sphere

import android.content.Context
import com.thewizard.chainreactiononline20.R
import com.thewizard.chainreactiononline20.objects_3d.OBJ

class TrippleSphere(context: Context) : OBJ(context, R.raw.ico_tripple_sphere) {

    fun draw(modelMatrix: FloatArray, color: FloatArray) {
        this.color = color
        super.draw(modelMatrix)
    }

    fun draw(modelMatrix: FloatArray, color: FloatArray, ambientColor: FloatArray) {
        this.color = color
        this.ambientColor = ambientColor
        super.draw(modelMatrix)
    }


}