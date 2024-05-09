package com.doubleclick.restaurant.views.smarttablayout.utils.v4


import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.doubleclick.restaurant.views.smarttablayout.utils.PagerItems


/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class FragmentPagerItems(context: Context?) :
    PagerItems<FragmentPagerItem?>(context) {
    class Creator(context: Context?) {
        private val items: FragmentPagerItems
        fun add(@StringRes title: Int, clazz: Class<out Fragment?>?): Creator {
            return add(FragmentPagerItem.of(items.getContext().getString(title), clazz!!))
        }

        fun add(@StringRes title: Int, clazz: Class<out Fragment?>?, args: Bundle?): Creator {
            return add(
                FragmentPagerItem.of(
                    items.getContext().getString(title), clazz!!,
                    args!!
                )
            )
        }

        fun add(@StringRes title: Int, width: Float, clazz: Class<out Fragment?>?): Creator {
            return add(FragmentPagerItem.of(items.getContext().getString(title), width, clazz!!))
        }

        fun add(
            @StringRes title: Int, width: Float, clazz: Class<out Fragment?>?,
            args: Bundle?
        ): Creator {
            return add(
                FragmentPagerItem.of(
                    items.getContext().getString(title), width,
                    clazz!!, args!!
                )
            )
        }

        fun add(title: CharSequence?, clazz: Class<out Fragment?>?): Creator {
            return add(FragmentPagerItem.of(title, clazz!!))
        }

        fun add(title: CharSequence?, clazz: Class<out Fragment?>?, args: Bundle?): Creator {
            return add(FragmentPagerItem.of(title, clazz!!, args!!))
        }

        fun add(item: FragmentPagerItem?): Creator {
            items.add(item)
            return this
        }

        fun create(): FragmentPagerItems {
            return items
        }

        init {
            items = FragmentPagerItems(context)
        }
    }

    companion object {
        fun with(context: Context?): Creator {
            return Creator(context)
        }
    }
}
