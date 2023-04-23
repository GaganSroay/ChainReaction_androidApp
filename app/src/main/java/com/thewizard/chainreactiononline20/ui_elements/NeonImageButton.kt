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
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.thewizard.chainreactiononline20.utils.otherUtils.Logger


class NeonImageButton(context: Context, attrs: AttributeSet?) : NeonView(context, attrs) {

    lateinit var bitmap: Bitmap
    lateinit var originalBitmap: Bitmap
    lateinit var blurredBitmap: Bitmap
    lateinit var blurredMoreBitmap: Bitmap
    lateinit var lastblurredMoreBitmap: Bitmap

    lateinit var mainRectF: RectF
    var buttonDown = false

    var glowColor = changeBrightness(buttonColor, 0.8f)
    val drawGlow = true

    lateinit var canvas: Canvas

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setBackgroundColor(Color.TRANSPARENT)
        bitmap = AppCompatResources.getDrawable(context, src)!!.toBitmap(w, h)
        originalBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        if (drawGlow) {
            blurredBitmap = blurRecursion(originalBitmap, 25f, 2)
            blurredMoreBitmap = blurRecursion(originalBitmap, 25f, 5)
            lastblurredMoreBitmap = blurRecursion(originalBitmap, 25f, 10)
        }

        mainRectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas
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


    val bitmapPaint: Paint
        get() {
            val paint = Paint()
            paint.colorFilter = topColorFilter(buttonColor)
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
            paint.colorFilter = topColorFilter(buttonColor)
            paint.alpha = 200
            return paint
        }

    fun topColorFilter(color: Int) = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)


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