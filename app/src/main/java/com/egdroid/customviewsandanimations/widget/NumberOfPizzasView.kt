package com.egdroid.customviewsandanimations.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.egdroid.customviewsandanimations.PizzaSize
import com.egdroid.customviewsandanimations.R

/**
 * Created at Tito on 2019-07-20
 */

class NumberOfPizzasView : ConstraintLayout {

    private var pizzaView: PizzaView? = null
    private var pizzaSizeTv: TextView? = null
    private var incrementBtn: Button? = null
    private var decrementBtn: Button? = null
    private var numOfPizzasTv: TextView? = null

    private var pizzaSize: PizzaSize = PizzaSize.SMALL
    private var pizzaColor: Int = Color.BLACK

    private var numOfPizzas = 1

    init {
        inflate(context, R.layout.num_of_pizzas_view, this)
    }

    constructor(context: Context) : super(context) {
        init(
            context = context,
            attrs = null
        )
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(
            context = context,
            attrs = attrs
        )
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(
            context = context,
            attrs = attrs
        )
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        pizzaView = findViewById(R.id.pizza_view)
        pizzaSizeTv = findViewById(R.id.pizza_size_tv)
        incrementBtn = findViewById(R.id.increment_num_of_pizzas_btn)
        decrementBtn = findViewById(R.id.decrement_num_of_pizzas_btn)
        numOfPizzasTv = findViewById(R.id.num_of_pizzas_tv)

        incrementBtn?.setOnClickListener {
            numOfPizzas++
            updateView()
        }
        decrementBtn?.setOnClickListener {
            numOfPizzas--
            updateView()
        }

        pizzaView?.setPizzaSize(pizzaSize)
        pizzaView?.setPizzaColor(pizzaColor)

        updateView()

    }

    private fun init(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(
                attrs, R.styleable.NumberOfPizzasView
            )

            pizzaSize = array.getInt(
                R.styleable.NumberOfPizzasView_pizza_size, pizzaSize.value
            ).let {
                PizzaSize.fromInt(it)
            }

            pizzaColor = array.getColor(
                R.styleable.NumberOfPizzasView_pizza_color, pizzaColor
            )

            array.recycle()
        }
    }

    private fun updateView() {
        when (numOfPizzas) {
            1 -> {
                decrementBtn?.isEnabled = false
                incrementBtn?.isEnabled = true
            }
            in 2..8 -> {
                decrementBtn?.isEnabled = true
                incrementBtn?.isEnabled = true
            }
            else -> {
                decrementBtn?.isEnabled = true
                incrementBtn?.isEnabled = false
            }
        }

        pizzaSizeTv?.text = when (pizzaSize) {
            PizzaSize.SMALL ->
                resources.getString(R.string.small)
            PizzaSize.MEDIUM ->
                resources.getString(R.string.medium)
            PizzaSize.LARGE ->
                resources.getString(R.string.large)
        }

        numOfPizzasTv?.text = numOfPizzas.toString()

    }

}