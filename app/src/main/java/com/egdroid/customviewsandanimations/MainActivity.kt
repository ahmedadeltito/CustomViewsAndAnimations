package com.egdroid.customviewsandanimations

import android.animation.*
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.egdroid.customviewsandanimations.extensions.getPositionOnScreen
import com.egdroid.customviewsandanimations.widget.OnPizzaListItemClickedListener
import com.egdroid.customviewsandanimations.widget.PizzaListItem
import com.egdroid.customviewsandanimations.widget.PizzaView

class MainActivity : AppCompatActivity() {

    private val parentLayout by lazy {
        findViewById<ConstraintLayout>(R.id.activity_main_parent_view)
    }
    private val smallPizzaListItem by lazy {
        findViewById<PizzaListItem>(R.id.small_pizza_list_item)
    }
    private val mediumPizzaListItem by lazy {
        findViewById<PizzaListItem>(R.id.medium_pizza_list_item)
    }
    private val largePizzaListItem by lazy {
        findViewById<PizzaListItem>(R.id.large_pizza_list_item)
    }
    private val shoppingCartIv by lazy {
        findViewById<ImageView>(R.id.shopping_cart_iv)
    }
    private val shoppingCartCounterParentView by lazy {
        findViewById<FrameLayout>(R.id.shopping_cart_counter_parent_view)
    }
    private val shoppingCartCounterTv by lazy {
        findViewById<TextView>(R.id.shopping_cart_counter_tv)
    }
    private val areYouSureParentView by lazy {
        findViewById<ConstraintLayout>(R.id.are_you_sure_view)
    }
    private val checkoutBtn by lazy {
        findViewById<Button>(R.id.checkout_btn)
    }
    private val noBtn by lazy {
        findViewById<Button>(R.id.no_btn)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        smallPizzaListItem.setPizzaSize(PizzaSize.SMALL)
        mediumPizzaListItem.setPizzaSize(PizzaSize.MEDIUM)
        largePizzaListItem.setPizzaSize(PizzaSize.LARGE)

        smallPizzaListItem.onPizzaListItemClickedListener = object : OnPizzaListItemClickedListener {
            override fun setOnPizzaViewClicked(pizzaView: PizzaView) {

                val pizzaViewPosition = pizzaView.getPositionOnScreen()
                val shoppingCartPosition = shoppingCartIv.getPositionOnScreen()

                val pizzaViewToAnimate = setupPizzaViewToAnimate(pizzaView)
                parentLayout.addView(pizzaViewToAnimate)

                val xAnimator = objectAnimator(
                    viewToAnimate = pizzaViewToAnimate,
                    propertyName = "translationX",
                    startValue = pizzaViewPosition[0].toFloat(),
                    endValue = shoppingCartPosition[0].toFloat()
                )
                val yAnimator = objectAnimator(
                    viewToAnimate = pizzaViewToAnimate,
                    propertyName = "translationY",
                    startValue = pizzaViewPosition[1].toFloat(),
                    endValue = shoppingCartPosition[1].toFloat()
                )
                val scaleDownX = ObjectAnimator.ofFloat(pizzaViewToAnimate, "scaleX", 0.5f)
                val scaleDownY = ObjectAnimator.ofFloat(pizzaViewToAnimate, "scaleY", 0.5f)
                val alphaAnimation = objectAnimator(
                    viewToAnimate = pizzaViewToAnimate,
                    propertyName = "alpha",
                    startValue = 1F,
                    endValue = 0F
                )

                AnimatorSet().apply {
                    play(xAnimator).with(yAnimator).with(scaleDownX).with(scaleDownY).with(alphaAnimation)

                    addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            parentLayout.removeView(pizzaViewToAnimate)
                        }
                    })

                    start()
                }

                shoppingCartCounterAnimatorSet().start()
            }
        }

        checkoutBtn.setOnClickListener {
            areYouSureParentView.visibility = View.VISIBLE
            objectAnimator(
                viewToAnimate = areYouSureParentView,
                propertyName = "translationY",
                startValue = areYouSureParentView.height.toFloat(),
                endValue = 0f
            ).start()
        }
        noBtn.setOnClickListener {
            areYouSureParentView.visibility = View.VISIBLE
            objectAnimator(
                viewToAnimate = areYouSureParentView,
                propertyName = "translationY",
                startValue = 0f,
                endValue = areYouSureParentView.height.toFloat()
            ).start()
        }

    }

    private fun objectAnimator(
        viewToAnimate: View,
        propertyName: String,
        startValue: Float,
        endValue: Float
    ): ObjectAnimator {
        return ObjectAnimator.ofFloat(
            viewToAnimate,
            propertyName,
            startValue,
            endValue
        ).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 500
        }
    }

    private fun shoppingCartCounterAnimatorSet(): AnimatorSet {
        val shoppingCartAnimatorX = ObjectAnimator.ofFloat(
            shoppingCartCounterParentView,
            View.SCALE_X,
            1F,
            1.5F
        )
        shoppingCartAnimatorX.duration = 200L
        shoppingCartAnimatorX.repeatCount = 1
        shoppingCartAnimatorX.repeatMode = ValueAnimator.REVERSE
        val shoppingCartAnimatorY = ObjectAnimator.ofFloat(
            shoppingCartCounterParentView,
            View.SCALE_Y,
            1F,
            1.5F
        )
        shoppingCartAnimatorY.duration = 200L
        shoppingCartAnimatorY.repeatCount = 1
        shoppingCartAnimatorY.repeatMode = ValueAnimator.REVERSE

        return AnimatorSet().apply {
            play(shoppingCartAnimatorX).with(shoppingCartAnimatorY)
        }
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
}
