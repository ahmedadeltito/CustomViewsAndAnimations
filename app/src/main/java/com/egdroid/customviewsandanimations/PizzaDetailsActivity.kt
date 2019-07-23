package com.egdroid.customviewsandanimations

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PizzaDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pizza_details)
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, PizzaDetailsActivity::class.java))
        }

    }
}
