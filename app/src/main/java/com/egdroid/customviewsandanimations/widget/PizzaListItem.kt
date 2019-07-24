package com.egdroid.customviewsandanimations.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.egdroid.customviewsandanimations.PizzaSize
import com.egdroid.customviewsandanimations.PizzaStatus
import com.egdroid.customviewsandanimations.R
import kotlin.math.max

/**
 * Created at Tito on 2019-07-21
 */

class PizzaListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private lateinit var pizzaView: PizzaView
    private lateinit var pizzaTitleTv: TextView
    private lateinit var pizzaSubtitleTv: TextView
    private lateinit var numOfPizzasView: NumberOfPizzasView

    private var pizzaSize: PizzaSize = PizzaSize.SMALL

    var setOnPizzaListItemClickedListener: PizzaListItemClickedListener? = null

    override fun onFinishInflate() {
        super.onFinishInflate()

        pizzaView = findViewById(R.id.pizza_list_item_icon)
        pizzaTitleTv = findViewById(R.id.pizza_list_item_title_tv)
        pizzaSubtitleTv = findViewById(R.id.pizza_list_item_subtitle_tv)
        numOfPizzasView = findViewById(R.id.number_of_pizzas_view)

        pizzaView.setOnClickListener {
            setOnPizzaListItemClickedListener?.onPizzaViewClicked(pizzaView)
        }

        pizzaTitleTv.setOnClickListener {
            setOnPizzaListItemClickedListener?.onPizzaTitleClicked(
                pizzaView = pizzaView,
                pizzaTitleTv = pizzaTitleTv
            )
        }

        numOfPizzasView.setOnNumberOfPizzasListener = object : NumberOfPizzasListener {
            override fun pizzaStatus(pizzaStatus: PizzaStatus) {
                setOnPizzaListItemClickedListener?.pizzaStatus(
                    pizzaStatus = pizzaStatus,
                    pizzaView = pizzaView
                )
            }
        }

        updatePizzaSize()

    }

    override fun checkLayoutParams(p: LayoutParams?): Boolean {
        return p is MarginLayoutParams
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(
            context,
            attrs
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        measureChildWithMargins(
            pizzaView, widthMeasureSpec, 0, heightMeasureSpec, 0
        )
        var marginLayoutParams = pizzaView.layoutParams as MarginLayoutParams
        val pizzaViewWidthUsed =
            pizzaView.measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin
        val pizzaViewHeightUsed =
            pizzaView.measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin

        measureChildWithMargins(
            pizzaTitleTv, widthMeasureSpec, pizzaViewWidthUsed, heightMeasureSpec, 0
        )
        marginLayoutParams = pizzaTitleTv.layoutParams as MarginLayoutParams
        val titleWidthUsed =
            pizzaTitleTv.measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin
        val titleHeightUsed =
            pizzaTitleTv.measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin

        measureChildWithMargins(
            pizzaSubtitleTv, widthMeasureSpec, pizzaViewWidthUsed, heightMeasureSpec, titleHeightUsed
        )
        marginLayoutParams = pizzaSubtitleTv.layoutParams as MarginLayoutParams
        val subtitleWidthUsed =
            pizzaSubtitleTv.measuredWidth + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin
        val subtitleHeightUsed =
            pizzaSubtitleTv.measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin

        measureChildWithMargins(
            numOfPizzasView, widthMeasureSpec, 0, heightMeasureSpec, pizzaViewHeightUsed
        )
        marginLayoutParams = numOfPizzasView.layoutParams as MarginLayoutParams
        val numOfPizzasHeightUsed =
            numOfPizzasView.measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin

        val width = pizzaViewWidthUsed + max(
            titleWidthUsed,
            subtitleWidthUsed
        ) + paddingLeft + paddingRight
        val height = max(
            pizzaViewHeightUsed,
            titleHeightUsed + subtitleHeightUsed + numOfPizzasHeightUsed
        ) + paddingTop + paddingBottom

        setMeasuredDimension(width, height)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        val pizzaViewLayoutParams = pizzaView.layoutParams as MarginLayoutParams
        var left = paddingLeft + pizzaViewLayoutParams.leftMargin
        var top = paddingTop + pizzaViewLayoutParams.topMargin
        var right = left + pizzaView.measuredWidth
        var bottom = top + pizzaView.measuredHeight

        pizzaView.layout(left, top, right, bottom)

        val pizzaViewRightPlusMargin = right + pizzaViewLayoutParams.rightMargin

        val pizzaTitleLayoutParams = pizzaTitleTv.layoutParams as MarginLayoutParams
        left = pizzaViewRightPlusMargin + pizzaTitleLayoutParams.leftMargin
        top = paddingTop + pizzaTitleLayoutParams.topMargin
        right = left + pizzaTitleTv.measuredWidth
        bottom = top + pizzaTitleTv.measuredHeight

        pizzaTitleTv.layout(left, top, right, bottom)

        val pizzaTitleBottomPlusMargin = bottom + pizzaTitleLayoutParams.bottomMargin

        val pizzaSubtitleLayoutParams = pizzaSubtitleTv.layoutParams as MarginLayoutParams
        left = pizzaViewRightPlusMargin + pizzaSubtitleLayoutParams.leftMargin
        top = pizzaTitleBottomPlusMargin + pizzaSubtitleLayoutParams.topMargin
        right = left + pizzaSubtitleTv.measuredWidth
        bottom = top + pizzaSubtitleTv.measuredHeight

        pizzaSubtitleTv.layout(left, top, right, bottom)

        val pizzaSubtitleBottomPlusMargin = bottom + pizzaSubtitleLayoutParams.bottomMargin

        val numOfPizzasLayoutParams = numOfPizzasView.layoutParams as MarginLayoutParams
        left = paddingLeft + numOfPizzasLayoutParams.leftMargin
        top = pizzaSubtitleBottomPlusMargin + numOfPizzasLayoutParams.topMargin
        right = left + numOfPizzasView.measuredWidth
        bottom = top + numOfPizzasView.measuredHeight

        numOfPizzasView.layout(left, top, right, bottom)

    }

    fun setPizzaSize(pizzaSize: PizzaSize) {
        this.pizzaSize = pizzaSize
        updatePizzaSize()
    }

    private fun updatePizzaSize() {
        pizzaView.setPizzaSize(pizzaSize)

        when (pizzaSize) {
            PizzaSize.SMALL -> {
                pizzaTitleTv.text = resources.getString(R.string.small).capitalize()
                pizzaSubtitleTv.text = resources.getString(R.string.small_pizza)
            }
            PizzaSize.MEDIUM -> {
                pizzaTitleTv.text = resources.getString(R.string.medium).capitalize()
                pizzaSubtitleTv.text = resources.getString(R.string.medium_pizza)
            }
            PizzaSize.LARGE -> {
                pizzaTitleTv.text = resources.getString(R.string.large).capitalize()
                pizzaSubtitleTv.text = resources.getString(R.string.large_pizza)
            }
        }
    }

}

interface PizzaListItemClickedListener {
    fun onPizzaTitleClicked(pizzaView: PizzaView, pizzaTitleTv: TextView)
    fun onPizzaViewClicked(pizzaView: PizzaView)
    fun pizzaStatus(pizzaView: PizzaView, pizzaStatus: PizzaStatus)
}