package com.egdroid.customviewsandanimations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_pizza_details.*

class PizzaDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_details)

        val pizzaTitle = intent?.getStringExtra(PIZZA_TITLE_EXTRAS)
        val pizzaSize = intent?.getSerializableExtra(PIZZA_SIZE_EXTRAS) as PizzaSize

        pizza_details_icon.setPizzaSize(pizzaSize = pizzaSize)
        pizza_details_title_tv.text = pizzaTitle
    }

    companion object {

        private const val PIZZA_TITLE_EXTRAS = "PIZZA_TITLE_EXTRAS"
        private const val PIZZA_SIZE_EXTRAS = "PIZZA_SIZE_EXTRAS"

        fun startIntent(
            context: Context,
            pizzaTitle: String?,
            pizzaSize: PizzaSize?
        ): Intent {
            val intent = Intent(context, PizzaDetailsActivity::class.java)
            intent.putExtra(PIZZA_TITLE_EXTRAS, pizzaTitle)
            intent.putExtra(PIZZA_SIZE_EXTRAS, pizzaSize)
            return intent
        }
    }
}
