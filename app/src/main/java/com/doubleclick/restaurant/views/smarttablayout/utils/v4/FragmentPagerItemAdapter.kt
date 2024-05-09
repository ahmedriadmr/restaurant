package com.doubleclick.restaurant.views.smarttablayout.utils.v4



import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.lang.ref.WeakReference

/**
 * Created By Eslam Ghazy on 11/20/2022
 */
class FragmentPagerItemAdapter(fm: FragmentManager?, pages: FragmentPagerItems) :
    FragmentPagerAdapter(fm!!) {
    private val pages: FragmentPagerItems
    private val holder: SparseArrayCompat<WeakReference<Fragment>>
    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        return getPagerItem(position)!!.instantiate(pages.getContext(), position)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val item = super.instantiateItem(container, position)
        if (item is Fragment) {
            holder.put(position, WeakReference(item))
        }
        return item
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        holder.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return getPagerItem(position)!!.getTitle()
    }

    override fun getPageWidth(position: Int): Float {
        return super.getPageWidth(position)
    }

    fun getPage(position: Int): Fragment? {
        val weakRefItem = holder[position]
        return weakRefItem?.get()
    }

    protected fun getPagerItem(position: Int): FragmentPagerItem? {
        return pages[position]
    }

    init {
        this.pages = pages
        holder = SparseArrayCompat(pages.size)
    }
}