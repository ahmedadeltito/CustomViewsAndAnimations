package com.egdroid.customviewsandanimations.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.egdroid.customviewsandanimations.PizzaStatus
import com.egdroid.customviewsandanimations.R

/**
 * Created at Tito on 2019-07-20
 */

class NumberOfPizzasView : ConstraintLayout {

    private var incrementBtn: Button? = null
    private var decrementBtn: Button? = null
    private var numOfPizzasTv: TextView? = null

    private var numOfPizzas = 0

    var setOnNumberOfPizzasListener: NumberOfPizzasListener? = null

    init {
        inflate(context, R.layout.num_of_pizzas_view, this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        incrementBtn = findViewById(R.id.increment_num_of_pizzas_btn)
        decrementBtn = findViewById(R.id.decrement_num_of_pizzas_btn)
        numOfPizzasTv = findViewById(R.id.num_of_pizzas_tv)

        incrementBtn?.setOnClickListener {
            numOfPizzas++
            updateView()
            setOnNumberOfPizzasListener?.pizzaStatus(PizzaStatus.INCREMENTED)
        }
        decrementBtn?.setOnClickListener {
            numOfPizzas--
            updateView()
            setOnNumberOfPizzasListener?.pizzaStatus(PizzaStatus.DECREMENTED)
        }

        updateView()

    }

    private fun updateView() {

        when (numOfPizzas) {
            0 -> {
                decrementBtn?.isEnabled = false
                incrementBtn?.isEnabled = true
            }
            in 1..8 -> {
                decrementBtn?.isEnabled = true
                incrementBtn?.isEnabled = true
            }
            else -> {
                decrementBtn?.isEnabled = true
                incrementBtn?.isEnabled = false
            }
        }

        numOfPizzasTv?.text = numOfPizzas.toString()
    }

}

interface NumberOfPizzasListener {
    fun pizzaStatus(pizzaStatus: PizzaStatus)
}