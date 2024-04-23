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
    private lateinit var contactNumber: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        binding.rectangleView.setOnClickListener {
            pickContact()
        }

        binding.extraCall.setOnClickListener {
            makeCall()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        contactManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onContactSelected(uri: Uri) {
        val contactDetails = ContactDetailsManager.getContactDetails(requireContext(), uri)
        updateUI(contactDetails)
        contactNumber = contactDetails.contactNumber.toString()
    }

    override fun onContactSelectionCancelled() {
        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun pickContact() {
        if (PermissionManager.checkPermission(requireContext(), Manifest.permission.CO)) {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
            startActivityForResult(intent, PermissionCode.CONTACT_PICK.ordinal)
        } else {
            PermissionManager.requestContactPermission(
                this,
                PermissionCode.CONTACT_PERMISSION.ordinal
            )
        }
    }

    private fun makeCall() {
        if (::contactNumber.isInitialized) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$contactNumber")

            if (PermissionManager.checkPhoneCallPermission(requireContext())) {
                startActivity(intent)
            } else {
                PermissionManager.requestPhoneCallPermission(
                    this,
                    PermissionCode.REQUEST_PHONE_CALL.ordinal
                )
            }
        } else {
            Toast.makeText(context, "Number is not initialized", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(contactDetails: ContactDetails) {
        val shortName = ContactNameFormatter.getShortenedName(contactDetails.contactName)
        binding.contactName.text = contactDetails.contactName
        binding.shortName.text = shortName
        binding.phoneNumber.text = contactDetails.contactNumber
        binding.phoneNumber1.text = contactDetails.contactNumber
    }

}
