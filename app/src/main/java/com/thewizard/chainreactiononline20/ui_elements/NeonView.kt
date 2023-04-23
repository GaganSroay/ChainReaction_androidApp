package com.thewizard.chainreactiononline20.ui_elements

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.thewizard.chainreactiononline20.R

open class NeonView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.NeonUI, 0, 0)

    open val text: String
        get() {
            if (typedArray.hasValue(R.styleable.NeonUI_text))
                return typedArray.getText(R.styleable.NeonUI_text).toString()
            return ""
        }

    val textSize = typedArray.getDimension(R.styleable.NeonUI_textSize, 10f)
    val glowSize = typedArray.getDimension(R.styleable.NeonUI_glowSize, 0f)
    val buttonColor = typedArray.getColor(R.styleable.NeonUI_buttonColor, 0)
    val src = typedArray.getResourceId(R.styleable.NeonUI_src, R.drawable.ic_launcher_foreground)
    val textColor = typedArray.getColor(R.styleable.NeonUI_textColor, 0)

    fun changeBrightness(color: Int, brightness: Float = 1f): Int = Color.rgb(
        minOf((Color.red(color).toFloat() * brightness).toInt(), 255),
        minOf((Color.green(color).toFloat() * brightness).toInt(), 255),
        minOf((Color.blue(color).toFloat() * brightness).toInt(), 255)
    )
}