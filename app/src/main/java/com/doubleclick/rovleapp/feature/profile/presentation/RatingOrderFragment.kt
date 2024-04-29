package com.doubleclick.rovleapp.feature.profile.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observe
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseDialogFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentRatingOrderBinding
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.profile.data.rates.RatingData
import com.doubleclick.rovleapp.feature.shop.response.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RatingOrderFragment : BaseDialogFragment(R.layout.fragment_rating_order) {

    private val binding by viewBinding(FragmentRatingOrderBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private val navArgs: RatingOrderFragmentArgs by navArgs()
    private var selectedRating: Int = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            observe(rate, ::handleRatingOrder)
            observeOrNull(showProduct, ::handleShowRate)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getShowProduct(navArgs.productId)
        }
        binding.header.title.text = getString(R.string.product_rating)

        val star1 = binding.star1
        val star2 = binding.star2
        val star3 = binding.star3
        val star4 = binding.star4
        val star5 = binding.star5

        val stars = listOf(star1, star2, star3, star4, star5)

        for (star in stars) {
            star.setOnClickListener {
                selectedRating = stars.indexOf(star) + 1
                updateRating(selectedRating, stars)
            }
        }

        binding.submit.setOnClickListener {
            viewModel.doRating(navArgs.productId.toInt(), selectedRating, 1)
        }
        binding.header.back.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.cancel.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
    }

    private fun updateRating(selectedRating: Int, stars: List<ImageView>) {
        for (i in stars.indices) {
            if (i < selectedRating) {
                stars[i].setImageResource(R.drawable.ic_star_filled)
            } else {
                stars[i].setImageResource(R.drawable.ic_star_empty)
            }
        }
    }

    private fun handleRatingOrder(@Suppress("UNUSED_PARAMETER") data: RatingData) {
        Toast.makeText(activity, "You have successfully rated this product", Toast.LENGTH_SHORT).show()


    }

    private fun handleShowRate(data: Product?) {
        binding.productName.text = data?.commercial_name
        val userRate = data?.user_rate

        if (userRate != null) {
            val maxStars = 5 // Maximum stars

            val stars = listOf(binding.star1, binding.star2, binding.star3, binding.star4, binding.star5)

            for (i in 0 until maxStars) {
                if (i < userRate.toInt()) {
                    stars[i].setImageResource(R.drawable.ic_star_filled)
                } else {
                    stars[i].setImageResource(R.drawable.ic_star_empty)
                }
            }
        }
    }


    override fun renderAuthenticating(user: UserAccess?) {
        super.renderAuthenticating(user)

        binding.header.photo.load(user?.image) {
            scale(Scale.FILL)
            placeholder(R.drawable.image)
            error(R.drawable.image)
        }

        binding.header.photo.setOnClickListener { startActivity(Intent(requireActivity(), ProfileActivity::class.java)) }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}