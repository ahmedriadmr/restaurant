package com.doubleclick.restaurant.feature.passport

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.platform.AuthPopup
import com.doubleclick.restaurant.core.platform.BaseActivity
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.ActivityPassportBinding
import com.doubleclick.restaurant.feature.auth.AuthActivity
import com.doubleclick.restaurant.feature.passport.logros.presentation.AchievementsFragment
import com.doubleclick.restaurant.feature.passport.offers.presentation.OffersFragment
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.shop.showOffer.OfferClickListener
import com.doubleclick.restaurant.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PassportActivity : BaseActivity(), OfferClickListener {

    lateinit var binding: ActivityPassportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.header.title.text = getString(R.string.passport)
        binding.qr.setOnClickListener {
            replaceFragment(binding.passportHost.id, QRFragment(), true)
            binding.bgLayout.setBackgroundResource(R.drawable.bg_qr_svg)
        }

        binding.offers.setOnClickListener {
            replaceFragment(binding.passportHost.id, OffersFragment(), true)
            binding.bgLayout.background = null
        }

        binding.logros.setOnClickListener {
            replaceFragment(binding.passportHost.id, AchievementsFragment(), true)
            binding.bgLayout.background = null
        }

        if (intent.getBooleanExtra("logros", false)) {
            replaceFragment(binding.passportHost.id, AchievementsFragment())
            binding.bgLayout.background = null
            binding.logros.isChecked = true
        } else {
            replaceFragment(binding.passportHost.id, QRFragment())
        }

        binding.header.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            when (supportFragmentManager.fragments.lastOrNull()) {
                is QRFragment -> {
                    binding.bgLayout.setBackgroundResource(R.drawable.bg_qr_svg)
                    binding.qr.isChecked = true
                }

                is OffersFragment -> {
                    binding.offers.isChecked = true
                    binding.bgLayout.background = null
                }

                is AchievementsFragment -> {
                    binding.logros.isChecked = true
                    binding.bgLayout.background = null
                }
            }
        }
    }

    override fun onDestroy() {
        // Dismiss any dialogs or popups here
        // Example: dialog?.dismiss()
        AuthPopup.dismiss()
        super.onDestroy()
    }

    override fun renderAuthenticating(user: UserAccess?) {
        super.renderAuthenticating(user)


        binding.header.photo.load(user?.image) {
            scale(Scale.FILL)
            placeholder(R.drawable.image)
            error(R.drawable.image)
        }

        lifecycleScope.launch {
            val userToken = appSettingsSource.user().firstOrNull()?.token

            if (userToken != null && userToken != "-1") {
                setupAuthenticatedUI()
            } else {
                setupUnauthenticatedUI()
            }
        }
    }

    private fun setupAuthenticatedUI() {

        binding.header.photo.setOnClickListener {
            startActivity(Intent(this@PassportActivity, ProfileActivity::class.java))
        }
    }

    private fun setupUnauthenticatedUI() {

        binding.header.photo.setOnClickListener {
            startActivity(AuthActivity.callingIntent(this))
        }
    }

    override fun onOfferClicked(providerId: String, offerId: String, discount: String) {

    }
}