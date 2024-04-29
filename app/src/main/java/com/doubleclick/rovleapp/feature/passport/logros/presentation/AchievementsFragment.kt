package com.doubleclick.rovleapp.feature.passport.logros.presentation

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observe
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseDialogFragment
import com.doubleclick.rovleapp.databinding.FragmentAchievementsBinding
import com.doubleclick.rovleapp.dialog.DialogAlert
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import com.doubleclick.rovleapp.feature.passport.PassportActivity
import com.doubleclick.rovleapp.feature.passport.QRFragment
import com.doubleclick.rovleapp.feature.passport.logros.data.newResponse.NewLogrosData
import com.doubleclick.rovleapp.feature.passport.offers.presentation.OffersFragment
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.shop.ShopActivity
import com.doubleclick.rovleapp.feature.subscriptions.SubscriptionActivity
import com.doubleclick.rovleapp.utils.collapse
import com.doubleclick.rovleapp.utils.expand
import com.doubleclick.rovleapp.utils.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AchievementsFragment : BaseDialogFragment(R.layout.fragment_achievements) {

    private val binding by viewBinding(FragmentAchievementsBinding::bind)
    private val viewModel: LogrosViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(viewModel) {
            observe(listLogros, ::displayItems)
            observe(finishTask, ::handleDoTask)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
        }

        isSignIn()
        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->

        }
        val resultLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->

        }
        binding.go1.setOnClickListener {
            startActivity(AuthActivity.callingIntent(requireActivity()))
        }
        binding.go2.setOnClickListener {
            (requireActivity() as PassportActivity).replaceFragment(
                (requireActivity() as PassportActivity).binding.passportHost.id, QRFragment()
            )
            (requireActivity() as PassportActivity).binding.qr.isChecked = true
        }
        binding.go3.setOnClickListener {
            (requireActivity() as PassportActivity).replaceFragment(
                (requireActivity() as PassportActivity).binding.passportHost.id, OffersFragment()
            )
            (requireActivity() as PassportActivity).binding.offers.isChecked = true
        }
        binding.go4.setOnClickListener {
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        binding.go5.setOnClickListener {
            val instagramProfileUrl = "https://www.instagram.com/yebalakoffie/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramProfileUrl))
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
                viewModel.doTask("5", "1")

            } else {
                // No activity to handle the intent
                // Handle this case accordingly
            }
        }
        binding.go6.setOnClickListener {
            val appPackageName = requireActivity().packageName
            val message = "Check out this cool app: https://play.google.com/store/apps/details?id=$appPackageName"

            val sendIntent = Intent("android.intent.action.SEND")
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, message)
            sendIntent.`package` = "com.whatsapp"

            try {
                // Start activity and check if the user sends the invitation
                viewModel.doTask("6", "1")
                resultLauncher.launch(sendIntent)
            } catch (ex: ActivityNotFoundException) {
                // Handle case where WhatsApp is not installed
                // You may want to provide a fallback or notify the user
            }
        }

        binding.go7.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go8.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go9.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go10.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go11.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go12.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go13.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go14.setOnClickListener {
            (requireActivity() as PassportActivity).replaceFragment(
                (requireActivity() as PassportActivity).binding.passportHost.id, QRFragment()
            )
            (requireActivity() as PassportActivity).binding.qr.isChecked = true
        }
        binding.go15.setOnClickListener {
            (requireActivity() as PassportActivity).replaceFragment(
                (requireActivity() as PassportActivity).binding.passportHost.id, OffersFragment()
            )
            (requireActivity() as PassportActivity).binding.offers.isChecked = true
        }
        binding.go16.setOnClickListener {
            startActivity(Intent(requireActivity(), ProfileActivity::class.java))
        }
        binding.go17.setOnClickListener {
            val appPackageName = requireActivity().packageName
            val message = "Check out this cool app: https://play.google.com/store/apps/details?id=$appPackageName"

            val sendIntent = Intent("android.intent.action.SEND")
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, message)
            sendIntent.`package` = "com.whatsapp"

            try {
                // Start activity and check if the user sends the invitation
                viewModel.doTask("17", "1")
                resultLauncher2.launch(sendIntent)
            } catch (ex: ActivityNotFoundException) {
                // Handle case where WhatsApp is not installed
                // You may want to provide a fallback or notify the user
            }
        }
        binding.go18.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go19.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go20.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go21.setOnClickListener {
            startActivity(Intent(requireActivity(), SubscriptionActivity::class.java))
        }
        binding.go22.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go23.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }
        binding.go24.setOnClickListener {
            startActivity(Intent(requireActivity(), ShopActivity::class.java))
        }


        binding.level1.setOnClickListener {
            if (binding.llLevel1.visibility == View.GONE) {
                binding.arrowLevel1.animate().rotation(0f).start()
                expand(binding.llLevel1)
            } else {
                binding.arrowLevel1.animate().rotation(-90f).start()
                collapse(binding.llLevel1)
            }
        }

    }

    private fun alertLevel2() {

        val dialog = DialogAlert(requireActivity())
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // Assuming you have a button with ID "verificar" in your custom dialog layout
        dialog.findViewById<ImageView>(R.id.imageClose)?.setOnClickListener {
            dialog.dismiss()
        }
        binding.arrowLevel2.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(), R.drawable.locker
            )
        )
    }

    private fun alertLevel3() {
        val dialog = DialogAlert(requireActivity())
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // Assuming you have a button with ID "verificar" in your custom dialog layout
        dialog.findViewById<ImageView>(R.id.imageClose)?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<TextView>(R.id.textView2).text = getString(R.string.level3_closed)

        binding.arrowLevel3.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(), R.drawable.locker
            )
        )
    }

    private fun isSignIn() {
        viewLifecycleOwner.lifecycleScope.launch {
            val userToken = viewModel.appSettingsSource.user().firstOrNull()?.token

            if (userToken != null && userToken != "-1") {
                viewModel.listLogros()

                binding.level2.setOnClickListener {
                    if (binding.llLevel2.visibility == View.GONE) {
                        binding.arrowLevel2.animate().rotation(0f).start()
                        expand(binding.llLevel2)
                    } else {
                        binding.arrowLevel2.animate().rotation(-90f).start()
                        collapse(binding.llLevel2)
                    }
                }

                binding.arrowLevel1.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(), R.drawable.arrow
                    )
                )
            } else {
                binding.level2.setOnClickListener {
                    alertLevel2()
                }
                binding.level3.setOnClickListener {
                    alertLevel3()
                }
                binding.level2.isClickable = true
                binding.arrowLevel2.rotation = 0f
                binding.arrowLevel2.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(), R.drawable.locker
                    )
                )
                binding.level3.isClickable = true
                binding.arrowLevel3.rotation = 0f

                binding.arrowLevel3.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(), R.drawable.locker
                    )
                )
            }
        }
    }

    private fun handleDoTask(@Suppress("UNUSED_PARAMETER") data: String) {}

    private fun displayItems(data: NewLogrosData) {
        var isDoneLevel2 = 0
        var isDoneLevel3 = 0

        data.tasks.onEach { item ->
            val formattedText = getString(R.string.user_repetition_placeholder, item.user_repetition ?: 0, item.repetition.toString())
            if (item.level_id == 1) {
                binding.Level1Achievement1.text = item.name
                when (item.is_done) {
                    1 -> {
                        binding.go1.text = getString(R.string.checkedmark)
                        binding.go1.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                        binding.go1.isEnabled = false
                    }

                    else -> {
                        binding.go1.text = getString(R.string.go)
                        binding.go1.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                        binding.go1.isEnabled = true
                    }
                }
            }
            if (item.level_id == 2) {
                when (item.id.toInt()) {
                    2 -> {
                        binding.Level2Achievement2.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go2.text = getString(R.string.checkedmark)
                                binding.go2.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go2.text = getString(R.string.go)
                                binding.go2.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    3 -> {
                        binding.Level2Achievement3.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go3.text = getString(R.string.checkedmark)
                                binding.go3.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go3.text = getString(R.string.go)
                                binding.go3.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    4 -> {
                        binding.Level2Achievement4.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go4.text = getString(R.string.checkedmark)
                                binding.go4.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go4.text = getString(R.string.go)
                                binding.go4.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    5 -> {
                        binding.Level2Achievement5.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go5.text = getString(R.string.checkedmark)
                                binding.go5.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go5.text = getString(R.string.go)
                                binding.go5.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    6 -> {
                        binding.Level2Achievement6.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go6.text = getString(R.string.checkedmark)
                                binding.go6.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go6.text = getString(R.string.go)
                                binding.go6.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    7 -> {
                        binding.Level2Achievement7.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go7.text = getString(R.string.checkedmark)
                                binding.go7.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go7.text = getString(R.string.go)
                                binding.go7.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    8 -> {
                        binding.Level2Achievement8.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go8.text = getString(R.string.checkedmark)
                                binding.go8.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go8.text = getString(R.string.go)
                                binding.go8.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    9 -> {
                        binding.Level2Achievement9.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go9.text = getString(R.string.checkedmark)
                                binding.go9.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go9.text = getString(R.string.go)
                                binding.go9.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    10 -> {
                        binding.Level2Achievement10.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go10.text = getString(R.string.checkedmark)
                                binding.go10.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go10.text = getString(R.string.go)
                                binding.go10.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    11 -> {
                        binding.Level2Achievement11.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go11.text = getString(R.string.checkedmark)
                                binding.go11.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go11.text = getString(R.string.go)
                                binding.go11.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    12 -> {
                        binding.Level2Achievement12.text = item.name
                        binding.userAmountAndRepitition12.text = formattedText
                        when (item.is_done) {
                            1 -> {
                                binding.go12.text = getString(R.string.checkedmark)
                                binding.go12.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go12.text = getString(R.string.go)
                                binding.go12.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    13 -> {
                        binding.Level2Achievement13.text = item.name
                        binding.userAmountAndRepitition13.text = formattedText
                        when (item.is_done) {
                            1 -> {
                                binding.go13.text = getString(R.string.checkedmark)
                                binding.go13.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel2++
                            }

                            else -> {
                                binding.go13.text = getString(R.string.go)
                                binding.go13.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }
                }
            }

            if (item.level_id == 3) {
                when (item.id.toInt()) {
                    14 -> {
                        binding.Level3Achievement14.text = item.name
                        binding.userAmountAndRepitition14.text = formattedText
                        when (item.is_done) {
                            1 -> {
                                binding.go14.text = getString(R.string.checkedmark)
                                binding.go14.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go14.text = getString(R.string.go)
                                binding.go14.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    15 -> {
                        binding.Level3Achievement15.text = item.name
                        binding.userAmountAndRepitition15.text = formattedText
                        when (item.is_done) {
                            1 -> {
                                binding.go15.text = getString(R.string.checkedmark)
                                binding.go15.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go15.text = getString(R.string.go)
                                binding.go15.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    16 -> {
                        binding.Level3Achievement16.text = item.name
                        binding.userAmountAndRepitition16.text = formattedText
                        when (item.is_done) {
                            1 -> {
                                binding.go16.text = getString(R.string.checkedmark)
                                binding.go16.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go16.text = getString(R.string.go)
                                binding.go16.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    17 -> {
                        binding.Level3Achievement17.text = item.name
                        binding.userAmountAndRepitition17.text = formattedText
                        when (item.is_done) {
                            1 -> {
                                binding.go17.text = getString(R.string.checkedmark)
                                binding.go17.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go17.text = getString(R.string.go)
                                binding.go17.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    18 -> {
                        binding.Level3Achievement18.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go18.text = getString(R.string.checkedmark)
                                binding.go18.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go18.text = getString(R.string.go)
                                binding.go18.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    19 -> {
                        binding.Level3Achievement19.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go19.text = getString(R.string.checkedmark)
                                binding.go19.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go19.text = getString(R.string.go)
                                binding.go19.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    20 -> {
                        binding.Level3Achievement20.text = item.name
                        binding.userAmountAndRepitition20.text = formattedText
                        when (item.is_done) {
                            1 -> {
                                binding.go20.text = getString(R.string.checkedmark)
                                binding.go20.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go20.text = getString(R.string.go)
                                binding.go20.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    21 -> {
                        binding.Level3Achievement21.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go21.text = getString(R.string.checkedmark)
                                binding.go21.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go21.text = getString(R.string.go)
                                binding.go21.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    22 -> {
                        binding.Level3Achievement22.text = item.name
                        when (item.is_done) {
                            1 -> {
                                binding.go22.text = getString(R.string.checkedmark)
                                binding.go22.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go22.text = getString(R.string.go)
                                binding.go22.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    23 -> {
                        binding.Level3Achievement23.text = item.name
                        binding.userAmountAndRepitition23.text = formattedText
                        when (item.is_done) {
                            1 -> {
                                binding.go23.text = getString(R.string.checkedmark)
                                binding.go23.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go23.text = getString(R.string.go)
                                binding.go23.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }

                    24 -> {
                        binding.Level3Achievement24.text = item.name
                        binding.userAmountAndRepitition24.text = formattedText
                        when (item.is_done) {
                            1 -> {
                                binding.go24.text = getString(R.string.checkedmark)
                                binding.go24.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                                isDoneLevel3++
                            }

                            else -> {
                                binding.go24.text = getString(R.string.go)
                                binding.go24.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_text_color))
                            }
                        }
                    }
                }
            }
        }
        when (data.user_level_id) {
            1 -> {
                val formattedTextLevel2 = resources.getString(R.string.nivel2_tasks, isDoneLevel2.toString())
                binding.tvLevel1.text = getString(R.string.level_1_actual)
                binding.tvLevel2.text = formattedTextLevel2
                binding.tvLevel3.text = getString(R.string.level_3)
                binding.tvLevel1.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                binding.tvLevel2.setTextColor(ContextCompat.getColor(requireContext(), R.color.hint_text_color))
                binding.tvLevel3.setTextColor(ContextCompat.getColor(requireContext(), R.color.hint_text_color))
                binding.level3.isClickable = false
                binding.arrowLevel3.rotation = 0f
                binding.level3.setOnClickListener {
                    alertLevel3()
                }
                binding.arrowLevel3.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(), R.drawable.locker
                    )
                )
            }

            2 -> {
                val formattedTextLevel3 = resources.getString(R.string.nivel3_tasks, isDoneLevel3.toString())
                binding.tvLevel1.text = getString(R.string.level_1)
                binding.tvLevel2.text = getString(R.string.level_2_actual)
                binding.tvLevel3.text = formattedTextLevel3
                binding.tvLevel1.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                binding.tvLevel2.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                binding.tvLevel3.setTextColor(ContextCompat.getColor(requireContext(), R.color.hint_text_color))
                binding.level3.isClickable = true
                binding.arrowLevel3.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(), R.drawable.arrow
                    )
                )
                binding.arrowLevel3.animate().rotation(-90f).start()
                binding.level3.setOnClickListener {
                    if (binding.llLevel3.visibility == View.GONE) {
                        binding.arrowLevel3.animate().rotation(0f).start()
                        expand(binding.llLevel3)
                    } else {
                        binding.arrowLevel3.animate().rotation(-90f).start()
                        collapse(binding.llLevel3)
                    }
                }
            }

            3 -> {
                binding.tvLevel1.text = getString(R.string.level_1)
                binding.tvLevel2.text = getString(R.string.level_2)
                binding.tvLevel3.text = getString(R.string.level_3_actual)
                binding.tvLevel1.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                binding.tvLevel2.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                binding.tvLevel3.setTextColor(ContextCompat.getColor(requireContext(), R.color.brown))
                binding.level3.isClickable = true
                binding.arrowLevel3.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(), R.drawable.arrow
                    )
                )
                binding.arrowLevel3.animate().rotation(-90f).start()
                binding.level3.setOnClickListener {
                    if (binding.llLevel3.visibility == View.GONE) {
                        binding.arrowLevel3.animate().rotation(0f).start()
                        expand(binding.llLevel3)
                    } else {
                        binding.arrowLevel3.animate().rotation(-90f).start()
                        collapse(binding.llLevel3)
                    }
                }
            }


        }


    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}
