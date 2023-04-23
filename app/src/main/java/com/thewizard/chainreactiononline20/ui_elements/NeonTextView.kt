package com.thewizard.chainreactiononline20.ui_elements

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet

class NeonTextView(context: Context, attrs: AttributeSet?) : NeonView(context, attrs) {

    var textPosX = 0f
    var textPosY = 0f

    override var text: String = super.text
        set(value) {
            field = value
            invalidate()
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        textPosX = (width / 2).toFloat()
        textPosY = (height / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            drawText(text, textPosX, textPosY, textPaint())
            drawText(text, textPosX, textPosY, textPaint(glowSize / 8))
            drawText(text, textPosX, textPosY, textPaint(glowSize / 2))
            drawText(text, textPosX, textPosY, textPaint(glowSize))

        }
    }

    fun textPaint(blur: Float = 0f): Paint {
        val paint = textPaint
        if (blur == 0f) paint.maskFilter = null
        else paint.maskFilter = BlurMaskFilter(blur, BlurMaskFilter.Blur.NORMAL)
        return paint
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = this@NeonTextView.textSize
        color = textColor
    }
}