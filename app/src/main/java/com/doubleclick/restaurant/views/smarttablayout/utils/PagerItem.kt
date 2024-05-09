package com.doubleclick.restaurant.views.smarttablayout.utils


/**
 * Created By Eslam Ghazy on 11/20/2022
 */

val DEFAULT_WIDTH: Float = 1f


abstract class PagerItem protected constructor(title: CharSequence, width: Float) {


    private val title: CharSequence
    private val width: Float
    fun getTitle(): CharSequence {
        return title
    }

    fun getWidth(): Float {
        return width
    }


    init {
        this.title = title
        this.width = width
    }
}