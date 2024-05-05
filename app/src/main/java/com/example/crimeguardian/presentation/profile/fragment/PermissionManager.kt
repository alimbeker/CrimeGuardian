package com.example.crimeguardian.presentation.profile.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionManager {

    fun checkPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(fragment: Fragment, permission: String, permissionCode: Int) {
        ActivityCompat.requestPermissions(fragment.requireActivity(), arrayOf(permission), permissionCode)
    }

    fun requestContactPermission(fragment: Fragment) {
        requestPermission(
            fragment,
            Manifest.permission.READ_CONTACTS,
            PermissionCode.CONTACT_PERMISSION.ordinal
        )
    }

    fun requestCallPermission(fragment: Fragment) {
        requestPermission(
            fragment,
            Manifest.permission.CALL_PHONE,
            PermissionCode.PHONE_CALL_PERMISSION.ordinal
        )
    }

    fun requestLocationPermission(fragment: Fragment) {
        requestPermission(
            fragment,
            Manifest.permission.ACCESS_FINE_LOCATION,
            PermissionCode.LOCATION_PERMISSION.ordinal
        )
    }
}



enum class PermissionCode {
    CONTACT_PERMISSION,
    CONTACT_PICK,
    PHONE_CALL_PERMISSION,
    LOCATION_PERMISSION
}