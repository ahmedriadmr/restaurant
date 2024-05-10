package com.doubleclick.restaurant.feature.chef.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.platform.BaseActivity
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.ActivityChefBinding
import com.doubleclick.restaurant.feature.home.presentation.HistoryOrdersFragment
import com.doubleclick.restaurant.feature.home.presentation.HomeViewModel
import com.doubleclick.restaurant.feature.home.presentation.ListOrdersFragment
import com.doubleclick.restaurant.views.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.doubleclick.restaurant.views.smarttablayout.utils.v4.FragmentPagerItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChefActivity : BaseActivity() {

    private lateinit var binding: ActivityChefBinding
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChefBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    override fun renderAuthenticating(user: UserAccess?) {
        super.renderAuthenticating(user)


        lifecycleScope.launch {
            val userToken = appSettingsSource.user().firstOrNull()?.role

            if (userToken == "user" || userToken == "waiter") {
                val adapter = FragmentPagerItemAdapter(
                    supportFragmentManager,
                    FragmentPagerItems.with(this@ChefActivity)
                        .add(
                            getString(R.string.list),
                            ListOrdersFragment::class.java,
                        )
                        .add(
                            getString(R.string.history),
                            HistoryOrdersFragment::class.java,
                        )
                        .create()
                )
                binding.viewpager.adapter = adapter
                binding.viewpagertab.setViewPager(binding.viewpager)
            } else {
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

        binding.logout.setOnClickListener {
            viewModel.doLogout()
        }

    }

}