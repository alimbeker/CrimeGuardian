package com.example.crimeguardian.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.crimeguardian.core.BaseFragment
import com.example.crimeguardian.databinding.FragmentProfileBinding
import com.example.crimeguardian.presentation.model.model.ContactDetails


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val contactManager = ContactManager(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rectangleView.setOnClickListener {
            contactManager.handleContactSelection()
        }

        binding.extraCall.setOnClickListener {
            contactManager.makeCall()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        contactManager.onActivityResult(requestCode, resultCode, data)
    }
}

class ContactManager(private val fragment: ProfileFragment) {

    private lateinit var contactNumber: String

    fun handleContactSelection() {
        if (PermissionManager.checkContactPermission(fragment.requireContext())) {
            fragment.pickContact()
        } else {
            PermissionManager.requestContactPermission(fragment, PermissionCode.CONTACT_PERMISSION.ordinal)
        }
    }

    fun makeCall() {
        if (PermissionManager.checkPhoneCallPermission(fragment.requireContext())) {
            fragment.makeCall(contactNumber)
        } else {
            PermissionManager.requestPhoneCallPermission(fragment, PermissionCode.REQUEST_PHONE_CALL.ordinal)
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
        contactNumber = contactDetails.contactNumber
    }
}

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

object ContactDetailsManager {

    fun getContactDetails(context: Context, uri: Uri): ContactDetails {
        // Implementation of getContactDetails() function
    }
}
