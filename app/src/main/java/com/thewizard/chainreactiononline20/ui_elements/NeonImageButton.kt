package com.thewizard.chainreactiononline20.ui_elements

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.RectF
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.thewizard.chainreactiononline20.R


class NeonImageButton(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var imgRes = 0
    lateinit var bitmap: Bitmap
    lateinit var originalBitmap: Bitmap
    lateinit var blurredBitmap: Bitmap
    lateinit var blurredMoreBitmap: Bitmap
    lateinit var lastblurredMoreBitmap: Bitmap
    lateinit var mainRectF: RectF


    var color = 0
    var glowColor = 0

    var buttonDown = false

    val drawGlow = true

    init {
        context.theme
            .obtainStyledAttributes(attrs, R.styleable.NeonUI, 0, 0)
            .apply {
                imgRes = getResourceId(R.styleable.NeonUI_src, R.drawable.ic_launcher_foreground)
                color = getColor(R.styleable.NeonUI_buttonColor, Color.WHITE)
                val brightness = 0.8f
                glowColor = Color.rgb(
                    (Color.red(color).toFloat() * brightness).toInt(),
                    (Color.green(color).toFloat() * brightness).toInt(),
                    (Color.blue(color).toFloat() * brightness).toInt()
                )
            }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = AppCompatResources.getDrawable(context, imgRes)!!.toBitmap(w, h)
        originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        if (drawGlow) {
            blurredBitmap = blurRecursion(originalBitmap, 25f, 2)
            blurredMoreBitmap = blurRecursion(originalBitmap, 25f, 5)
            lastblurredMoreBitmap = blurRecursion(originalBitmap, 25f, 10)
        }

        mainRectF = RectF(
            0f,
            0f,
            width.toFloat(),
            height.toFloat()
        )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {
            if (buttonDown)
                drawBitmap(bitmap, null, mainRectF, bitmapDownPaint)
            else {
                if (drawGlow) {
                    drawBitmap(blurredBitmap, null, mainRectF, blurBitmapPaint)
                    drawBitmap(blurredMoreBitmap, null, mainRectF, blurBitmapPaint)
                    drawBitmap(lastblurredMoreBitmap, null, mainRectF, blurBitmapPaint)
                }
                drawBitmap(originalBitmap, null, mainRectF, bitmapPaint)
            }
        }


    }


    override fun performClick(): Boolean {
        return super.performClick()
    }


    val bitmapPaint: Paint
        get() {
            val paint = Paint()
            paint.colorFilter = topColorFilter(color)
            return paint
        }

    val blurBitmapPaint: Paint
        get() {
            val paint = Paint()
            paint.colorFilter = topColorFilter(glowColor)
            paint.alpha = 200
            return paint
        }


    val bitmapDownPaint: Paint
        get() {
            val paint = Paint()
            paint.colorFilter = topColorFilter(color)
            paint.alpha = 200
            return paint
        }

    fun topColorFilter(color: Int) = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)

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

    fun blurRecursion(bitmap: Bitmap, radius: Float, i: Int): Bitmap {
        if (i > 0) {
            return blurRecursion(
                blur(bitmap, radius),
                radius,
                i - 1
            )
        }
        return bitmap
    }


    fun blur(image: Bitmap, radius: Float): Bitmap {
        val outputBitmap = Bitmap.createBitmap(image)
        val renderScript = RenderScript.create(context)
        val tmpIn = Allocation.createFromBitmap(renderScript, image)
        val tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap)

        val theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        theIntrinsic.setRadius(radius)
        theIntrinsic.setInput(tmpIn)
        theIntrinsic.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)
        return outputBitmap
    }


}