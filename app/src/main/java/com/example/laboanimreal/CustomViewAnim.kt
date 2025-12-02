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
import com.example.laboanimreal.MainActivity
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
    var sides = 3
        get() = field
        set(value) {
            field = value
            invalidate()
        }
    var rotationPerSecond = 30f
    var time = 0f
        get() = field
        set(value) {
            field = value
            invalidate()
        }


    init {
        myPaint.color = Color.BLUE
        myPaint.strokeWidth = 3f
        myPaint.style = Paint.Style.STROKE

        val attributes = context.theme.obtainStyledAttributes(attrs, com.example.laboanimreal.R.styleable.CustomViewAnim, defStyle, 0)
        try {

            time = attributes.getFloat(com.example.laboanimreal.R.styleable.CustomViewAnim_time, 0f)
            sides = attributes.getInt(com.example.laboanimreal.R.styleable.CustomViewAnim_sides, 3)

            if (sides > 7) sides = 7
            if (sides < 3) sides = 3

            //attributes.getDimensionPixelSize(R.styleable.CustomView_size, 0)

        } finally {

            attributes.recycle()
        }

    }

    override fun onDraw(canvas: Canvas) {
        canvas.translate(cx,cy)
        canvas.rotate(-90f)
        canvas.rotate(time * rotationPerSecond)
        drawPolygon(canvas, radius/3, sides)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("MainActivity", "Touched")
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        cx = w * 0.5f
        cy = h * 0.5f
        radius = min(cx,cy)
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
            if (sides > 3) {
                canvas.rotate(time * rotationPerSecond)
                drawPolygon(canvas, size / 2, sides - 1)
            }
            canvas.restore()

            canvas.rotate(centralAngle)
        }
    }

    public fun update() {
        time += MainActivity.SEC_PER_FRAME
    }

}