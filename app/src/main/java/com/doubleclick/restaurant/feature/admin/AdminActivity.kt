package com.doubleclick.restaurant.presentation.ui.admin

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.ActivityAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAdminBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        navController = Navigation.findNavController(
            this@AdminActivity,
            findViewById<View>(R.id.home_fragment).id
        );
        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.adminHomeFragment,
            R.id.listFragment,
            R.id.staffFragment,
            R.id.profileFragment
        ).build()

        NavigationUI.setupActionBarWithNavController(
            this@AdminActivity,
            navController,
            appBarConfiguration
        );
        setupSmoothBottomMenu()
    }
    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this@AdminActivity, binding.root)
        popupMenu.inflate(R.menu.admin_nav_menu)
        val menu: Menu = popupMenu.menu
        binding.bottomBar.setupWithNavController(menu, navController)
    }
}