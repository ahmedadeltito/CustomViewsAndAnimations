package com.egdroid.customviewsandanimations

import java.io.Serializable

/**
 * Created at Tito on 2019-07-21
 */
enum class PizzaSize(val value: Int) : Serializable {
    SMALL(0),
    MEDIUM(1),
    LARGE(2);

    companion object {
        private val map = values().associateBy(PizzaSize::value)
        fun fromInt(type: Int) = map[type] ?: SMALL
    }
}