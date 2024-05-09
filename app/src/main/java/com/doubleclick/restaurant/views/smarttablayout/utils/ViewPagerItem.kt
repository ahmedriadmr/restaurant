package com.doubleclick.restaurant.views.smarttablayout.utils


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class ViewPagerItem protected constructor(
    title: CharSequence?,
    width: Float,
    @LayoutRes resource: Int
) :
    PagerItem(title!!, width) {
    private val resource: Int
    fun initiate(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(resource, container, false)
    }

    companion object {
        fun of(title: CharSequence?, @LayoutRes resource: Int): ViewPagerItem {
            return of(title, DEFAULT_WIDTH, resource)
        }

        fun of(title: CharSequence?, width: Float, @LayoutRes resource: Int): ViewPagerItem {
            return ViewPagerItem(title, width, resource)
        }
    }

    init {
        this.resource = resource
    }
}