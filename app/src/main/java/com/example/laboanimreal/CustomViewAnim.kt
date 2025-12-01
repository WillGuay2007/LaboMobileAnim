package com.example.laboanimation

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.min

class CustomViewAnim @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private var myPaint = Paint()
    var cx = 0f
    var cy = 0f
    var radius = 1f

    init {
        myPaint.color = Color.BLUE
        myPaint.strokeWidth = 3f
        myPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(cx,cy,radius,myPaint)
        drawTriangle(canvas, 3f)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("MainActivity", "Touched")
        invalidate()
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cx = w * 0.5f
        cy = h * 0.5f
        radius = min(cx,cy)
    }

    private fun drawTriangle(canvas: Canvas, size:Float) {



        repeat(3) {

            canvas.save()
            canvas.translate(size, 0f)
            canvas.rotate(180f - 30)
            canvas.drawLine(0f, 0f, size, 0f, myPaint)
            canvas.rotate(60f)
            canvas.drawLine(0f, 0f, size, 0f, myPaint)
            canvas.restore()

            canvas.rotate(120f)
        }
    }


    private fun drawPolygon(canvas: Canvas, size:Float, sides:Int) {

        val centralAngle = 360f / sides
        val cornerAngle = 180f - centralAngle

        repeat(sides) {

            canvas.save()
            canvas.translate(size, 0f)
            canvas.rotate(180f - cornerAngle / 2)
            canvas.drawLine(0f, 0f, size, 0f, myPaint)
            canvas.rotate(cornerAngle)
            canvas.drawLine(0f, 0f, size, 0f, myPaint)
            canvas.restore()

            canvas.rotate(centralAngle)
        }
    }
}