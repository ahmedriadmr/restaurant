package com.doubleclick.rovleapp.feature.subscriptions.providerList.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import coil.size.Scale
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.adapter.spinner.SpinnerAdapter
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observe
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.platform.BaseFragment
import com.doubleclick.rovleapp.core.platform.local.UserAccess
import com.doubleclick.rovleapp.databinding.FragmentPlanInformationBinding
import com.doubleclick.rovleapp.dialog.DialogAddAddress
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import com.doubleclick.rovleapp.feature.profile.ProfileActivity
import com.doubleclick.rovleapp.feature.profile.data.addAddress.response.AddressData
import com.doubleclick.rovleapp.feature.profile.data.listAddresses.AddressesData
import com.doubleclick.rovleapp.feature.profile.data.showProfile.ProfileDetails
import com.doubleclick.rovleapp.feature.shop.cart.NewCoffeeShopAdapter
import com.doubleclick.rovleapp.feature.shop.cart.NewLockerAdapter
import com.doubleclick.rovleapp.feature.shop.cart.locker.data.LockerData
import com.doubleclick.rovleapp.feature.shop.cartDetails.data.filterCities.FilterCitiesData
import com.doubleclick.rovleapp.feature.shop.cartDetails.presentation.NewFilterCityAdapter
import com.doubleclick.rovleapp.feature.shop.cartDetails.presentation.NewFilterCountryAdapter
import com.doubleclick.rovleapp.feature.shop.cartDetails.presentation.NewFilterProvinceAdapter
import com.doubleclick.rovleapp.feature.shop.response.CoffeeShop
import com.doubleclick.rovleapp.feature.subscriptions.mySubscriptions.data.showSubscription.SubscriptionData
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.showPlan.PlanDetails
import com.doubleclick.rovleapp.feature.subscriptions.providerList.data.showPlan.Size
import com.doubleclick.rovleapp.feature.subscriptions.providerList.presentation.adapter.NewAddressesAdapter
import com.doubleclick.rovleapp.feature.subscriptions.providerList.presentation.adapter.SpinnerAdapterForWeight
import com.doubleclick.rovleapp.utils.Constant.euroSign
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlanInformationFragment : BaseFragment(R.layout.fragment_plan_information) {
//    lateinit var selectedCountry: FilterCitiesData
//    lateinit var selectedProvince: Province
//    lateinit var selectedCity: City
    private val list = listOf("1 Month", "3 Months", "6 Months")
    private val binding by viewBinding(FragmentPlanInformationBinding::bind)
    private lateinit var navController: NavController
    private val viewModel: ShowPlanViewModel by viewModels()
    private val navArgs: PlanInformationFragmentArgs by navArgs()
    private lateinit var weightSize: List<Size>
    lateinit var selectedSize: Size
    lateinit var selectedPeriodic: String
    private lateinit var selectedLocation: AddressesData
    private var selectedRadio: Int = -1
    private var isShopSelected = false
    private var isLockerSelected = false
    private var isCountrySelected = false
    private var isProvinceSelected = false
    private var isCitySelected = false
    private val coffeeListAdapter = NewCoffeeShopAdapter()
    private val lockerListAdapter = NewLockerAdapter()
    private val addressListAdapter = NewAddressesAdapter()
    private val countryAdapter = NewFilterCountryAdapter()
    private val provinceAdapter = NewFilterProvinceAdapter()
    private val cityAdapter = NewFilterCityAdapter()
    private var subscriptionData: SubscriptionData? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.header.title.text = getString(R.string.nombre_del_plan)
        binding.iTakeAway.rvSelectCoffeeShop.adapter = coffeeListAdapter
        binding.iLocker.rvSelectLocker.adapter = lockerListAdapter
        binding.iHome.rvSelectAddress.adapter = addressListAdapter
        binding.iHome.rvSelectCountry.adapter = countryAdapter
        binding.iHome.rvSelectProvince.adapter = provinceAdapter
        binding.iHome.rvSelectCity.adapter = cityAdapter
        with(viewModel) {
            observe(getAddresses){addresses ->renderGetAddresses(addresses){addressListAdapter.submitList(null) } }
            observeOrNull(showSubscription, ::handleShowSubscriptionInformation)
            observe(doSubscribe, ::renderSubscription)
            observe(editSubscription, ::renderEditSubscription)
            observe(getCoffeeShop) { coffeeShops -> renderGetCoffeeShop(coffeeShops) { coffeeListAdapter.submitList(null) } }
            observe(listLockers) { lockers -> renderGetLockers(lockers) {lockerListAdapter.submitList(null) } }
            observe(getCities) { filterCities -> renderGetCities(filterCities) { countryAdapter.submitList(null) } }
            observe(addAddress, ::handleAddingAddress)
            observeOrNull(showProfile, ::renderProfileState)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            doGetAddresses()
            navArgs.plan?.let { renderPlanInformation(it) }

        }
        addressListAdapter.clickListenerEditAddresses = { onAddressSelected(addressListAdapter.selectedLocation) }
        binding.header.back.setOnClickListener {
            navController.popBackStack()
        }

        binding.iTakeAway.llCoffeeshopList.setOnClickListener {
            if (binding.iTakeAway.rvSelectCoffeeShop.visibility == View.VISIBLE) {
                binding.iTakeAway.rvSelectCoffeeShop.visibility = View.GONE
                binding.iTakeAway.arrowIndicator.rotation = 0f
            } else {
                binding.iTakeAway.rvSelectCoffeeShop.visibility = View.VISIBLE
                binding.iTakeAway.arrowIndicator.rotation = 180f
            }
        }
        binding.iTakeAway.arrowIndicator.setOnClickListener {
            if (binding.iTakeAway.rvSelectCoffeeShop.visibility == View.VISIBLE) {
                binding.iTakeAway.rvSelectCoffeeShop.visibility = View.GONE
                binding.iTakeAway.arrowIndicator.rotation = 0f
            } else {
                binding.iTakeAway.rvSelectCoffeeShop.visibility = View.VISIBLE
                binding.iTakeAway.arrowIndicator.rotation = 180f
            }
        }
        binding.iLocker.llLockerList.setOnClickListener {
            if (binding.iLocker.rvSelectLocker.visibility == View.VISIBLE) {
                binding.iLocker.rvSelectLocker.visibility = View.GONE
                binding.iLocker.arrowIndicatorLocker.rotation = 0f
            } else {
                binding.iLocker.rvSelectLocker.visibility = View.VISIBLE
                binding.iLocker.arrowIndicatorLocker.rotation = 180f
            }
        }
        binding.iLocker.arrowIndicatorLocker.setOnClickListener {
            if (binding.iLocker.rvSelectLocker.visibility == View.VISIBLE) {
                binding.iLocker.rvSelectLocker.visibility = View.GONE
                binding.iLocker.arrowIndicatorLocker.rotation = 0f
            } else {
                binding.iLocker.rvSelectLocker.visibility = View.VISIBLE
                binding.iLocker.arrowIndicatorLocker.rotation = 180f
            }
        }
        binding.iHome.llAddressList.setOnClickListener {
            if (binding.iHome.rvSelectAddress.visibility == View.VISIBLE) {
                binding.iHome.rvSelectAddress.visibility = View.GONE
                binding.iHome.arrowIndicatorAddress.rotation = 0f
            } else {
                binding.iHome.rvSelectAddress.visibility = View.VISIBLE
                binding.iHome.arrowIndicatorAddress.rotation = 180f
            }
        }
        binding.iHome.arrowIndicatorAddress.setOnClickListener {
            if (binding.iHome.rvSelectAddress.visibility == View.VISIBLE) {
                binding.iHome.rvSelectAddress.visibility = View.GONE
                binding.iHome.arrowIndicatorAddress.rotation = 0f
            } else {
                binding.iHome.rvSelectAddress.visibility = View.VISIBLE
                binding.iHome.arrowIndicatorAddress.rotation = 180f
            }
        }
        binding.iHome.llCountryList.setOnClickListener {
            if (binding.iHome.rvSelectCountry.visibility == View.VISIBLE) {
                binding.iHome.rvSelectCountry.visibility = View.GONE
                binding.iHome.arrowIndicatorCountry.rotation = 0f
            } else {
                binding.iHome.rvSelectCountry.visibility = View.VISIBLE
                binding.iHome.arrowIndicatorCountry.rotation = 180f
            }
        }
        binding.iHome.arrowIndicatorCountry.setOnClickListener {
            if (binding.iHome.rvSelectCountry.visibility == View.VISIBLE) {
                binding.iHome.rvSelectCountry.visibility = View.GONE
                binding.iHome.arrowIndicatorCountry.rotation = 0f
            } else {
                binding.iHome.rvSelectCountry.visibility = View.VISIBLE
                binding.iHome.arrowIndicatorCountry.rotation = 180f
            }
        }

        binding.iHome.llProvinceList.setOnClickListener {
            if (binding.iHome.rvSelectProvince.visibility == View.VISIBLE) {
                binding.iHome.rvSelectProvince.visibility = View.GONE
                binding.iHome.arrowIndicatorProvince.rotation = 0f
            } else {
                binding.iHome.rvSelectProvince.visibility = View.VISIBLE
                binding.iHome.arrowIndicatorProvince.rotation = 180f
            }
        }
        binding.iHome.arrowIndicatorProvince.setOnClickListener {
            if (binding.iHome.rvSelectProvince.visibility == View.VISIBLE) {
                binding.iHome.rvSelectProvince.visibility = View.GONE
                binding.iHome.arrowIndicatorProvince.rotation = 0f
            } else {
                binding.iHome.rvSelectProvince.visibility = View.VISIBLE
                binding.iHome.arrowIndicatorProvince.rotation = 180f
            }
        }

        binding.iHome.llCityList.setOnClickListener {
            if (binding.iHome.rvSelectCity.visibility == View.VISIBLE) {
                binding.iHome.rvSelectCity.visibility = View.GONE
                binding.iHome.arrowIndicatorCity.rotation = 0f
            } else {
                binding.iHome.rvSelectCity.visibility = View.VISIBLE
                binding.iHome.arrowIndicatorCity.rotation = 180f
            }
        }
        binding.iHome.arrowIndicatorCity.setOnClickListener {
            if (binding.iHome.rvSelectCity.visibility == View.VISIBLE) {
                binding.iHome.rvSelectCity.visibility = View.GONE
                binding.iHome.arrowIndicatorCity.rotation = 0f
            } else {
                binding.iHome.rvSelectCity.visibility = View.VISIBLE
                binding.iHome.arrowIndicatorCity.rotation = 180f
            }
        }

        binding.arrowWeightSpinner.setOnClickListener {
            binding.spinnerWeight.performClick()
        }
        binding.arrowDeliveryPeriodic.setOnClickListener {
            binding.deliveryPeriodic.performClick()
        }
        if (navArgs.subscriptionId?.isNotEmpty() == true) {
            viewModel.getShowSubscription(navArgs.subscriptionId!!)
        } else {
            viewModel.showProfile()
            binding.showInformation.text = getString(R.string.subscribe)
        }
        // call show subscription

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbtakeaway -> {
                    selectedRadio = R.id.rbtakeaway
                    checkEnableShopInformationButton()
                    binding.iTakeAway.root.visibility = View.VISIBLE
                    binding.iLocker.root.visibility = View.GONE
                    binding.iHome.root.visibility = View.GONE

                }

                R.id.rblocker -> {
                    selectedRadio = R.id.rblocker

                    checkEnableShopInformationButton()
                    binding.iTakeAway.root.visibility = View.GONE
                    binding.iLocker.root.visibility = View.VISIBLE
                    binding.iHome.root.visibility = View.GONE

                }

                R.id.rbhome -> {
                    selectedRadio = R.id.rbhome
                    checkEnableShopInformationButton()
                    binding.iTakeAway.root.visibility = View.GONE
                    binding.iLocker.root.visibility = View.GONE
                    binding.iHome.root.visibility = View.VISIBLE

                }
            }
        }

        binding.iTakeAway.etZipCode.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
            if (binding.iTakeAway.etZipCode.text.length == 5) {
                binding.iTakeAway.etZipCode.error = null
            } else {
                binding.iTakeAway.etZipCode.error = "zipCode must be 5 digits"
            }
        }

        binding.iLocker.etZipCode.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
            if (binding.iLocker.etZipCode.text.length == 5) {
                binding.iLocker.etZipCode.error = null
            } else {
                binding.iLocker.etZipCode.error = "zipCode must be 5 digits"
            }
        }

        binding.iHome.etZipCode.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
            if (binding.iHome.etZipCode.text.length == 5) {
                binding.iHome.etZipCode.error = null
            } else {
                binding.iHome.etZipCode.error = "zipCode must be 5 digits"
            }
        }



        binding.iTakeAway.etNombre.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.iLocker.etNombre.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.iHome.etNombre.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }



        binding.iTakeAway.etNumero.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }

        binding.iLocker.etNumero.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }

        binding.iHome.etNumero.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }



        binding.iTakeAway.etCorreo.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }

        binding.iLocker.etCorreo.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }

        binding.iHome.etCorreo.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }
        binding.iHome.etAddress.doOnTextChanged { _, _, _, _ ->
            checkEnableShopInformationButton()
        }

        binding.header.back.setOnClickListener {
            navController.popBackStack()
        }

        binding.deliveryPeriodic.adapter = SpinnerAdapter(
            requireActivity(), list, R.color.white, Gravity.CENTER_VERTICAL
        )

        setSpinner()
    }
    private fun renderGetAddresses(data: List<AddressesData>, refreshData: (() -> Unit)?) {

        if (data.isEmpty()) {
            binding.iHome.llAddressnameandlist.visibility = View.GONE
        } else {
            binding.iHome.llAddressnameandlist.visibility = View.VISIBLE
        }
        when {
            data.isEmpty() -> refreshData?.invoke()
            else -> {
                addressListAdapter.submitList(data)
                // Set click listener to handle address selection
                addressListAdapter.clickListenerEditAddresses = {
                    onAddressSelected(addressListAdapter.selectedLocation)
                    // Update UI with selected address details
                    binding.iHome.etAddress.text = Editable.Factory.getInstance().newEditable(selectedLocation.address)
                    binding.iHome.etZipCode.text = Editable.Factory.getInstance().newEditable(selectedLocation.zip)
                    binding.iHome.etNombre.text = Editable.Factory.getInstance().newEditable(selectedLocation.user_name ?: "")
                    binding.iHome.etCorreo.text = Editable.Factory.getInstance().newEditable(selectedLocation.email ?: "")
                    binding.iHome.etNumero.text = Editable.Factory.getInstance().newEditable(selectedLocation.phone ?: "")
                    // Perform any additional actions based on selected address
                    // For example, you might enable/disable a button based on the selection
                    checkEnableShopInformationButton()
                }


            }
        }
    }
    private fun renderGetCities(data: List<FilterCitiesData>, refreshData: (() -> Unit)?) {
        if (data.isEmpty()) {
            isCountrySelected = false
        }
        when {
            data.isEmpty() -> refreshData?.invoke()
            else -> {
                countryAdapter.submitList(data) { updateAdaptersCountry(data) }
                countryAdapter.clickListenerEditCountry = {
                    isCountrySelected = true
                    checkEnableShopInformationButton()

                }
            }
        }
    }

    private fun updateAdaptersCountry(data: List<FilterCitiesData>) {
        countryAdapter.currentList.forEach { country ->
            country.isSelected = country.id == selectedLocation.country_id
            isCountrySelected = country.isSelected || isCountrySelected
            checkEnableShopInformationButton()
        }
        countryAdapter.notifyDataSetChanged()


        provinceAdapter.submitList(data.firstOrNull()?.provinces) { updateAdapterProvince(data) }
        if (data.firstOrNull()?.provinces?.isEmpty() == true) {
            isProvinceSelected = false
        } else {
            provinceAdapter.clickListenerEditProvince = {
                isProvinceSelected = true
                checkEnableShopInformationButton()
            }
        }
    }
    private  fun updateAdapterProvince(data: List<FilterCitiesData>){
        // Check the province_id in addressData against the id of each province in the adapter
        provinceAdapter.currentList.forEach { province ->
            province.isSelected = province.id == selectedLocation.province_id
            isProvinceSelected = province.isSelected || isProvinceSelected
            checkEnableShopInformationButton()
        }
        provinceAdapter.notifyDataSetChanged()

        cityAdapter.submitList(data.firstOrNull()?.provinces?.firstOrNull()?.cities) {updateAdapterCity()}
        if (data.firstOrNull()?.provinces?.firstOrNull()?.cities?.isEmpty() == true) {
            isCitySelected = false
        } else {
            cityAdapter.clickListenerEditCity = {
                isCitySelected = true
                checkEnableShopInformationButton()
            }
        }
    }
    private fun updateAdapterCity(){
        // Check the city_id in addressData against the id of each city in the adapter
        cityAdapter.currentList.forEach { city ->
            city.isSelected = city.id == selectedLocation.city_id
            isCitySelected = city.isSelected || isCitySelected
            checkEnableShopInformationButton()
        }
        cityAdapter.notifyDataSetChanged()
    }



    private fun renderGetCoffeeShop(data: List<CoffeeShop>, refreshData: (() -> Unit)?) {
        if (data.isEmpty()) {
            isShopSelected = false
            binding.iTakeAway.noCoffeeShops.visibility = View.VISIBLE
        }
        when {
            data.isEmpty() -> refreshData?.invoke()
            else -> {
                coffeeListAdapter.submitList(data)
                coffeeListAdapter.clickListenerEditCoffeeShop = {
                    isShopSelected = true
                    checkEnableShopInformationButton()
                }

            }
        }
        coffeeListAdapter.currentList.forEach { coffee ->
            coffee.isSelected = coffee.id == subscriptionData?.coffee_shop_id
            isShopSelected = coffee.isSelected || isShopSelected
            checkEnableShopInformationButton()
        }
        coffeeListAdapter.notifyDataSetChanged()
    }

    private fun renderGetLockers(data: List<LockerData>, refreshData: (() -> Unit)?) {
        if (data.isEmpty()) {
            isLockerSelected = false
            binding.iLocker.noLockers.visibility = View.VISIBLE
        }
        when {
            data.isEmpty() -> refreshData?.invoke()
            else -> {
                lockerListAdapter.submitList(data)
                lockerListAdapter.clickListenerEditLocker = {
                    isLockerSelected = true
                    checkEnableShopInformationButton()
                }

            }
        }
        lockerListAdapter.currentList.forEach { locker ->
            locker.isSelected = locker.id == subscriptionData?.locker_location
            isLockerSelected = locker.isSelected || isLockerSelected
            checkEnableShopInformationButton()
        }
        lockerListAdapter.notifyDataSetChanged()

    }


    private fun renderSizes(plan: PlanDetails) {
        if (plan.sizes.isEmpty()) return
        weightSize = plan.sizes.sortedBy { it.size.weight.toFloatOrNull() }
        weightSize = weightSize.filter { it.size.weight.toFloatOrNull() != null }
//        binding.price.text = "${plan.sizes.first().price} ${Constant.euroSign}"
        selectedSize = weightSize[0]
        // Update price based on the selected size
        updatePriceText()
        binding.spinnerWeight.adapter = SpinnerAdapterForWeight(
            binding.spinnerWeight.context, weightSize
        )
        binding.spinnerWeight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long
            ) {
                selectedSize = weightSize[position]
                // Update price based on the newly selected size
                updatePriceText()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }
    }

    private fun updatePriceText() {
        binding.price.text = getString(R.string.total_cost_placeholder, selectedSize.price, euroSign)
    }

    private fun setSpinnerSize(subscription: SubscriptionData) {
        val nameWithParentheses = "(${subscription.plan_size?.size?.name})"
        binding.spinnerWeight.adapter = SpinnerAdapter(
            requireActivity(), listOf("$nameWithParentheses ${subscription.plan_size?.size?.weight}g"), R.color.white, Gravity.CENTER_VERTICAL
        )
        binding.spinnerWeight.setSpinnerEventsListener()

        binding.spinnerWeight.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                list[i]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                list[0]
            }
        }
    }


    private fun setSpinner() {
        binding.deliveryPeriodic.setSpinnerEventsListener()

        selectedPeriodic = list[0]
        binding.deliveryPeriodic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
                selectedPeriodic = list[i]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun doSubscribeMapping(plan: PlanDetails) {
        viewModel.doSubscribe(
            when (selectedPeriodic) {
                "1 Month" -> "1"
                "3 Months" -> "3"
                "6 Months" -> "6"
                else -> ""
            }, plan.id, selectedSize.size_id, when (selectedRadio) {
                R.id.rbtakeaway -> "take_away"
                R.id.rblocker -> "locker"
                R.id.rbhome -> "delivery"
                else -> ""
            }, when (selectedRadio) {
                R.id.rbtakeaway -> binding.iTakeAway.etZipCode.text.toString()
                R.id.rblocker -> binding.iLocker.etZipCode.text.toString()
                R.id.rbhome -> binding.iHome.etZipCode.text.toString()
                else -> ""
            }, when (selectedRadio) {
                R.id.rbtakeaway -> binding.iTakeAway.etNombre.text.toString()
                R.id.rblocker -> binding.iLocker.etNombre.text.toString()
                R.id.rbhome -> binding.iHome.etNombre.text.toString()
                else -> ""
            }, when (selectedRadio) {
                R.id.rbtakeaway -> binding.iTakeAway.etNumero.text.toString()
                R.id.rblocker -> binding.iLocker.etNumero.text.toString()
                R.id.rbhome -> binding.iHome.etNumero.text.toString()
                else -> ""
            }, when (selectedRadio) {
                R.id.rbtakeaway -> binding.iTakeAway.etCorreo.text.toString()
                R.id.rblocker -> binding.iLocker.etCorreo.text.toString()
                R.id.rbhome -> binding.iHome.etCorreo.text.toString()
                else -> ""
            }, when (selectedRadio) {
                R.id.rbtakeaway -> coffeeListAdapter.currentList.find { it.isSelected }?.address.toString()
                R.id.rblocker -> lockerListAdapter.currentList.find { it.isSelected }?.direccion.toString()
                R.id.rbhome -> binding.iHome.etAddress.text.toString()
                else -> ""
            }, binding.note.text.toString(),
            when (selectedRadio) {
                R.id.rbtakeaway -> coffeeListAdapter.currentList.find { it.isSelected }?.id.toString()
                else -> ""
            },
            when (selectedRadio) {
                R.id.rblocker -> lockerListAdapter.currentList.find { it.isSelected }?.id.toString()
                else -> ""
            }
        )
    }

    private fun doEditSubscriptionMapping(subscription: SubscriptionData) {
        viewModel.doEditSubscribe(
            subscription.id,
            when (selectedPeriodic) {
                "1 Month" -> "1"
                "3 Months" -> "3"
                "6 Months" -> "6"
                else -> ""
            },
            navArgs.plan?.id ?: subscription.plan_id,
            navArgs.plan?.sizes?.get(0)?.size_id ?: subscription.plan_size?.size_id ?: -1,
            binding.note.text.toString(),
            when (selectedRadio) {
                R.id.rbtakeaway -> "take_away"
                R.id.rblocker -> "locker"
                R.id.rbhome -> "delivery"
                else -> ""
            },
            when (selectedRadio) {
                R.id.rbtakeaway -> binding.iTakeAway.etZipCode.text.toString()
                R.id.rblocker -> binding.iLocker.etZipCode.text.toString()
                R.id.rbhome -> binding.iHome.etZipCode.text.toString()
                else -> ""
            },
            when (selectedRadio) {
                R.id.rbtakeaway -> binding.iTakeAway.etNombre.text.toString()
                R.id.rblocker -> binding.iLocker.etNombre.text.toString()
                R.id.rbhome -> binding.iHome.etNombre.text.toString()
                else -> ""
            },
            when (selectedRadio) {
                R.id.rbtakeaway -> binding.iTakeAway.etNumero.text.toString()
                R.id.rblocker -> binding.iLocker.etNumero.text.toString()
                R.id.rbhome -> binding.iHome.etNumero.text.toString()
                else -> ""
            },
            when (selectedRadio) {
                R.id.rbtakeaway -> binding.iTakeAway.etCorreo.text.toString()
                R.id.rblocker -> binding.iLocker.etCorreo.text.toString()
                R.id.rbhome -> binding.iHome.etCorreo.text.toString()
                else -> ""
            },
            "",
            when (selectedRadio) {
                R.id.rbtakeaway -> coffeeListAdapter.currentList.find { it.isSelected }?.id.toString()
                else -> ""
            },
            when (selectedRadio) {
                R.id.rblocker -> lockerListAdapter.currentList.find { it.isSelected }?.id.toString()
                else -> ""
            },
            when (selectedRadio) {
                R.id.rbtakeaway -> coffeeListAdapter.currentList.find { it.isSelected }?.address.toString()
                R.id.rblocker -> lockerListAdapter.currentList.find { it.isSelected }?.direccion.toString()
                R.id.rbhome -> binding.iHome.etAddress.text.toString()
                else -> ""
            }
        )
    }

    private fun renderPlanInformation(plan: PlanDetails) {
        renderSizes(plan)
        binding.showInformation.setOnClickListener {
            when (selectedRadio) {
                R.id.rbhome -> {if(binding.iHome.etAddress.text.toString() == selectedLocation?.address){
                    doSubscribeMapping(plan)
                } else {
                    DialogAddAddress.showTwoButtonPopup(requireContext(), "Desea guardar esta dirección para futuras compras?", {
                        doSubscribeMapping(plan)
                    },


                        {
                            viewModel.doAddAddress(
                                countryAdapter.currentList.find { it.isSelected }?.id.toString(),
                                provinceAdapter.currentList.find { it.isSelected }?.id.toString(),
                                cityAdapter.currentList.find { it.isSelected }?.id.toString(),
                                binding.iHome.etAddress.text.toString(),
                                binding.iHome.etZipCode.text.toString(),
                                binding.iHome.etNombre.text.toString(),
                                binding.iHome.etCorreo.text.toString(),
                                binding.iHome.etNumero.text.toString()
                            )
                            doSubscribeMapping(plan)
                        })
                }
                }


                else -> doSubscribeMapping(plan)
            }

        }
        handleZipCodeChangesTakeAway(plan, null)
        handleZipCodeChangesHome(plan, null)
        handleZipCodeChangesLocker(plan, null)
        binding.planName.text = plan.name
        binding.providerName.text = getString(R.string.provider_name_suffix, plan.provider.commercial_name )
        binding.description.text = plan.description
        binding.namePlan.text = plan.name
        binding.price.text = getString(R.string.total_cost_placeholder, plan.sizes.first().price, euroSign)


    }

    private fun renderProfileState(data: ProfileDetails?) {

        if (data != null) {

            if (data.zip != null) {
                binding.iTakeAway.etZipCode.text = Editable.Factory.getInstance().newEditable(data.zip)
            } else {
                binding.iTakeAway.etZipCode.text = Editable.Factory.getInstance().newEditable("")
            }
            binding.iTakeAway.etNombre.text = Editable.Factory.getInstance().newEditable(data.name)
            if (data.phone != null) {
                binding.iTakeAway.etNumero.text = Editable.Factory.getInstance().newEditable(data.phone)
            } else {
                binding.iTakeAway.etNumero.text = Editable.Factory.getInstance().newEditable("")
            }
            binding.iTakeAway.etCorreo.text = Editable.Factory.getInstance().newEditable(data.email)

            if (data.zip != null) {
                binding.iLocker.etZipCode.text = Editable.Factory.getInstance().newEditable(data.zip)
            } else {
                binding.iLocker.etZipCode.text = Editable.Factory.getInstance().newEditable("")
            }
            binding.iLocker.etNombre.text = Editable.Factory.getInstance().newEditable(data.name)
            if (data.phone != null) {
                binding.iLocker.etNumero.text = Editable.Factory.getInstance().newEditable(data.phone)
            } else {
                binding.iLocker.etNumero.text = Editable.Factory.getInstance().newEditable("")
            }
            binding.iLocker.etCorreo.text = Editable.Factory.getInstance().newEditable(data.email)

            if (data.zip != null) {
                binding.iHome.etZipCode.text = Editable.Factory.getInstance().newEditable(data.zip)
            } else {
                binding.iHome.etZipCode.text = Editable.Factory.getInstance().newEditable("")
            }
            binding.iHome.etNombre.text = Editable.Factory.getInstance().newEditable(data.name)
            if (data.phone != null) {
                binding.iHome.etNumero.text = Editable.Factory.getInstance().newEditable(data.phone)
            } else {
                binding.iHome.etNumero.text = Editable.Factory.getInstance().newEditable("")
            }
            binding.iHome.etCorreo.text = Editable.Factory.getInstance().newEditable(data.email)
        }


    }

    private fun handleShowSubscriptionInformation(subscription: SubscriptionData?) {
        subscriptionData = subscription
        subscription?.let {

            binding.showInformation.setOnClickListener {
                when (selectedRadio) {
                    R.id.rbhome -> {if(binding.iHome.etAddress.text.toString() == selectedLocation?.address){
                        doEditSubscriptionMapping(subscription)
                    } else {DialogAddAddress.showTwoButtonPopup(requireContext(), "Desea guardar esta dirección para futuras compras?", {
                        doEditSubscriptionMapping(subscription)
                    },


                        {
                            viewModel.doAddAddress(
                                countryAdapter.currentList.find { it.isSelected }?.id.toString(),
                                provinceAdapter.currentList.find { it.isSelected }?.id.toString(),
                                cityAdapter.currentList.find { it.isSelected }?.id.toString(),
                                binding.iHome.etAddress.text.toString(),
                                binding.iHome.etZipCode.text.toString(),
                                binding.iHome.etNombre.text.toString(),
                                binding.iHome.etCorreo.text.toString(),
                                binding.iHome.etNumero.text.toString()
                            )
                            doEditSubscriptionMapping(subscription)
                        })}
                    }


                    else -> doEditSubscriptionMapping(subscription)
                }

            }


            if (navArgs.plan == null) {
                setSpinnerSize(subscription)
                binding.price.text = getString(R.string.total_cost_placeholder, subscription.price, euroSign)
                binding.planName.text = subscription.plan?.name
                binding.providerName.text = getString(R.string.provider_name_suffix, subscription.plan?.provider?.commercial_name )
                binding.namePlan.text = subscription.plan?.name
                binding.description.text = subscription.plan?.description
                handleZipCodeChangesTakeAway(null, subscription)
                handleZipCodeChangesHome(null, subscription)
                handleZipCodeChangesLocker(null, subscription)
                binding.iTakeAway.noCoffeeShops.visibility = View.GONE
                binding.iLocker.noLockers.visibility = View.GONE
            }
            val notes = subscription.notes ?: ""
            binding.note.text = Editable.Factory.getInstance().newEditable(notes)

            binding.deliveryPeriodic.setSelection(
                when (subscription.periodicity) {
                    1 -> 0
                    3 -> 1
                    6 -> 2
                    else -> 0
                }
            )

            when (subscription.delivery_type) {
                "delivery" -> {
                    binding.iHome.etZipCode.text = Editable.Factory.getInstance().newEditable(subscription.zip_code)
                    binding.iHome.etAddress.text = Editable.Factory.getInstance().newEditable(subscription.address)
                    binding.iHome.etNombre.text = Editable.Factory.getInstance().newEditable(subscription.name)
                    binding.iHome.etNumero.text = Editable.Factory.getInstance().newEditable(subscription.phone)
                    binding.iHome.etCorreo.text = Editable.Factory.getInstance().newEditable(subscription.email)
                    binding.radioGroup.check(R.id.rbhome)
                }

                "locker" -> {
                    binding.iLocker.etZipCode.text = Editable.Factory.getInstance().newEditable(subscription.zip_code)
                    binding.iLocker.etNombre.text = Editable.Factory.getInstance().newEditable(subscription.name)
                    binding.iLocker.etNumero.text = Editable.Factory.getInstance().newEditable(subscription.phone)
                    binding.iLocker.etCorreo.text = Editable.Factory.getInstance().newEditable(subscription.email)
                    binding.radioGroup.check(R.id.rblocker)
                }

                "take_away" -> {
                    binding.iTakeAway.etZipCode.text = Editable.Factory.getInstance().newEditable(subscription.zip_code)
                    binding.iTakeAway.etNombre.text = Editable.Factory.getInstance().newEditable(subscription.name)
                    binding.iTakeAway.etNumero.text = Editable.Factory.getInstance().newEditable(subscription.phone)
                    binding.iTakeAway.etCorreo.text = Editable.Factory.getInstance().newEditable(subscription.email)
                    binding.radioGroup.check(R.id.rbtakeaway)
                }
            }
        }
    }


    private fun handleZipCodeChangesTakeAway(plan: PlanDetails?, subscription: SubscriptionData?) {
        binding.iTakeAway.etZipCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val providerId = plan?.provider_id ?: subscription?.plan?.provider_id
                if (s?.length == 5 && providerId != null) {
                    viewModel.getCoffeeShop(s.toString(), providerId.toString())
                } else {
                    viewModel.clearCoffeeShop()
                    binding.iTakeAway.etZipCode.error = "Zip code must be 5 digits"
                    binding.iTakeAway.noCoffeeShops.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleZipCodeChangesLocker(@Suppress("UNUSED_PARAMETER") plan: PlanDetails?, @Suppress("UNUSED_PARAMETER") subscription: SubscriptionData?) {
        binding.iLocker.etZipCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 5) {
                    viewModel.getLockers(s.toString())
                } else {
                    viewModel.clearLockers()
                    binding.iLocker.etZipCode.error = "Zip code must be 5 digits"
                    binding.iLocker.noLockers.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleZipCodeChangesHome(@Suppress("UNUSED_PARAMETER") plan: PlanDetails?,@Suppress("UNUSED_PARAMETER") subscription: SubscriptionData?) {
        binding.iHome.etZipCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 5) {
                    viewModel.getCities(s.toString())
                } else {
                    viewModel.clearCities()
                    binding.iHome.etZipCode.error = "Zip code must be 5 digits"
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleAddingAddress(@Suppress("UNUSED_PARAMETER") data: AddressData) {

    }

    private fun renderSubscription(data: SubscriptionData) {

        Toast.makeText(activity, "Subscription Success", Toast.LENGTH_SHORT).show()
        navController.navigate(
            PlanInformationFragmentDirections.actionPlanInformationFragmentToShowInformationFragment(

                subscriptionId = data.id
            )
        )
    }

    private fun renderEditSubscription(data: SubscriptionData) {

        Toast.makeText(activity, "Editing Subscription Success", Toast.LENGTH_SHORT).show()
        navController.navigate(
            PlanInformationFragmentDirections.actionPlanInformationFragmentToShowInformationFragment(

                subscriptionId = data.id
            )
        )
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

                binding.header.photo.setOnClickListener { launcher.launch(Intent(requireActivity(), ProfileActivity::class.java)) }
            } else {

                binding.header.photo.setOnClickListener { startActivity(AuthActivity.callingIntent(requireActivity())) }
            }
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.doGetAddresses()
        }
    }
    private fun onAddressSelected(address: AddressesData?) {
        address?.let { selectedLocation = it }
    }
    private fun showInformationButton(isEnabled: Boolean) {
        binding.showInformation.isEnabled = isEnabled
    }

    private fun checkEnableShopInformationButton() {
        when (selectedRadio) {
            R.id.rbtakeaway -> {
                val zipCodeFilled = binding.iTakeAway.etZipCode.text.length == 5
                val nameShopFilled = binding.iTakeAway.etNombre.text.isNotBlank()
                val numberShopFilled = binding.iTakeAway.etNumero.text.isNotBlank()
                val emailShopFilled = binding.iTakeAway.etCorreo.text.isNotBlank()
                showInformationButton(zipCodeFilled && nameShopFilled && numberShopFilled && emailShopFilled && isShopSelected)
            }

            R.id.rblocker -> {
                val zipCodeFilled = binding.iLocker.etZipCode.text.length == 5
                val nameShopFilled = binding.iLocker.etNombre.text.isNotBlank()
                val numberShopFilled = binding.iLocker.etNumero.text.isNotBlank()
                val emailShopFilled = binding.iLocker.etCorreo.text.isNotBlank()
                showInformationButton(zipCodeFilled && nameShopFilled && numberShopFilled && emailShopFilled && isLockerSelected)
            }

            R.id.rbhome -> {
                val zipCodeFilled = binding.iHome.etZipCode.text.length == 5
                val nameShopFilled = binding.iHome.etNombre.text.isNotBlank()
                val numberShopFilled = binding.iHome.etNumero.text.isNotBlank()
                val emailShopFilled = binding.iHome.etCorreo.text.isNotBlank()
                val addressShopFilled = binding.iHome.etAddress.text.isNotBlank()
                showInformationButton(zipCodeFilled && nameShopFilled && numberShopFilled && emailShopFilled && addressShopFilled && isCountrySelected && isProvinceSelected && isCitySelected)

            }

            else -> Unit
        }
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}