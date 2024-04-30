package com.doubleclick.restaurant.feature.passport

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.core.extension.viewBinding
import com.doubleclick.restaurant.core.platform.BaseFragment
import com.doubleclick.restaurant.databinding.FragmentQRBinding
import com.doubleclick.restaurant.views.qrgenearator.QRGContents
import com.doubleclick.restaurant.views.qrgenearator.QRGEncoder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QRFragment : BaseFragment(R.layout.fragment_q_r) {
    private lateinit var bitmap: Bitmap
    private lateinit var qrgEncoder: QRGEncoder
    private val binding by viewBinding(FragmentQRBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            if (TextUtils.isEmpty(appSettingsSource.user().first()?.id.toString())) {
                // if the edittext inputs are empty then execute
                // this method showing a toast message.
                Toast.makeText(
                    requireContext(),
                    "You should login first",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.qrImage.visibility = View.VISIBLE
                // below line is for getting
                // the windowmanager service.
                val manager =
                    requireActivity().getSystemService(Context.WINDOW_SERVICE) as WindowManager

                // initializing a variable for default display.
                val display = manager.defaultDisplay

                // creating a variable for point which
                // is to be displayed in QR Code.
                val point = Point()
                display.getSize(point)

                // getting width and
                // height of a point
                val width = point.x
                val height = point.y

                // generating dimension from width and height.
                var dimen = if (width < height) width else height
                dimen = dimen * 3 / 4

                // setting this dimensions inside our qr code
                // encoder to generate our qr code.
                qrgEncoder = QRGEncoder(
                    appSettingsSource.user().first()?.id.toString(),
                    null,
                    QRGContents.Type.TEXT,
                    dimen
                )
                // getting our qrcode in the form of bitmap.
                bitmap = qrgEncoder.bitmap
                // the bitmap is set inside our image
                // view using .setimagebitmap method.
                binding.qrImage.setImageBitmap(bitmap)
            }
        }
    }
}