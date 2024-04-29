package com.doubleclick.rovleapp.feature.shop

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ScreenSlidePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val mFragmentList = ArrayList<Fragment>()
    override fun getItemCount() = mFragmentList.size

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun setFragmentList(fragment: List<Fragment>) {
        mFragmentList.addAll(fragment)
    }
}