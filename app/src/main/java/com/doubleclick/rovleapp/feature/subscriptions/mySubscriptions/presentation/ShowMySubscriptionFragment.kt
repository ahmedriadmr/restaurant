package com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.presentation

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.failure
import com.doubleclick.restaurant.core.extension.loading
import com.doubleclick.restaurant.core.extension.observe
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.functional.Either
import com.doubleclick.restaurant.core.functional.ProgressHandler
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.core.platform.local.UserAccess
import com.doubleclick.restaurant.databinding.FragmentShowMysubscriptionBinding
import com.doubleclick.restaurant.dialog.DialogCancelSubscription
import com.doubleclick.restaurant.feature.auth.AuthActivity
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.ChangePopup
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.PausePopup
import com.doubleclick.restaurant.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class ShowMySubscriptionFragment : BaseFragment(R.layout.fragment_show_mysubscription),
    ChangePopup.OnSelectionChangeListener {

    private val binding by viewBinding(FragmentShowMysubscriptionBinding::bind)

    private val viewModel: ShowSubscriptionViewModel by viewModels()
    private val navArgs: ShowMySubscriptionFragmentArgs by navArgs()
    private var selectedOption: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            observe(showSubscription, ::renderSubscription)
            observe(cancel, ::handleCancelSubscription)
            observe(pause, ::handlePauseSubscription)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            getShowSubscription(navArgs.id)
        }

        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.header.title.text = getString(R.string.nombre_plan)
    }

    private fun initListeners(subscription: SubscriptionData) {
        binding.goToPay.setOnClickListener {
            findNavController().navigate(ShowMySubscriptionFragmentDirections.actionShowMySubscriptionFragmentToShowInformationFragment(subscription.id))
        }
        when (subscription.status) {
            "pending" -> binding.edit.setOnClickListener {

                Toast.makeText(context, "your subscription is not activated yet", Toast.LENGTH_SHORT).show()
            }

            else -> {
                binding.edit.setOnClickListener {
                    findNavController().navigate(
                        ShowMySubscriptionFragmentDirections.actionShowMySubscriptionFragmentToPlanInformationFragment(
                            subscriptionId = subscription.id,
                            plan = null,
                            title = "Edit Plan",
                        )
                    )
                }
            }
        }

        when (subscription.status) {
            "pending" -> binding.change.setOnClickListener {
                Toast.makeText(context, "your subscription is not activated yet", Toast.LENGTH_SHORT).show()
            }

            else -> {
                binding.change.setOnClickListener {
                    showChangePopup(subscription)
                }
            }
        }

        binding.cancel.setOnClickListener {
            val dialog = DialogCancelSubscription(requireActivity())
            dialog.show()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // Assuming you have a button with ID "verificar" in your custom dialog layout
            dialog.findViewById<Button>(R.id.buttonCancel)?.setOnClickListener {
                dialog.dismiss()
            }
            dialog.findViewById<ImageView>(R.id.imageView2)?.setOnClickListener {
                dialog.dismiss()
            }
            dialog.findViewById<Button>(R.id.buttonContinue)?.setOnClickListener {

                viewModel.docancel(navArgs.id)
                dialog.dismiss()
            }
        }
        when (subscription.status) {
            "pending" -> binding.pausePlay.setOnClickListener {
                Toast.makeText(context, "your subscription is not activated yet", Toast.LENGTH_SHORT).show()
            }

            "active" -> when (subscription.paused){
                "1" -> {
                    binding.pausePlay.setOnClickListener {
                        PausePopup.showTwoButtonPopup(requireContext(), "Choose the Pause Period", onOkClicked = { selectedPeriod ->
                            val monthsToAdd = extractMonths(selectedPeriod)
                            val activationDate = calculateActivationDate(monthsToAdd)

                            viewModel.doInitStatus(navArgs.id, statusMapping(subscription.paused), activationDate)
                        })
                    }
                }
                "0" -> {
                    binding.pausePlay.setOnClickListener {
                        PausePopup.showTwoButtonPopup(requireContext(), "Choose the Pause Period", onOkClicked = { selectedPeriod ->
                            val monthsToAdd = extractMonths(selectedPeriod)
                            val activationDate = calculateActivationDate(monthsToAdd)

                            viewModel.doInitStatus(navArgs.id, statusMapping(subscription.paused), activationDate)
                        })
                    }
                }
                else -> {
                    binding.pausePlay.setOnClickListener {
                        PausePopup.showTwoButtonPopup(requireContext(), "Choose the Pause Period", onOkClicked = { selectedPeriod ->
                            val monthsToAdd = extractMonths(selectedPeriod)
                            val activationDate = calculateActivationDate(monthsToAdd)

                            viewModel.doInitStatus(navArgs.id, statusMapping(subscription.paused), activationDate)
                        })
                    }
                }
            }

        }


    }

    private fun extractMonths(period: String): Int {
        val numericValue = period.split(" ")[0].toIntOrNull()
        return numericValue ?: 0
    }

    private fun statusMapping(status: String?): String =
        when (status) {
            "1" -> "active"
            "0" -> "suspended"
            else -> "suspended"
        }


    private fun calculateActivationDate(monthsToAdd: Int): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()

        // Set the activation date as the current date
        calendar.time = Date()

        // Add selected months to the current date
        calendar.add(Calendar.MONTH, monthsToAdd)

        return sdf.format(calendar.time)
    }

    private fun renderSubscription(data: SubscriptionData) {

        data.let { subscription ->
            renderDetails(subscription)
            initListeners(subscription)
            subscription.paused?.let { status ->
                renderSubscriptionStatus(status)
            }
        }
    }

    private fun convertDateFormat(inputDate: String): String {
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault())

        return try {
            val date: Date = inputFormat.parse(inputDate) ?: Date()
            outputFormat.format(date)
        } catch (e: ParseException) {
            // Handle the exception, return an appropriate error message or inputDate as-is
            e.printStackTrace()
            "Error converting date"
        }
    }






    private fun renderDetails(subscription: SubscriptionData) {
        binding.planName.text = subscription.plan?.name
        binding.description.text = subscription.plan?.description
        binding.planname.text = subscription.plan?.name
        binding.deliveryPeriodic.text = getString(R.string.delivery_periodic_text, subscription.periodicity.toString())
        binding.deliveryDate.text = subscription.activation_date?.let { convertDateFormat(it) } ?: subscription.start_at?.let { convertDateFormat(it) }
        binding.price.text = getString(R.string.total_cost_placeholder, subscription.plan_size?.price ?: "", getString(R.string.euro_sign))
        binding.note.text = subscription.notes
        binding.addressType.text = subscription.delivery_type
        binding.address.text = when (subscription.delivery_type) {
            "take_away" -> subscription.coffee_shop?.address
            "delivery" -> subscription.address
            else -> {
                subscription.address
            }
        }
        binding.dateArrive.text = getString(R.string.paused_until_text, subscription.activation_date?.let { convertDateFormat(it) } ?: "")
        binding.providerName.text = getString(R.string.provider_name_suffix, subscription.plan?.provider?.commercial_name ?: "")
        binding.size.text = getString(R.string.size_weight_text, subscription.plan_size?.size?.weight ?: "")
        when(subscription.status){
            "active" -> binding.goToPay.visibility = View.GONE
            "pending" -> binding.goToPay.visibility = View.VISIBLE
        }
    }

    private fun renderSubscriptionStatus(status: String?) {
        // Check if the status is null
        if (status == null) {
            // Handle null status here, for example:
            // You may want to set default values or hide UI elements
            binding.llImageDateArrived.visibility = View.GONE
            binding.statusIcon.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.root.context,
                    R.drawable.ic_pause
                )
            )
            binding.statusTitle.text = getString(R.string.pausa)
        } else {
            // Handle non-null status using when expression
            when (status) {
                "1" -> {
                    binding.llImageDateArrived.visibility = View.VISIBLE
                    binding.statusIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.ic_play_big
                        )
                    )
                    binding.statusTitle.text = getString(R.string.activar)
                }

                "0" -> {
                    binding.llImageDateArrived.visibility = View.GONE
                    binding.statusIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.ic_pause
                        )
                    )
                    binding.statusTitle.text = getString(R.string.pausa)
                }

                else -> {
                    // Handle unexpected status values here, if needed
                    binding.llImageDateArrived.visibility = View.GONE
                    binding.statusIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.ic_pause
                        )
                    )
                    binding.statusTitle.text = getString(R.string.pausa)
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
        viewLifecycleOwner.lifecycleScope.launch {
            val userToken = viewModel.appSettingsSource.user().firstOrNull()?.token

            if (userToken != null && userToken != "-1") {

                binding.header.photo.setOnClickListener { startActivity(Intent(requireActivity(), ProfileActivity::class.java)) }
            } else {
                binding.header.photo.setOnClickListener { startActivity(AuthActivity.callingIntent(requireActivity())) }
            }
        }
    }

    private fun handleCancelSubscription(@Suppress("UNUSED_PARAMETER") data: String) {

        Toast.makeText(context, "you have cancelled this subscription", Toast.LENGTH_SHORT).show()
        findNavController().navigate(ShowMySubscriptionFragmentDirections.actionShowMySubscriptionFragmentToSubscribtionFragment(""))
    }

    private fun handlePauseSubscription(data: SubscriptionData) {


        when (data.paused) {
            "1" -> Toast.makeText(context, "you have paused your subscription", Toast.LENGTH_SHORT).show()
            "0" -> Toast.makeText(context, "you have activated your subscription", Toast.LENGTH_SHORT).show()
        }
        viewModel.getShowSubscription(navArgs.id)
        renderSubscription(data)
    }

    override fun onSelectionChanged(selectedOption: String) {
        this.selectedOption = selectedOption
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }

    private fun showChangePopup(subscription: SubscriptionData) {
        ChangePopup.showPopup(
            requireContext(),
            this,
            object : ChangePopup.OnContinueClickListener {
                override fun onContinueClicked() {
                    // Handle continue button click based on the selected option
                    when (selectedOption) {
                        "Same Roaster" -> {
                            findNavController().navigate(
                                ShowMySubscriptionFragmentDirections
                                    .actionShowMySubscriptionFragmentToSubscribtionFragment(subscription.id, true, subscription.plan?.provider_id)
                            )
                        }

                        "Another Roaster" -> {
                            findNavController().navigate(
                                ShowMySubscriptionFragmentDirections
                                    .actionShowMySubscriptionFragmentToSubscribtionFragment(subscription.id, true, "")
                            )
                        }
                    }
                }
            }
        )
    }
}