package com.example.crimeguardian.presentation.profile.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
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

    fun pickContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, PermissionCode.CONTACT_PICK.ordinal)
    }

    fun makeCall(contactNumber: String) {
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
    }

    fun handleContactPickCancelled() {
        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
    }

    fun updateUI(contactDetails: ContactDetails) {
        val shortName = getShortenedName(contactDetails.contactName)
        binding.contactName.text = contactDetails.contactName
        binding.shortName.text = shortName
        binding.phoneNumber.text = contactDetails.contactNumber
        binding.phoneNumber1.text = contactDetails.contactNumber
    }

    private fun getShortenedName(contactName: String): String {
        return contactName.replace(Regex("[^\\p{L} ]"), "").split(" ")
            .filter { it.isNotEmpty() }.joinToString("") { it[0].uppercase() }
    }
}
