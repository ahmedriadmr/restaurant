package com.doubleclick.restaurant.utils.dialog

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.doubleclick.domain.ts.OnClickAlert
import com.doubleclick.restaurant.databinding.AlertAddCategoryBinding
import com.doubleclick.restaurant.swipetoactionlayout.utils.getFileName
import com.doubleclick.restaurant.utils.UploadRequestBody
import com.doubleclick.restaurant.superbottomsheet.SuperBottomSheetFragment
import com.iceteck.silicompressorr.SiliCompressor
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

private const val TAG = "AddCategoryDialog"

@AndroidEntryPoint
class AddCategoryDialog(private val onClickAlert: OnClickAlert) : SuperBottomSheetFragment(),
    UploadRequestBody.UploadCallback {

    private lateinit var binding: AlertAddCategoryBinding
    private lateinit var body: UploadRequestBody
    private var uri: Uri? = null
//    private val mainViewModel by viewModels<MainViewModel>()

    val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        try {
            this.uri = uri
            binding.image.setImageURI(uri)
            val filePath = SiliCompressor.with(requireActivity()).compress(
                uri.toString(), File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .toString() + "/restaurant/images/"
                )
            )
            val parcelFileDescriptor = requireActivity().contentResolver.openFileDescriptor(
                Uri.parse(filePath)!!, "r", null
            ) ?: return@registerForActivityResult
            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(
                requireActivity().cacheDir,
                requireActivity().contentResolver.getFileName(Uri.parse(filePath)!!)
            )
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            body = UploadRequestBody(file, "image", this@AddCategoryDialog)
        } catch (e: NullPointerException) {
            Log.e("registerForActivity", "registerForActivityResult: ${e.message}")
        } catch (e: FileNotFoundException) {
            Log.e("registerForActivity", "registerForActivityResult: ${e.message}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = AlertAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()

    }


    private fun onClick() {
        binding.upload.setOnClickListener {
//            upload()
        }
        binding.selectImage.setOnClickListener {
            getContent.launch("image/*")
        }

    }

//    private fun upload() = lifecycleScope.launch {
//        mainViewModel.setCategories(
//            name = binding.name.text.toString(),
//            image = MultipartBody.Part.createFormData(
//                name = "image",
//                filename = "${System.currentTimeMillis()}.jpg",
//                body = body
//            )
//        ).collect { response ->
//            if (response.isSuccessful) {
//                onClickAlert.onClickOk()
//                dismiss()
//                Toast.makeText(
//                    requireActivity(),
//                    getString(R.string.done),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }

    override fun onProgressUpdate(percentage: Int) {
        Log.e(TAG, "onProgressUpdate: $percentage")
    }

    override fun getCornerRadius(): Float = 150f

    override fun animateCornerRadius(): Boolean = true

    override fun animateStatusBar(): Boolean = true

    override fun isSheetAlwaysExpanded(): Boolean = true

    override fun isSheetCancelableOnTouchOutside(): Boolean = true

}