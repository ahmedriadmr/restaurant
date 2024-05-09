package com.doubleclick.restaurant.views.smarttablayout.utils


import android.content.Context

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
abstract class PagerItems<T : PagerItem?> protected constructor(context: Context?) :
    ArrayList<T?>() {
    private lateinit var context: Context
    fun getContext(): Context {
        return context
    }

    init {
        if (context != null) {
            this.context = context
        }
    }
}