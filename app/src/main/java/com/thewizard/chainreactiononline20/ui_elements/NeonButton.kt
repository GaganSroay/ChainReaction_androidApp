package com.thewizard.chainreactiononline20.ui_elements

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import com.thewizard.chainreactiononline20.utils.otherUtils.Logger


class NeonButton(context: Context, attrs: AttributeSet) : NeonView(context, attrs) {

    lateinit var baseRect: RectF

    var textPosX = 0f
    var textPosY = 0f
    var buttonDown = false



    val buttonDownColor = changeBrightness(buttonColor, 0.8f)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setBackgroundColor(Color.TRANSPARENT)
        baseRect = RectF(
            glowSize + width / 6,
            glowSize + (height) / 2,
            width - glowSize - width / 6,
            height - glowSize - (height) / 2
        )
        textPosX = (width / 2).toFloat()
        textPosY = (height / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            if (buttonDown) {
                drawRect(baseRect, downPaint)
                drawText(text, textPosX, textPosY, textPaint())
            } else {
                drawRect(baseRect, buttonPaint())
                drawRect(baseRect, buttonPaint(0.5f))
                drawRect(baseRect, buttonPaint(2f))
                drawText(text, textPosX, textPosY, textPaint())
                drawText(text, textPosX, textPosY, textPaint(1f))
                drawText(text, textPosX, textPosY, textPaint(20f))
                drawText(text, textPosX, textPosY, textPaint(40f))
                drawText(text, textPosX, textPosY, textPaint(80f))
            }

        }

    }



    private val downPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = buttonDownColor
    }

    fun buttonPaint(blur: Float = 0f): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = buttonColor
        }
        if (blur != 0f)
            paint.maskFilter = BlurMaskFilter(glowSize * blur, BlurMaskFilter.Blur.NORMAL)
        return paint
    }


    fun textPaint(blur: Float = 0f): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            textSize = this@NeonButton.textSize
            color = Color.WHITE
        }
        if (blur != 0f)
            paint.maskFilter = BlurMaskFilter(blur, BlurMaskFilter.Blur.NORMAL)
        return paint
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = this@NeonButton.textSize
        color = Color.WHITE
    }






    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Logger("DOWN")
                buttonDown = true
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                Logger("UP")
                buttonDown = false
                performClick()
                invalidate()
            }
        }
        return true
    }


}