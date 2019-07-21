package com.egdroid.customviewsandanimations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.egdroid.customviewsandanimations.widget.PizzaListItem

class MainActivity : AppCompatActivity() {

    private val smallPizzaListItem by lazy {
        findViewById<PizzaListItem>(R.id.small_pizza_list_item)
    }
    private val mediumPizzaListItem by lazy {
        findViewById<PizzaListItem>(R.id.medium_pizza_list_item)
    }
    private val largePizzaListItem by lazy {
        findViewById<PizzaListItem>(R.id.large_pizza_list_item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        smallPizzaListItem.setPizzaSize(PizzaSize.SMALL)
        mediumPizzaListItem.setPizzaSize(PizzaSize.MEDIUM)
        largePizzaListItem.setPizzaSize(PizzaSize.LARGE)
    }
}
