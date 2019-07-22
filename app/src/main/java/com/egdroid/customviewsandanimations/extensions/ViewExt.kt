package com.egdroid.customviewsandanimations.extensions

import android.view.View

/**
 * Created at Tito on 2019-07-21
 */

fun View.getPositionOnScreen(): IntArray {
    val position = intArrayOf(0, 0)
    getLocationOnScreen(position)
    return position
}