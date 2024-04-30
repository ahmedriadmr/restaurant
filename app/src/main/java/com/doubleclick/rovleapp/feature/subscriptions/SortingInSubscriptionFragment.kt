package com.doubleclick.restaurant.feature.subscriptions

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.SortingOption
import com.doubleclick.restaurant.core.functional.SortingOption.Companion.SORTING
import com.doubleclick.restaurant.core.functional.SortingOption.Periodicity.toStringValue
import com.doubleclick.restaurant.core.platform.BaseDialogFragment
import com.doubleclick.restaurant.databinding.FragmentSortInSubscriptionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SortingInSubscriptionFragment : BaseDialogFragment(R.layout.fragment_sort_in_subscription) {

    private val binding by viewBinding(FragmentSortInSubscriptionBinding::bind)
    private lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.header.title.text = getString(R.string.subscriptions_title)

        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        var sortingOption: SortingOption? = null
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            sortingOption = when (checkedId) {
                R.id.rating -> SortingOption.Rating
                R.id.sizes -> SortingOption.Sizes
                R.id.price_asc -> SortingOption.PriceAsc
                R.id.price_des -> SortingOption.PriceDes
                R.id.periodicity -> SortingOption.Periodicity
                else -> null
            }
        }
        binding.confirmSort.setOnClickListener {
            navController.previousBackStackEntry?.savedStateHandle?.set(SORTING, sortingOption?.toStringValue())
            navController.popBackStack()
        }
    }
}