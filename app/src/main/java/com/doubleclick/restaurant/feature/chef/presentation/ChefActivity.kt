package com.doubleclick.restaurant.feature.chef.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.AuthPopup
import com.doubleclick.restaurant.core.platform.BaseActivity
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.ActivityChefBinding
import com.doubleclick.restaurant.feature.home.data.LogoutResponse
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
    var isLogoutClicked:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChefBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(viewModel) {
            observe(logout, ::renderLogout)
            loading(loading, ::renderLoading)

        }
        binding.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    override fun renderAuthenticating(user: UserAccess?) {
        super.renderAuthenticating(user)


        lifecycleScope.launch {
            val userToken = appSettingsSource.user().firstOrNull()?.role

            if (userToken == "user" || userToken == "waiter") {
                binding.logout.visibility = View.GONE
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
            } else if(userToken == "chief"){
                binding.logout.visibility = View.VISIBLE
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
            } else {
                if(!isLogoutClicked){
                    AuthPopup.showTwoButtonPopup(
                        this@ChefActivity,
                        "You Should Login First",
                        "Do you want to go to the Login Page?",
                        onOkClicked = {
                            finishAffinity()
                            navigator.showAuth(this@ChefActivity)
                            AuthPopup.dismiss()

                        },
                        onCancelClicked = {
                            finish()
                            AuthPopup.dismiss()// Finish the activity

                        }
                    )
                }

            }

        }

        binding.logout.setOnClickListener {
            viewModel.doLogout()
            isLogoutClicked = true
        }

    }

    private fun renderLogout(@Suppress("UNUSED_PARAMETER") data: LogoutResponse) {
        ActivityCompat.finishAffinity(this)
        navigator.showAuth(this)
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, this)
    }

}