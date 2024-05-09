package com.example.crimeguardian.presentation.profile.fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.crimeguardian.core.PermissionCode

class ContactManager(private val listener: ContactSelectionListener) {
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

