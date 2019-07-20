package com.egdroid.customviewsandanimations.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.egdroid.customviewsandanimations.R
import kotlin.math.min

/**
 * Created at Tito on 2019-07-20
 *
 * PizzaView is a custom view that draws Pizza with different slices starting from 4 to 8
 * 4 slices mean Small
 * 6 slices mean Medium
 * 8 slices mean Large
 */

class PizzaView : View {

    private lateinit var paint: Paint

    private var numSlices = 4
    private var color = Color.BLACK
    private var strokeWidth = 4

    private val slicesRange = 4..8

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        strokeWidth = 4
        color = Color.YELLOW

        if (attrs != null) {
            val array = context.obtainStyledAttributes(
                attrs, R.styleable.PizzaView
            )

            strokeWidth = array.getDimensionPixelSize(
                R.styleable.PizzaView_stroke_width, strokeWidth
            )
            color = array.getColor(
                R.styleable.PizzaView_color, color
            )
            numSlices = array.getInt(
                R.styleable.PizzaView_num_slices, numSlices
            )
            if (numSlices !in slicesRange)
                numSlices = 4

            array.recycle()
        }

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth.toFloat()
        paint.color = color
    }

    override fun onDraw(canvas: Canvas) {
        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom

        val cx = width / 2F + paddingLeft
        val cy = height / 2F + paddingTop

        val diameter = min(width, height) - paint.strokeWidth
        val radius = diameter / 2

        canvas.drawCircle(cx, cy, radius, paint)
        drawPizzaCuts(canvas, cx, cy, radius)
    }

    private fun drawPizzaCuts(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val degrees = 360f / numSlices

        canvas.save()

        for (i in 0 until numSlices) {
            canvas.rotate(degrees, cx, cy)
            canvas.drawLine(cx, cy, cx, cy - radius, paint)
        }

        canvas.restore()
    }
}