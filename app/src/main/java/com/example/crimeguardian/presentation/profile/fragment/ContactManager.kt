package com.example.crimeguardian.presentation.profile.fragment

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

class ContactManager(private val fragment: ProfileFragment) {

    private lateinit var contactNumber: String

    fun handleContactSelection() {
        if (PermissionManager.checkContactPermission(fragment.requireContext())) {
            fragment.pickContact()
        } else {
            PermissionManager.requestContactPermission(
                fragment,
                PermissionCode.CONTACT_PERMISSION.ordinal
            )
        }
    }

    fun makeCall() {
        if (PermissionManager.checkPhoneCallPermission(fragment.requireContext())) {
            fragment.makeCall(contactNumber)
        } else {
            PermissionManager.requestPhoneCallPermission(
                fragment,
                PermissionCode.REQUEST_PHONE_CALL.ordinal
            )
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == PermissionCode.CONTACT_PICK.ordinal) {
                data?.data?.let { uri ->
                    handleContactPick(uri)
                } ?: run {
                    throw IllegalStateException("Uri is null")
                }
            }
        } else {
            fragment.handleContactPickCancelled()
        }
    }

    private fun handleContactPick(uri: Uri) {
        val contactDetails = ContactDetailsManager.getContactDetails(fragment.requireContext(), uri)
        fragment.updateUI(contactDetails)
        contactNumber = contactDetails.contactNumber.toString()
    }
}