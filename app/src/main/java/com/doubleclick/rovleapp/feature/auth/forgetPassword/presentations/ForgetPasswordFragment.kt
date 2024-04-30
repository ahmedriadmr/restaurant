package com.doubleclick.restaurant.feature.auth.forgetPassword.presentations

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.findNavControllerFromFragmentContainer
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentForgetPasswordBinding
import com.doubleclick.restaurant.feature.auth.AuthActivity
import com.doubleclick.restaurant.feature.auth.AuthViewModel
import com.doubleclick.stepper.StepperNavListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment(R.layout.fragment_forget_password), StepperNavListener {

    private val binding by viewBinding(FragmentForgetPasswordBinding::bind)
    private val viewModel: AuthViewModel by activityViewModels()


    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavControllerFromFragmentContainer(R.id.frame_stepper).popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.isBackPressedEnabled, ::renderBackDispatcher)

        (requireActivity() as AuthActivity).setSupportActionBar(binding.toolbar)
        // Setup Action bar for title with top-level destinations.

        val fragmentContainer = findNavControllerFromFragmentContainer(R.id.frame_stepper)
        setupActionBarWithNavController(
            fragmentContainer,
            AppBarConfiguration.Builder(
                R.id.step_1_dest,
                R.id.step_2_dest,
                R.id.step_3_dest,
            ).build()
        )


//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)


        fragmentContainer.addOnDestinationChangedListener { _, destination, _ ->
            with(binding.stepper) {
                when (destination.id) {
                    R.id.forgetPasswordStepOneFragment -> if (currentStep > 0) goToPreviousStep()
                    R.id.forgetPasswordStepTwoFragment -> if (currentStep == 0) goToNextStep() else goToPreviousStep()
                    R.id.forgetPasswordStepThreeFragment -> if (currentStep == 1) goToNextStep() else goToPreviousStep()
                }
            }
        }
        binding.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun renderBackDispatcher(isEnabled: Boolean) {
        callback.isEnabled = isEnabled
    }

    override fun onCompleted() {

        Toast.makeText(requireActivity(), "Done", Toast.LENGTH_SHORT).show()

    }

    override fun onStepChanged(step: Int) {


    }

    private fun setupActionBarWithNavController(
        navController: NavController,
        configuration: AppBarConfiguration = AppBarConfiguration(navController.graph)
    ) {
        setupActionBarWithNavController(
            (requireActivity() as AuthActivity),
            navController,
            configuration
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback.remove()
    }
}

