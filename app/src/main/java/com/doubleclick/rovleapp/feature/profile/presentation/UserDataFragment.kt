package com.doubleclick.rovleapp.feature.profile.presentation

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doubleclick.rovleapp.R
import com.doubleclick.rovleapp.core.extension.failure
import com.doubleclick.rovleapp.core.extension.loading
import com.doubleclick.rovleapp.core.extension.observe
import com.doubleclick.rovleapp.core.extension.observeOrNull
import com.doubleclick.rovleapp.core.extension.viewBinding
import com.doubleclick.rovleapp.core.extension.visible
import com.doubleclick.rovleapp.core.functional.Either
import com.doubleclick.rovleapp.core.functional.PermissionUtil
import com.doubleclick.rovleapp.core.functional.PhotoUtils
import com.doubleclick.rovleapp.core.functional.ProgressHandler
import com.doubleclick.rovleapp.core.functional.sdk33AndUp
import com.doubleclick.rovleapp.core.platform.BaseFragment
import com.doubleclick.rovleapp.feature.profile.data.listAddresses.AddressesData
import com.doubleclick.rovleapp.feature.profile.data.orders.listOrders.Order
import com.doubleclick.rovleapp.feature.profile.data.points.PointsData
import com.doubleclick.rovleapp.feature.profile.data.showProfile.ProfileDetails
import com.doubleclick.rovleapp.feature.profile.data.visits.VisitsData
import com.doubleclick.rovleapp.feature.profile.presentation.adapter.AddressesAdapter
import com.doubleclick.rovleapp.feature.profile.presentation.adapter.OrdersAdapter
import com.doubleclick.rovleapp.feature.profile.presentation.adapter.YourPointsAdapter
import com.doubleclick.rovleapp.feature.profile.presentation.adapter.YourVisitsAdapter
import com.doubleclick.rovleapp.utils.collapse
import com.doubleclick.rovleapp.utils.expand
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.util.UUID

@AndroidEntryPoint
class UserDataFragment : BaseFragment(R.layout.fragment_user_data) {

    private val binding by viewBinding(com.doubleclick.rovleapp.databinding.FragmentUserDataBinding::bind)
    private val yourPointsAdapter = YourPointsAdapter()
    private val yourVisitsAdapter = YourVisitsAdapter()
    private var ordersAdapter = OrdersAdapter(false)
    private val addressesAdapter = AddressesAdapter()
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var storagePermission: PermissionUtil
    private var imagePart: MultipartBody.Part? = null
    private var paymentChoose: Int = 0
    private var isApiCalled = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvYourOrders.adapter = ordersAdapter
        binding.rvYourOrders.addOnScrollListener(ordersScrollListener)
        binding.rvYourPoints.adapter = yourPointsAdapter
        binding.rvYourVisits.adapter = yourVisitsAdapter
        binding.rvYourAddresses.adapter = addressesAdapter
        with(viewModel) {
            observeOrNull(showProfile, ::renderProfileState)
            observe(updateProfile, ::renderUpdateProfileState)
            observe(changePassword, ::renderChangePassword)
            observe(listOrders) { orders -> handleListOrdersResponse(orders) { ordersAdapter.submitList(null) } }
            observe(showPoints) { points -> handleShowPointsResponse(points) { yourPointsAdapter.submitList(null) } }
            observe(showVisits) { visits -> handleShowVisitsResponse(visits) { yourVisitsAdapter.submitList(null) } }
            observeOrNull(getAddresses) { addresses ->
                if (addresses != null) {
                    handleListAddressesResponse(addresses) { addressesAdapter.submitList(null) }
                }
            }
            observe(logout, ::renderLogout)
            loading(loading, ::renderLoading)
            failure(failure, ::handleFailure)
            showProfile()
            showPoints()
            showVisits()
            doGetAddresses()

        }
        if (!isApiCalled) {
            viewModel.resetOrdersPage()
            viewModel.listOrders()
            isApiCalled = true
        }

        initListeners()
        checkingPaymentMethod()
        binding.llDireccionDeEntrega.setOnClickListener {
            if (binding.rvYourAddresses.visibility == View.GONE) {
                binding.arrowDireccionDeEntrega.rotation = 0f
                binding.rvYourAddresses.visibility = View.VISIBLE
                expand(binding.rvYourAddresses)
            } else {
                binding.arrowDireccionDeEntrega.rotation = -90f
                collapse(binding.rvYourAddresses)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Reset the flag when the fragment is paused
        isApiCalled = false
    }
    private fun checkingPaymentMethod() {
        viewLifecycleOwner.lifecycleScope.launch {
            when (appSettingsSource.getPaymentMethod()) {
                0 -> {
                    // Do nothing, no radio button checked
                }
                1 -> binding.byVisa.isChecked = true
                2 -> binding.byGoogle.isChecked = true
            }
        }
    }
    private fun initListeners() {
        binding.changePhoto.setOnClickListener { storagePermission.checkPermissions() }
        binding.logout.setOnClickListener {
            viewModel.doLogout()
        }

        binding.back.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.setResult(RESULT_OK)
                isEnabled = false
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        })

