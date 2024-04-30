package com.doubleclick.restaurant.feature.profile.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
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
import com.doubleclick.restaurant.databinding.FragmentAddressBinding
import com.doubleclick.restaurant.feature.profile.ProfileActivity
import com.doubleclick.restaurant.feature.profile.data.addAddress.response.AddressData
import com.doubleclick.restaurant.feature.profile.data.updateAddress.UpdateAddressData
import com.doubleclick.restaurant.feature.shop.cartDetails.data.filterCities.FilterCitiesData
import com.doubleclick.restaurant.feature.shop.cartDetails.presentation.NewFilterCityAdapter
import com.doubleclick.restaurant.feature.shop.cartDetails.presentation.NewFilterCountryAdapter
import com.doubleclick.restaurant.feature.shop.cartDetails.presentation.NewFilterProvinceAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressFragment : BaseFragment(R.layout.fragment_address) {

    private val binding by viewBinding(FragmentAddressBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private val navArgs: AddressFragmentArgs by navArgs()
//    lateinit var selectedCountry: FilterCitiesData
//    lateinit var selectedProvince: Province
//    lateinit var selectedCity: City
    private var isCountrySelected = false
    private var isProvinceSelected = false
    private var isCitySelected = false
    private var addressData: AddressData? = null
    private val countryAdapter = NewFilterCountryAdapter()
    private val provinceAdapter = NewFilterProvinceAdapter()
    private val cityAdapter = NewFilterCityAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSelectCountry.adapter = countryAdapter
        binding.rvSelectProvince.adapter = provinceAdapter
        binding.rvSelectCity.adapter = cityAdapter

        with(viewModel) {
            observe(addAddress, ::handleAddingAddress)
            observe(showAddress, ::renderShowAddress)
            observe(updateAddress, ::handleUpdatingAddress)
            observe(deleteAddress, ::handleDeleteAddress)
            observe(getFilteredCities) { filterCities -> renderGetCities(filterCities) { countryAdapter.submitList(null) } }
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            navArgs.addressId?.let { doShowAddress(it) }
        }
        binding.llCountryList.setOnClickListener {
            if (binding.rvSelectCountry.visibility == View.VISIBLE) {
                binding.rvSelectCountry.visibility = View.GONE
                binding.arrowIndicatorCountry.rotation = 0f
            } else {
                binding.rvSelectCountry.visibility = View.VISIBLE
                binding.arrowIndicatorCountry.rotation = 180f
            }
        }
        binding.arrowIndicatorCountry.setOnClickListener {
            if (binding.rvSelectCountry.visibility == View.VISIBLE) {
                binding.rvSelectCountry.visibility = View.GONE
                binding.arrowIndicatorCountry.rotation = 0f
            } else {
                binding.rvSelectCountry.visibility = View.VISIBLE
                binding.arrowIndicatorCountry.rotation = 180f
            }
        }

        binding.llProvinceList.setOnClickListener {
            if (binding.rvSelectProvince.visibility == View.VISIBLE) {
                binding.rvSelectProvince.visibility = View.GONE
                binding.arrowIndicatorProvince.rotation = 0f
            } else {
                binding.rvSelectProvince.visibility = View.VISIBLE
                binding.arrowIndicatorProvince.rotation = 180f
            }
        }
        binding.arrowIndicatorProvince.setOnClickListener {
            if (binding.rvSelectProvince.visibility == View.VISIBLE) {
                binding.rvSelectProvince.visibility = View.GONE
                binding.arrowIndicatorProvince.rotation = 0f
            } else {
                binding.rvSelectProvince.visibility = View.VISIBLE
                binding.arrowIndicatorProvince.rotation = 180f
            }
        }

        binding.llCityList.setOnClickListener {
            if (binding.rvSelectCity.visibility == View.VISIBLE) {
                binding.rvSelectCity.visibility = View.GONE
                binding.arrowIndicatorCity.rotation = 0f
            } else {
                binding.rvSelectCity.visibility = View.VISIBLE
                binding.arrowIndicatorCity.rotation = 180f
            }
        }
        binding.arrowIndicatorCity.setOnClickListener {
            if (binding.rvSelectCity.visibility == View.VISIBLE) {
                binding.rvSelectCity.visibility = View.GONE
                binding.arrowIndicatorCity.rotation = 0f
            } else {
                binding.rvSelectCity.visibility = View.VISIBLE
                binding.arrowIndicatorCity.rotation = 180f
            }
        }

        when (navArgs.addressId) {
            null -> {
                binding.header.title.text = getString(R.string.add_address)
                binding.submit.text = getString(R.string.confirm)
                binding.cancel.text = getString(R.string.cancel)
                binding.cancel.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
                binding.submit.setOnClickListener {
                    viewModel.doAddAddress(
                        countryAdapter.currentList.find { it.isSelected }?.id.toString(),
                        provinceAdapter.currentList.find { it.isSelected }?.id.toString(),
                        cityAdapter.currentList.find { it.isSelected }?.id.toString(),
                        binding.address.text.toString(),
                        binding.zip.text.toString(),
                        binding.userName.text.toString(),
                        binding.email.text.toString(),
                        binding.phone.text.toString()
                    )
                }
            }

            else -> {
                binding.header.title.text = getString(R.string.edit_address)
                binding.submit.text = getString(R.string.edit)
                binding.cancel.text = getString(R.string.delete_address)
                binding.cancel.setOnClickListener {
                    navArgs.addressId?.let { it1 -> viewModel.doDeleteAddress(it1) }
                }
                binding.submit.setOnClickListener {
                    navArgs.addressId?.let { it1 ->
                        viewModel.doUpdateAddress(
                            it1,
                            countryAdapter.currentList.find { it.isSelected }?.id.toString(),
                            provinceAdapter.currentList.find { it.isSelected }?.id.toString(),
                            cityAdapter.currentList.find { it.isSelected }?.id.toString(),
                            binding.address.text.toString(),
                            binding.zip.text.toString(),
                            binding.userName.text.toString(),
                            binding.email.text.toString(),
                            binding.phone.text.toString()
                        )
                    }
                }


            }
        }



        handleZipCodeChangesHome()

        binding.header.back.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }




        binding.zip.doOnTextChanged { _, _, _, _ ->
            if (binding.zip.text.length == 5) {
                binding.zip.error = null
            } else {
                binding.zip.error = "zipCode must be 5 digits"
            }

            checkEnableSubmitButton()
        }
        binding.address.doOnTextChanged { _, _, _, _ ->
            checkEnableSubmitButton()
        }
        binding.userName.doOnTextChanged { _, _, _, _ ->
            checkEnableSubmitButton()
        }
        binding.phone.doOnTextChanged { _, _, _, _ ->
            checkEnableSubmitButton()
        }
        binding.email.doOnTextChanged { _, _, _, _ ->
            checkEnableSubmitButton()
        }

    }

    private fun handleAddingAddress(@Suppress("UNUSED_PARAMETER") data: AddressData) {
        Toast.makeText(context, "You have successfully added this address", Toast.LENGTH_SHORT).show()
        findNavController().navigate(AddressFragmentDirections.actionAddressFragmentToUserDataFragment())
    }

    private fun renderShowAddress(data: AddressData) {
        addressData = data
        binding.zip.text = Editable.Factory.getInstance().newEditable(data.zip)
        binding.address.text = Editable.Factory.getInstance().newEditable(data.address)
        if (data.user_name == null) {
            binding.userName.text = Editable.Factory.getInstance().newEditable("")
        } else {
            binding.userName.text = Editable.Factory.getInstance().newEditable(data.user_name)
        }

        if (data.email == null) {
            binding.email.text = Editable.Factory.getInstance().newEditable("")
        } else {
            binding.email.text = Editable.Factory.getInstance().newEditable(data.email)
        }

        if (data.phone == null) {
            binding.phone.text = Editable.Factory.getInstance().newEditable("")
        } else {
            binding.phone.text = Editable.Factory.getInstance().newEditable(data.phone)
        }


    }


    private fun handleUpdatingAddress(@Suppress("UNUSED_PARAMETER") data: UpdateAddressData) {
        Toast.makeText(context, "You have successfully updated this address", Toast.LENGTH_SHORT).show()
    }

    private fun handleDeleteAddress(@Suppress("UNUSED_PARAMETER") data: String) {
        Toast.makeText(context, "you have deleted this Address ", Toast.LENGTH_SHORT).show()

        findNavController().navigate(AddressFragmentDirections.actionAddressFragmentToUserDataFragment())
    }

    private fun renderGetCities(data: List<FilterCitiesData>, refreshData: (() -> Unit)?) {
        if (data.isEmpty()) {
            isCountrySelected = false
        }
        when {
            data.isEmpty() -> refreshData?.invoke()
            else -> {
                countryAdapter.submitList(data)
                countryAdapter.clickListenerEditCountry = {
                    isCountrySelected = true
                    checkEnableSubmitButton()
                }
                provinceAdapter.submitList(data.firstOrNull()?.provinces)
                if (data.firstOrNull()?.provinces?.isEmpty() == true){
                    isProvinceSelected = false
                } else {
                    provinceAdapter.clickListenerEditProvince = {
                        isProvinceSelected = true
                        checkEnableSubmitButton()
                    }
                }
                cityAdapter.submitList(data.firstOrNull()?.provinces?.firstOrNull()?.cities)
                if (data.firstOrNull()?.provinces?.firstOrNull()?.cities?.isEmpty()   == true){
                    isCitySelected = false
                } else {
                    cityAdapter.clickListenerEditCity = {
                        isCitySelected = true
                        checkEnableSubmitButton()
                    }
                }
            }
        }
        // Check the country_id in addressData against the id of each country in the adapter
        countryAdapter.currentList.forEach { country ->
            country.isSelected = country.id == addressData?.country_id
            isCountrySelected = country.isSelected || isCountrySelected
            checkEnableSubmitButton()
        }
        countryAdapter.notifyDataSetChanged()

        // Check the province_id in addressData against the id of each province in the adapter
        provinceAdapter.currentList.forEach { province ->
            province.isSelected = province.id == addressData?.province_id
            isProvinceSelected = province.isSelected || isProvinceSelected
            checkEnableSubmitButton()
        }
        provinceAdapter.notifyDataSetChanged()

        // Check the city_id in addressData against the id of each city in the adapter
        cityAdapter.currentList.forEach { city ->
            city.isSelected = city.id == addressData?.city_id
            isCitySelected = city.isSelected || isCitySelected
            checkEnableSubmitButton()
        }
        cityAdapter.notifyDataSetChanged()
    }

    private fun handleZipCodeChangesHome() {
        binding.zip.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 5) {
                    viewModel.getCities(s.toString())
                } else {
                    viewModel.clearCities()
                    binding.zip.error = "Zip code must be 5 digits"
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showSubmitButton(isEnabled: Boolean) {
        binding.submit.isEnabled = isEnabled
    }

    private fun checkEnableSubmitButton() {
        if (view != null) {
            val zip = binding.zip.text.length == 5
            val address = binding.address.text.isNotBlank()
            val userName = binding.userName.text.isNotBlank()
            val phone = binding.phone.text.isNotBlank()
            val email = binding.email.text.isNotBlank()

            showSubmitButton(zip && address && userName && phone && email && isCountrySelected && isProvinceSelected && isCitySelected)
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