package com.example.crimeguardian.presentation.profile.fragment

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


interface ContactSelectionListener {
    fun onContactSelected(uri: Uri)
    fun onContactSelectionCancelled()
}

class ContactManager(private val listener: ContactSelectionListener) {

    fun handleContactSelection(fragment: Fragment) {
        if (PermissionManager.checkContactPermission(fragment.requireContext())) {
            fragment
        } else {
            PermissionManager.requestContactPermission(
                fragment,
                PermissionCode.CONTACT_PERMISSION.ordinal
            )
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == PermissionCode.CONTACT_PICK.ordinal) {
                data?.data?.let { uri ->
                    listener.onContactSelected(uri)
                } ?: run {
                    throw IllegalStateException("Uri is null")
                }
            }
        } else {
            listener.onContactSelectionCancelled()
        }
    }
}