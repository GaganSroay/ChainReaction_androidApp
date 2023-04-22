package com.thewizard.chainreactiononline20.ui_elements

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.thewizard.chainreactiononline20.R

class NeonTextView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    var text = ""
    var radius = 0f
    var textColor: Int

    var buttonDownColor: Int

    lateinit var baseRect: RectF

    var buttonTextSize: Float = 0f
    var textPosX = 0f
    var textPosY = 0f

    var buttonDown = false


    init {
        context.theme
            .obtainStyledAttributes(attrs, R.styleable.NeonUI, 0, 0)
            .apply {
                radius = getDimension(R.styleable.NeonUI_glowSize, 0f)
                buttonTextSize = getDimension(R.styleable.NeonUI_textSize, 10f)
                text = getString(R.styleable.NeonUI_text).toString()
                textColor = getColor(R.styleable.NeonUI_textColor, 0)

                val brightness = 0.8f
                buttonDownColor = Color.rgb(
                    (Color.red(textColor).toFloat() * brightness).toInt(),
                    (Color.green(textColor).toFloat() * brightness).toInt(),
                    (Color.blue(textColor).toFloat() * brightness).toInt()
                )
            }
        this.setBackgroundColor(Color.TRANSPARENT)

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
            drawText(text, textPosX, textPosY, textPaint(radius / 8))
            drawText(text, textPosX, textPosY, textPaint(radius / 2))
            drawText(text, textPosX, textPosY, textPaint(radius))

        }

    }

    fun textPaint(blur: Float = 0f): Paint {
        val paint = textPaint
        if (blur != 0f)
            paint.maskFilter = BlurMaskFilter(blur, BlurMaskFilter.Blur.NORMAL)
        return paint
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = buttonTextSize
        color = textColor
    }
}