package com.thewizard.chainreactiononline20.ui_elements

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.thewizard.chainreactiononline20.R


class NeonButton(context: Context, attrs: AttributeSet) : View(context, attrs) {

    lateinit var baseRect: RectF

    var textPosX = 0f
    var textPosY = 0f

    var buttonDown = false

    var originalWidth = 0
    var originalHeight = 0


    val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.NeonUI, 0, 0)

    val text = typedArray.getText(R.styleable.NeonUI_text).toString()
    val buttonTextSize = typedArray.getDimension(R.styleable.NeonUI_textSize, 10f)
    val radius = typedArray.getDimension(R.styleable.NeonUI_glowSize, 0f)
    val buttonColor = typedArray.getColor(R.styleable.NeonUI_buttonColor, 0)
    val buttonDownColor = changeBrightness(buttonColor, 0.8f)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setBackgroundColor(Color.TRANSPARENT);
        baseRect = RectF(
            radius + width / 6,
            radius + (height - originalHeight) / 2,
            width - radius - width / 6,
            height - radius - (height - originalHeight) / 2
        )
        textPosX = (width / 2).toFloat()
        textPosY = (height / 2) - ((textColor.descent() + textColor.ascent()) / 2)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            if (buttonDown) {
                drawRect(baseRect, downPaint)
                drawText(text, textPosX, textPosY, textColor());
            } else {
                drawRect(baseRect, paint())
                drawRect(baseRect, paint(0.5f))
                drawRect(baseRect, paint(2f))
                drawText(text, textPosX, textPosY, textColor());
                drawText(text, textPosX, textPosY, textColor(1f));
                drawText(text, textPosX, textPosY, textColor(20f));
                drawText(text, textPosX, textPosY, textColor(40f));
                drawText(text, textPosX, textPosY, textColor(80f));
            }

        }

    }

    private fun changeBrightness(color: Int, brightness: Float = 1f): Int = Color.rgb(
        minOf((Color.red(color).toFloat() * brightness).toInt(), 255),
        minOf((Color.green(color).toFloat() * brightness).toInt(), 255),
        minOf((Color.blue(color).toFloat() * brightness).toInt(), 255)
    )


    private val downPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = buttonDownColor
    }

    fun paint(blur: Float = 0f): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = buttonColor
        }
        if (blur != 0f)
            paint.maskFilter = BlurMaskFilter(radius * blur, BlurMaskFilter.Blur.NORMAL)
        return paint
    }


    fun textColor(blur: Float = 0f): Paint {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            textSize = buttonTextSize
            color = Color.WHITE
        }
        if (blur != 0f)
            paint.maskFilter = BlurMaskFilter(blur, BlurMaskFilter.Blur.NORMAL)
        return paint
    }

    private val textColor = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = buttonTextSize
        color = Color.WHITE
    }


    override fun performClick(): Boolean {
        return super.performClick()
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                buttonDown = true
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                buttonDown = false
                performClick()
                invalidate()
            }
        }
        return true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val parent = parent as ViewGroup
        val params = layoutParams

        originalHeight = params.height
        originalWidth = params.width

        params.height = params.height * 2
        params.width = params.width * 2
        y = -originalHeight.toFloat() / 2
        parent.updateViewLayout(this, params)
    }


}