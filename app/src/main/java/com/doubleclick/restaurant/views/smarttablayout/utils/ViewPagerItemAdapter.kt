package com.doubleclick.restaurant.views.smarttablayout.utils


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.viewpager.widget.PagerAdapter
import java.lang.ref.WeakReference

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class ViewPagerItemAdapter(pages: ViewPagerItems) : PagerAdapter() {
    private val pages: ViewPagerItems
    private val holder: SparseArrayCompat<WeakReference<View>>
    private val inflater: LayoutInflater
    override fun getCount(): Int {
        return pages.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = getPagerItem(position)!!.initiate(inflater, container)
        container.addView(view)
        holder.put(position, WeakReference(view))
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        holder.remove(position)
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }

    override fun getPageTitle(position: Int): CharSequence {
        return getPagerItem(position)!!.getTitle()
    }

    override fun getPageWidth(position: Int): Float {
        return getPagerItem(position)!!.getWidth()
    }

    fun getPage(position: Int): View? {
        val weakRefItem = holder[position]
        return weakRefItem?.get()
    }

    protected fun getPagerItem(position: Int): ViewPagerItem? {
        return pages.get(position)
    }

    init {
        this.pages = pages
        holder = SparseArrayCompat(pages.size)
        inflater = LayoutInflater.from(pages.getContext())
    }
}
