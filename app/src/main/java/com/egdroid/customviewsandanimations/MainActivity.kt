package com.egdroid.customviewsandanimations

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.egdroid.customviewsandanimations.extensions.*
import com.egdroid.customviewsandanimations.widget.PizzaListItem
import com.egdroid.customviewsandanimations.widget.PizzaListItemClickedListener
import com.egdroid.customviewsandanimations.widget.PizzaView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), PizzaListItemClickedListener {

    private val smallPizzaListItem by lazy {
        findViewById<PizzaListItem>(R.id.small_pizza_list_item)
    }
    private val mediumPizzaListItem by lazy {
        findViewById<PizzaListItem>(R.id.medium_pizza_list_item)
    }
    private val largePizzaListItem by lazy {
        findViewById<PizzaListItem>(R.id.large_pizza_list_item)
    }

    private var numOfPizzas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        smallPizzaListItem.setPizzaSize(PizzaSize.SMALL)
        mediumPizzaListItem.setPizzaSize(PizzaSize.MEDIUM)
        largePizzaListItem.setPizzaSize(PizzaSize.LARGE)

        smallPizzaListItem.setOnPizzaListItemClickedListener = this
        mediumPizzaListItem.setOnPizzaListItemClickedListener = this
        largePizzaListItem.setOnPizzaListItemClickedListener = this

        checkout_btn.setOnClickListener {
            animateAreYouSureView(isAnimatedToUp = true)
        }
        no_btn.setOnClickListener {
            animateAreYouSureView(isAnimatedToUp = false)
        }

        checkoutBtnVisibility()
    }

    private fun animateAreYouSureView(isAnimatedToUp: Boolean) {
        are_you_sure_view.visibility = View.VISIBLE
        are_you_sure_view.objectAnimator(
            propertyName = "translationY",
            durationToAnimate = ANIMATION_DURATION,
            startValue = if (isAnimatedToUp)
                are_you_sure_view.height.toFloat()
            else
                0F,
            endValue = if (isAnimatedToUp)
                0f
            else
                are_you_sure_view.height.toFloat()
        ).start()
    }

    private fun setupPizzaViewToAnimate(pizzaView: PizzaView): PizzaView {
        val pizzaViewToAnimate = PizzaView(this)

        val layoutParams = pizzaView.layoutParams
        layoutParams.height = pizzaView.measuredHeight
        layoutParams.width = pizzaView.measuredWidth
        pizzaViewToAnimate.layoutParams = layoutParams

        pizzaViewToAnimate.setPizzaSize(pizzaSize = pizzaView.getPizzaSize())

        return pizzaViewToAnimate
    }

    private fun checkoutBtnVisibility() {
        if (numOfPizzas > 0)
            checkout_btn.visible()
        else
            checkout_btn.invisible()
    }

    override fun onPizzaViewClicked(pizzaView: PizzaView) {
        pizzaView.rotateAnimation(context = this@MainActivity)
    }

    override fun pizzaStatus(pizzaView: PizzaView, pizzaStatus: PizzaStatus) {

        val xAnimator: ObjectAnimator
        val yAnimator: ObjectAnimator
        val scaleDownX: ObjectAnimator
        val scaleDownY: ObjectAnimator
        val alphaAnimation: ObjectAnimator

        val pizzaViewPosition = pizzaView.getPositionOnScreen()
        val shoppingCartPosition = shopping_cart_iv.getPositionOnScreen()

        val pizzaViewToAnimate = setupPizzaViewToAnimate(pizzaView)
        activity_main_parent_view.addView(pizzaViewToAnimate)

        when (pizzaStatus) {
            PizzaStatus.INCREMENTED -> {
                numOfPizzas = numOfPizzas.inc()

                xAnimator = pizzaViewToAnimate.objectAnimator(
                    propertyName = "translationX",
                    durationToAnimate = ANIMATION_DURATION,
                    startValue = pizzaViewPosition[0].toFloat(),
                    endValue = shoppingCartPosition[0].toFloat()
                )
                yAnimator = pizzaViewToAnimate.objectAnimator(
                    propertyName = "translationY",
                    durationToAnimate = ANIMATION_DURATION,
                    startValue = pizzaViewPosition[1].toFloat() - shopping_cart_iv.measuredHeight,
                    endValue = shoppingCartPosition[1].toFloat() - shopping_cart_iv.measuredHeight
                )

                scaleDownX = ObjectAnimator.ofFloat(
                    pizzaViewToAnimate,
                    "scaleX",
                    0.5f
                )
                scaleDownY = ObjectAnimator.ofFloat(
                    pizzaViewToAnimate,
                    "scaleY",
                    0.5f
                )

                alphaAnimation = pizzaViewToAnimate.objectAnimator(
                    propertyName = "alpha",
                    durationToAnimate = ANIMATION_DURATION,
                    startValue = 1F,
                    endValue = 0F
                )
            }
            PizzaStatus.DECREMENTED -> {
                numOfPizzas = numOfPizzas.dec()

                xAnimator = pizzaViewToAnimate.objectAnimator(
                    propertyName = "translationX",
                    durationToAnimate = ANIMATION_DURATION,
                    startValue = shoppingCartPosition[0].toFloat(),
                    endValue = pizzaViewPosition[0].toFloat()
                )
                yAnimator = pizzaViewToAnimate.objectAnimator(
                    propertyName = "translationY",
                    durationToAnimate = ANIMATION_DURATION,
                    startValue = shoppingCartPosition[1].toFloat() - shopping_cart_iv.measuredHeight,
                    endValue = pizzaViewPosition[1].toFloat() - shopping_cart_iv.measuredHeight
                )

                scaleDownX = ObjectAnimator.ofFloat(
                    pizzaViewToAnimate,
                    "scaleX",
                    1f
                )
                scaleDownY = ObjectAnimator.ofFloat(
                    pizzaViewToAnimate,
                    "scaleY",
                    1f
                )

                alphaAnimation = pizzaViewToAnimate.objectAnimator(
                    propertyName = "alpha",
                    durationToAnimate = ANIMATION_DURATION,
                    startValue = 0F,
                    endValue = 1F
                )
            }
        }

        AnimatorSet().apply {
            play(xAnimator).with(yAnimator).with(scaleDownX).with(scaleDownY).with(alphaAnimation)

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    activity_main_parent_view.removeView(pizzaViewToAnimate)
                }
            })

            start()
        }

        if (numOfPizzas > 0) {
            shopping_cart_counter_parent_view.visibility = View.VISIBLE
            shopping_cart_counter_tv.text = numOfPizzas.toString()
        } else {
            shopping_cart_counter_parent_view.visibility = View.GONE
        }

        checkoutBtnVisibility()

        shopping_cart_counter_parent_view.animateShoppingCart()
    }

    companion object {
        const val ANIMATION_DURATION = 500L
    }
}
