package com.doubleclick.restaurant.core.functional

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

class PermissionUtil(
    private val context: FragmentActivity,
    private val permissions: List<String>,
    private val deniedMessage: String,
    private val callback: (() -> Unit)? = null
) {
    private var requestPermissionLauncher: ActivityResultLauncher<Array<String>>? = null
    private var navigateSetting=0
    private var permissionGranted: () -> Unit = { }

    init {
        requestPermissionLauncher = context.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { resultMap ->
            handlePermissionsResult(resultMap)
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun handlePermissionsResult(resultMap: Map<String, Boolean>) {
        val grantedPermissions = mutableListOf<String>()
        val deniedPermissions = mutableListOf<String>()

        resultMap.entries.forEach { entry ->
            if (entry.value) {
                grantedPermissions.add(entry.key)
            } else {
                deniedPermissions.add(entry.key)
            }
        }

        if (deniedPermissions.isEmpty()) {
            callback?.invoke()
            permissionGranted.invoke()
        } else {
            val shouldShowRationale = deniedPermissions.any { permission ->
                ActivityCompat.shouldShowRequestPermissionRationale(context, permission)
            }
            if(deniedPermissions.isNotEmpty() && !shouldShowRationale ) {
                navigateSetting += 1
            }else{
                navigateSetting=1
            }
            if ( navigateSetting>=3) {
                val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", context.packageName, null)
                settingsIntent.data = uri
                context.startActivity(settingsIntent)
            } else {
                Toast.makeText(context, deniedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkPermissions() {
        val permissionsToRequest = permissions.filter { permission ->
            ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher?.launch(permissionsToRequest)
        } else {
            callback?.invoke()
            permissionGranted.invoke()
        }
    }
}