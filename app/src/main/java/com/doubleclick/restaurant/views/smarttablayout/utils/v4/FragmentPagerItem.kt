package com.doubleclick.restaurant.views.smarttablayout.utils.v4


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.doubleclick.restaurant.views.smarttablayout.utils.DEFAULT_WIDTH
import com.doubleclick.restaurant.views.smarttablayout.utils.PagerItem


/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class FragmentPagerItem protected constructor(
    title: CharSequence?,
    width: Float,
    className: String,
    args: Bundle
) :
    PagerItem(title!!, width) {
    private val className: String
    private val args: Bundle
    fun instantiate(context: Context?, position: Int): Fragment {
        setPosition(args, position)
        return Fragment.instantiate(context!!, className, args)
    }

    companion object {
        private const val TAG = "FragmentPagerItem"
        private const val KEY_POSITION = TAG + ":Position"
        fun of(title: CharSequence?, clazz: Class<out Fragment?>): FragmentPagerItem {
            return of(title, DEFAULT_WIDTH, clazz)
        }

        fun of(
            title: CharSequence?, clazz: Class<out Fragment?>,
            args: Bundle
        ): FragmentPagerItem {
            return of(title, DEFAULT_WIDTH, clazz, args)
        }

        @JvmOverloads
        fun of(
            title: CharSequence?, width: Float,
            clazz: Class<out Fragment?>, args: Bundle = Bundle()
        ): FragmentPagerItem {
            return FragmentPagerItem(title, width, clazz.name, args)
        }

        fun hasPosition(args: Bundle?): Boolean {
            return args != null && args.containsKey(KEY_POSITION)
        }

        fun getPosition(args: Bundle): Int {
            return if (hasPosition(args)) args.getInt(KEY_POSITION) else 0
        }

        fun setPosition(args: Bundle, position: Int) {
            args.putInt(KEY_POSITION, position)
        }
    }

    init {
        this.className = className
        this.args = args
    }
}
