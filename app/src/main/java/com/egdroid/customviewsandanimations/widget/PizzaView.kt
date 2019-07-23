package com.egdroid.customviewsandanimations.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.egdroid.customviewsandanimations.PizzaSize
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
    private lateinit var imagePaint: Paint

    private lateinit var pepperoniPizzaBitmap: Bitmap

    private var numOfSlices = 4
    private var color = Color.YELLOW
    private var strokeWidth = 4

    private var pizzaSize: PizzaSize? = null
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
            numOfSlices = array.getInt(
                R.styleable.PizzaView_num_slices, numOfSlices
            )
            if (numOfSlices !in slicesRange)
                numOfSlices = 4

            array.recycle()
        }

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth.toFloat()
        paint.color = color

        imagePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        pepperoniPizzaBitmap = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(resources, R.drawable.pepperoni_pizza),
            width,
            height,
            false
        )
    }

    override fun onDraw(canvas: Canvas) {
        val width = width - paddingLeft - paddingRight
        val height = height - paddingTop - paddingBottom

        val cx = width / 2F + paddingLeft
        val cy = height / 2F + paddingTop

        val diameter = min(width, height) - paint.strokeWidth
        val radius = diameter / 2

        canvas.drawBitmap(pepperoniPizzaBitmap, 0F, 0F, imagePaint)

        canvas.drawCircle(cx, cy, radius, paint)
        drawPizzaCuts(canvas, cx, cy, radius)
    }

    private fun drawPizzaCuts(canvas: Canvas, cx: Float, cy: Float, radius: Float) {
        val degrees = 360f / numOfSlices

        canvas.save()

        for (index in 0 until numOfSlices) {
            canvas.rotate(degrees, cx, cy)
            canvas.drawLine(cx, cy, cx, cy - radius, paint)
        }

        canvas.restore()
    }

    fun setPizzaSize(pizzaSize: PizzaSize?) {
        this.pizzaSize = pizzaSize
        numOfSlices = when (pizzaSize) {
            PizzaSize.SMALL -> {
                setPizzaColor(ContextCompat.getColor(context, R.color.red))
                4
            }
            PizzaSize.MEDIUM -> {
                setPizzaColor(ContextCompat.getColor(context, R.color.green))
                6
            }
            PizzaSize.LARGE -> {
                setPizzaColor(ContextCompat.getColor(context, R.color.blue))
                8
            }
            else -> 4
        }
        invalidate()
    }

    fun getPizzaSize() = pizzaSize

    private fun setPizzaColor(pizzaColor: Int) {
        color = pizzaColor
        paint.color = color
    }
}