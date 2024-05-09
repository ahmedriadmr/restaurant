package com.doubleclick.restaurant.views.smarttablayout.utils


import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class ViewPagerItems(context: Context?) :
    PagerItems<ViewPagerItem?>(context!!) {
    class Creator(context: Context?) {
        private val items: ViewPagerItems
        fun add(@StringRes title: Int, @LayoutRes resource: Int): Creator {
            return add(ViewPagerItem.of(items.getContext().getString(title), resource))
        }

        fun add(@StringRes title: Int, width: Float, @LayoutRes resource: Int): Creator {
            return add(ViewPagerItem.of(items.getContext().getString(title), width, resource))
        }

        fun add(title: CharSequence?, @LayoutRes resource: Int): Creator {
            return add(ViewPagerItem.of(title, resource))
        }

        fun add(item: ViewPagerItem): Creator {
            items.add(item)
            return this
        }

        fun create(): ViewPagerItems {
            return items
        }

        init {
            items = ViewPagerItems(context)
        }
    }

    companion object {
        fun with(context: Context?): Creator {
            return Creator(context)
        }
    }
}