        binding.showAll.setOnClickListener {
            findNavController().navigate(UserDataFragmentDirections.actionUserDataFragmentToYourOrdersFragment())
        }

        binding.detailsMetodoDePago.setOnCheckedChangeListener { _, checkedId ->
            paymentChoose = when (checkedId) {
                R.id.by_visa -> 1
                R.id.by_google -> 2
                else -> 0
            }
            savePaymentChoiceToDatabase(paymentChoose)
        }



        binding.layoutYourAccount.setOnClickListener {
            if (binding.detailsYourAccount.visibility == View.GONE) {
                binding.imageTuCuenta.rotation = 0f
                binding.detailsYourAccount.visibility = View.VISIBLE
//                binding.detailsYourAccount.expand(binding.detailsYourAccount)
            } else {
//                binding.detailsYourAccount.collapse(binding.detailsYourAccount)
                binding.detailsYourAccount.visibility = View.GONE
                binding.imageTuCuenta.rotation = -90f
            }
        }



            binding.layoutUserData.setOnClickListener {
            if (binding.detailsUserData.visibility == View.GONE) {
                binding.arrowDatosDeUsuaria.rotation = 0f
                binding.detailsUserData.visibility = View.VISIBLE
//                binding.detailsUserData.expand(binding.detailsUserData)
            } else {
                binding.arrowDatosDeUsuaria.rotation = -90f
//                binding.detailsUserData.collapse(binding.detailsUserData)
                binding.detailsUserData.visibility = View.GONE
            }
        }

        binding.layoutPaymentMethod.setOnClickListener {
            if (binding.detailsMetodoDePago.visibility == View.GONE) {
                binding.arrowMetodoDePago.rotation = 0f
                binding.detailsMetodoDePago.visibility = View.VISIBLE
//                binding.detailsMetodoDePago.expand(binding.detailsMetodoDePago)
            } else {
                binding.detailsMetodoDePago.visibility = View.GONE
//                binding.arrowMetodoDePago.rotation = -90f
//                binding.detailsMetodoDePago.collapse(binding.detailsMetodoDePago)
            }
        }
        binding.detailsPoints.setOnClickListener {

            if (binding.rvYourPoints.visibility == View.GONE) {
                binding.imagePoints.rotation = 0f
                binding.rvYourPoints.visibility = View.VISIBLE
                if (yourPointsAdapter.itemCount == 0) {
                    Toast.makeText(context, "You have no points", Toast.LENGTH_SHORT).show()
                }

            } else {
                binding.imagePoints.rotation = -90f
                binding.rvYourPoints.visibility = View.GONE
            }
        }
        binding.detailsVisits.setOnClickListener {

            if (binding.rvYourVisits.visibility == View.GONE) {
                binding.imageVisits.rotation = 0f
                binding.rvYourVisits.visibility = View.VISIBLE
                if (yourVisitsAdapter.itemCount == 0) {
                    Toast.makeText(context, "You have no visits", Toast.LENGTH_SHORT).show()
                }

            } else {
                binding.imageVisits.rotation = -90f
                binding.rvYourVisits.visibility = View.GONE
            }
        }

        ordersAdapter.clickDetails = { order ->
            findNavController().navigate(
                UserDataFragmentDirections.actionUserDataFragmentToFacturaFragment(order)
            )
        }

        ordersAdapter.clickRateOrder = {  order ->
            findNavController().navigate(
                UserDataFragmentDirections.actionUserDataFragmentToProductsInOrderFragment(order)
            )
        }

        addressesAdapter.clickListenerEdit = { addressId ->
            findNavController().navigate(
                UserDataFragmentDirections.actionUserDataFragmentToAddressFragment(addressId)
            )
        }

