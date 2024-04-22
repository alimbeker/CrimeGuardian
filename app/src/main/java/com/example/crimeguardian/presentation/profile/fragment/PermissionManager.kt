package com.example.crimeguardian.presentation.profile.fragment

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionManager {

    fun checkContactPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestContactPermission(fragment: Fragment, PERMISSION_CODE: Int) {
        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(fragment.requireActivity(), permission, PERMISSION_CODE)
    }

    fun checkPhoneCallPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, android.Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPhoneCallPermission(fragment: Fragment, PERMISSION_CODE: Int) {
        ActivityCompat.requestPermissions(
            fragment.requireActivity(),
            arrayOf(android.Manifest.permission.CALL_PHONE),
            PERMISSION_CODE
        )
    }
}


enum class PermissionCode {
    CONTACT_PERMISSION, CONTACT_PICK, REQUEST_PHONE_CALL
}