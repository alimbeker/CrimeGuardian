package com.example.crimeguardian.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
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


@Suppress("DEPRECATION")
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    //view binding
    private lateinit var _contactNumber: String

    private var contactNumber: String
        get() = if (::_contactNumber.isInitialized) _contactNumber else ""
        set(value) {
            _contactNumber = value
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //handle click, to pick contact
        binding.rectangleView.setOnClickListener {
            //check permission allowed or not
            if (checkContactPermission()) {
                //allowed
                pickContact()
            } else {
                //not allowed, request
                requestContactPermission(PermissionCode.CONTACT_PERMISSION.ordinal)
            }
        }

        binding.extraCall.setOnClickListener {
            // checking permission
            if (checkContactPermission()) {
                makeCall()
            } else {
                requestContactPermission(PermissionCode.REQUEST_PHONE_CALL.ordinal)
            }
        }
    }

    private fun checkContactPermission(): Boolean {
        //check if permission was granted/allowed or not, returns true if granted/allowed, false if not
        return ContextCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission(PERMISSION_CODE: Int) {
        //request the READ_CONTACTS permission
        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(requireActivity(), permission, PERMISSION_CODE)
    }

    private fun makeCall() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$contactNumber")

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CALL_PHONE),
                PermissionCode.REQUEST_PHONE_CALL.ordinal
            )
        } else {
            // Permission already granted, make the call
            startActivity(intent)
        }
    }


    private fun pickContact() {
        //intent ti pick contact
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, PermissionCode.CONTACT_PICK.ordinal)
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == PermissionCode.CONTACT_PICK.ordinal) {
                data?.data?.let { uri ->
                    handleContactPick(uri)
                } ?: run {
                    throw IllegalStateException("Uri in Cursor1 is null")
                }
            }
        } else {
            handleContactPickCancelled()
        }
    }

    private fun handleContactPick(uri: Uri) {
        val contactDetails = getContactDetails(uri)
        updateUI(contactDetails)
    }

    private fun handleContactPickCancelled() {
        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun getContactDetails(uri: Uri): ContactDetails {
        val contactId = getContactId(uri)
        val contactName = getContactName(contactId)
        val contactNumber = getContactPhoneNumber(contactId)

        return ContactDetails(contactId, contactName, contactNumber)
    }

    private fun getContactId(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(
            uri,
            null,
            null,
            null,
            null
        )
        cursor?.use { cursor ->
            if (cursor.moveToFirst()) {
                val contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                if (contactIdIndex != -1) {
                    return cursor.getString(contactIdIndex)
                }
            }
        }
        throw IllegalStateException("Failed to retrieve contact ID")
    }


    private fun getContactName(contactId: String): String {
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
        val cursor = requireContext().contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            projection,
            "${ContactsContract.Contacts._ID} = ?",
            arrayOf(contactId),
            null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                if (nameIndex != -1) {
                    return it.getString(nameIndex)
                }
            }
        }
        throw IllegalStateException("Failed to retrieve contact name")
    }

    private fun getContactPhoneNumber(contactId: String): String? {
        val cursor = requireContext().contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                val phoneNumberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                if (phoneNumberIndex != -1) {
                    return it.getString(phoneNumberIndex)
                }
            }
        }
        return null
    }


    private fun updateUI(contactDetails: ContactDetails) {
        val shortName = getShortenedName(contactDetails.contactName)
        binding.contactName.text = contactDetails.contactName
        binding.shortName.text = shortName
        binding.phoneNumber.text = contactDetails.contactNumber
        _contactNumber = contactDetails.contactNumber.toString()
        binding.phoneNumber1.text = contactDetails.contactNumber
    }

    private fun getShortenedName(contactName: String): String {
        return contactName.replace(Regex("[^\\p{L} ]"), "").split(" ")
            .filter { it.isNotEmpty() }.joinToString("") { it[0].uppercase() }
    }



}

enum class PermissionCode {
    CONTACT_PERMISSION, CONTACT_PICK, REQUEST_PHONE_CALL
}