        binding.addAddress.setOnClickListener {
            findNavController().navigate(
                UserDataFragmentDirections.actionUserDataFragmentToAddressFragment(null)
            )
        }
    }
    private fun savePaymentChoiceToDatabase(method: Int) {
        viewLifecycleOwner.lifecycleScope.launch { appSettingsSource.setPaymentMethod(method) }
    }
    private fun renderProfileState(data: ProfileDetails?) {

        data?.let {
            binding.profileContainer.visible()
            if (!data.image.isNullOrEmpty()) {
                binding.userPhoto.load(data.image) {
                    crossfade(true)
                    placeholder(R.drawable.image)
                    error(R.drawable.image)
                }
            }
            binding.name.text = data.name
            binding.userFirstName.text = Editable.Factory.getInstance().newEditable(data.name)
            binding.userEmail.text = Editable.Factory.getInstance().newEditable(data.email)
        }
        binding.save.setOnClickListener {

            viewModel.updateProfile(
                binding.userFirstName.text.toString(), null, "", data?.address.toString(), null, when (data?.card_id) {
                    null -> ""
                    else -> data.card_id
                }, when (data?.country_id) {
                    null -> ""
                    else -> data.country_id
                }, when (data?.province_id) {
                    null -> ""
                    else -> data.province_id
                }, when (data?.city_id) {
                    null -> ""
                    else -> data.city_id
                }, imagePart, binding.userEmail.text.toString()
            )

        }

        binding.modifyPassword.setOnClickListener {

            viewModel.changePassword(
                binding.oldPassword.text.toString(),
                binding.password.text.toString(),
                binding.passwordConfirmation.text.toString()
            )
        }

    }

    private fun renderUpdateProfileState(data: ProfileDetails) {

        data.let {
            binding.profileContainer.visible()
            binding.name.text = data.name
            binding.userFirstName.text = Editable.Factory.getInstance().newEditable(data.name)
            binding.userEmail.text = Editable.Factory.getInstance().newEditable(data.email)
            Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()

        }
    }

    private fun renderChangePassword(@Suppress("UNUSED_PARAMETER") data: ProfileDetails) {

        Toast.makeText(context, "you changed your password successfully", Toast.LENGTH_SHORT).show()
    }


    private fun handleListAddressesResponse(data: List<AddressesData>, refreshData: (() -> Unit)?) {
        when {
            data.isEmpty() -> {
                refreshData?.invoke()
            }

            else -> addressesAdapter.submitList(data)
        }
    }

    private fun handleListOrdersResponse(orders: List<Order>, refreshData: (() -> Unit)?) {
        when {
            orders.isEmpty() -> {
                disableOrders()
                refreshData?.invoke()
            }

            else -> {
                ordersAdapter.submitList(orders)
                disableOrders()
            }
        }
    }

    private fun handleShowPointsResponse(data: List<PointsData>, refreshData: (() -> Unit)?) {
        when {
            data.isEmpty() -> {
                refreshData?.invoke()
            }

            else -> yourPointsAdapter.submitList(data)
        }
    }

    private fun handleShowVisitsResponse(data: List<VisitsData>, refreshData: (() -> Unit)?) {
        when {
            data.isEmpty() -> {
                refreshData?.invoke()
            }

            else -> yourVisitsAdapter.submitList(data)
        }
    }

    private fun disableOrders() {

        binding.layoutYourOrders.isClickable = true
        binding.layoutYourOrders.setOnClickListener {
            if (binding.yourOrdersWithButton.visibility == View.GONE) {
                binding.imageTusPedidos.rotation = 0f
                binding.yourOrdersWithButton.visibility = View.VISIBLE
                if (ordersAdapter.itemCount == 0) {
                    Toast.makeText(context, "You have no orders", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.yourOrdersWithButton.visibility = View.GONE
                binding.imageTusPedidos.rotation = -90f
            }
        }
    }

    var ordersIsLoading = false
    var ordersIsScrolling = false


    private val ordersScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val ordersLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val ordersFirstVisibleItemPosition = ordersLayoutManager.findFirstVisibleItemPosition()
            val ordersVisibleItemCount = ordersLayoutManager.childCount
            val ordersTotalItemCount = ordersLayoutManager.itemCount

            val ordersIsNotLoadingAndNotLastPage = !ordersIsLoading && !viewModel.ordersIsLastPage
            val ordersIsAtLastItem =
                ordersFirstVisibleItemPosition + ordersVisibleItemCount >= ordersTotalItemCount
            val ordersIsNotAtBeginning = ordersFirstVisibleItemPosition >= 0
            val ordersIsTotalMoreThanVisible =
                ordersTotalItemCount >= viewModel.ordersQUERYPAGESIZE
            val ordersShouldPaginate =
                ordersIsNotLoadingAndNotLastPage && ordersIsAtLastItem && ordersIsNotAtBeginning && ordersIsTotalMoreThanVisible && ordersIsScrolling
            if (ordersShouldPaginate) {
                viewModel.listOrders()
                ordersIsScrolling = false
            } else {
                binding.rvYourOrders.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                ordersIsScrolling = true
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        initPermissions()
    }


    private fun initPermissions() {
        storagePermission = PermissionUtil(
            requireActivity(),
            listOf(sdk33AndUp { Manifest.permission.READ_MEDIA_IMAGES } ?: Manifest.permission.READ_EXTERNAL_STORAGE),
            getString(R.string.storage_permission_denied)) {
            photoPicker.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also { it.type = "image/*" })
        }
    }

    private val photoPicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Get the selected photo from the data intent.
            result.data?.data?.let { photoUrl ->
                val photoPath = PhotoUtils.getPath(requireContext(), photoUrl)
                val bitmap = (BitmapFactory.decodeFile(photoPath))
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
                val imageByteArray = outputStream.toByteArray()
                val requestFile = imageByteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
                imagePart = MultipartBody.Part.createFormData("image", "${UUID.randomUUID()}.jpg", requestFile)
                displaySelectedImage(photoPath)
            }
        } else {
            Toast.makeText(requireContext(), "You haven't picked Image", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun displaySelectedImage(photoPath: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.userPhoto.load(BitmapFactory.decodeFile(photoPath))
            }
        }
    }


    private fun renderLogout(@Suppress("UNUSED_PARAMETER") data: String) {
        finishAffinity(requireActivity())
        navigator.showAuth(requireActivity())
    }

    private fun renderLoading(loading: Either.Loading) {
        ProgressHandler.handleProgress(loading.isLoading, requireContext())
    }
}