package com.doubleclick.restaurant.feature.chef.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.ActivityChefBinding
import com.doubleclick.restaurant.views.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.doubleclick.restaurant.views.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChefActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChefBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChefBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewPager()
    }

    private fun setViewPager() {
        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager,
            FragmentPagerItems.with(this@ChefActivity)
                .add(
                    getString(R.string.list),
                    ListOrderChefFragment::class.java,
                )
                .add(
                    getString(R.string.history),
                    HistoryOrderChefFragment::class.java,
                )
                .create()
        )
        binding.viewpager.adapter = adapter
        binding.viewpagertab.setViewPager(binding.viewpager)
    }
}