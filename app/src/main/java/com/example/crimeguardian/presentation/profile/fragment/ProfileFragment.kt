package com.example.crimeguardian.presentation.profile.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import com.example.crimeguardian.core.BaseFragment
import com.example.crimeguardian.databinding.FragmentProfileBinding
import com.example.crimeguardian.presentation.model.model.ContactDetails

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate),
    ContactSelectionListener {

    private val contactManager = ContactManager(this)
    private var selectedContactNumber: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        binding.rectangleView.setOnClickListener {
            pickContact()
        }

        binding.extraCall.setOnClickListener {
            selectedContactNumber?.let { makeCall(it) }
                ?: showToast("Number is not initialized")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        contactManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onContactSelected(uri: Uri) {
        val contactDetails = ContactDetailsManager.getContactDetails(requireContext(), uri)
        updateUI(contactDetails)
        selectedContactNumber = contactDetails.contactNumber.toString()
    }

    override fun onContactSelectionCancelled() {
        showToast("Cancelled")
    }

    private fun pickContact() {
        if (PermissionManager.checkPermission(
                requireContext(),
                Manifest.permission.READ_CONTACTS
            )
        ) {
            startActivityForResult(
                Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI),
                PermissionCode.CONTACT_PICK.ordinal
            )
        } else {
            PermissionManager.requestContactPermission(this)
        }
    }

    private fun makeCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")

        if (PermissionManager.checkPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            )
        ) {
            startActivity(intent)
        } else {
            PermissionManager.requestCallPermission(this)
        }
    }

    private fun updateUI(contactDetails: ContactDetails) {
        val shortenedName = ContactNameFormatter.getShortenedName(contactDetails.contactName)
        with(binding) {
            contactName.text = contactDetails.contactName
            shortName.text = shortenedName
            phoneNumber.text = contactDetails.contactNumber
            phoneNumber1.text = contactDetails.contactNumber
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

