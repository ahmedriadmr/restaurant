package com.doubleclick.restaurant.feature.home.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.ActivityHomeBinding
import com.doubleclick.restaurant.core.platform.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        navController = Navigation.findNavController(
            this@HomeActivity,
            findViewById<View>(R.id.home_fragment).id
        );
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.cartFragment,
            R.id.userProfileFragment
        ).build()

        NavigationUI.setupActionBarWithNavController(
            this@HomeActivity,
            navController,
            appBarConfiguration
        );
        setupSmoothBottomMenu()
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this@HomeActivity, binding.root)
        popupMenu.inflate(R.menu.home_nav_menu)
        val menu: Menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu, navController)
    }
    companion object {
        fun callingIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }
}