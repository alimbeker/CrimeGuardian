package com.example.crimeguardian

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.crimeguardian.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    //view binding
    private lateinit var binding: FragmentProfileBinding
    private lateinit var contactNumber: String

    //contact permission code
    private val CONTACT_PERMISSION_CODE = 1

    //contact pick code
    private val CONTACT_PICK_CODE = 2

    //contact call code
    private val REQUEST_PHONE_CALL = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        //handle click, to pick contact
        binding.rectangleView.setOnClickListener {
            //check permission allowed or not
            if (checkContactPermission()) {
                //allowed
                pickContact()
            } else {
                //not allowed, request
                requestContactPermission()
            }
        }

        binding.extraCall.setOnClickListener {
            // checking permission
            if (ActivityCompat.checkSelfPermission(
                    requireContext(), android.Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            )
            {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    REQUEST_PHONE_CALL
                )
            } else {
                if(!contactNumber.isNullOrEmpty()) {
                    makeCall()
                }
            }
        }

        return binding.root
    }

    private fun makeCall() {
        val intent = Intent(Intent.ACTION_CALL)

        intent.data = Uri.parse("tel:$contactNumber")

        if (ActivityCompat.checkSelfPermission(
                requireContext(), android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_LONG).show()
            return
        }

        startActivity(intent)
    }


    private fun checkContactPermission(): Boolean {
        //check if permission was granted/allowed or not, returns true if granted/allowed, false if not
        return ContextCompat.checkSelfPermission(
            requireContext(), android.Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestContactPermission() {
        //request the READ_CONTACTS permission
        val permission = arrayOf(android.Manifest.permission.READ_CONTACTS)
        ActivityCompat.requestPermissions(requireActivity(), permission, CONTACT_PERMISSION_CODE)
    }

    private fun pickContact() {
        //intent ti pick contact
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, CONTACT_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode == REQUEST_PHONE_CALL) {
            makeCall()
        }
        //handle permission request results || calls when user from Permission request dialog presses Allow or Deny
        if (requestCode == CONTACT_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //permission granted, can pick contact
                pickContact()
            } else {
                //permission denied, can't pick contact, just show message
                Toast.makeText(this.context, "Permission denied...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //handle intent results || calls when user from Intent (Contact Pick) picks or cancels pick contact
        if (resultCode == AppCompatActivity.RESULT_OK) {
            //calls when user click a contact from contacts (intent) list
            if (requestCode == CONTACT_PICK_CODE) {

                val cursor1: Cursor
                val cursor2: Cursor?

                //get data from intent
                val uri = data!!.data
                cursor1 = requireContext().contentResolver.query(uri!!, null, null, null, null)!!
                if (cursor1.moveToFirst()) {
                    //get contact details
                    val contactId =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID))
                    val contactName =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val idResults =
                        cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                    val idResultHold = idResults.toInt()
                    //set details: contact id, contact name, image
                    val shortName = contactName.replace(Regex("[^\\p{L} ]"), "").split(" ")
                        .filter { it.isNotEmpty() }.joinToString("") { it[0].uppercase() }

                    binding.contactName.text = contactName
                    binding.shortName.text = shortName


                    //check if contact has a phone number or not
                    if (idResultHold == 1) {
                        cursor2 = requireContext().contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                            null,
                            null
                        )
                        //a contact may have multiple phone numbers
                        while (cursor2!!.moveToNext()) {
                            //get phone number
                            contactNumber =
                                cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                            //set phone number
                            binding.phoneNumber.text = contactNumber
                            binding.phoneNumber1.text = contactNumber

                        }

                        cursor2.close()
                    }
                    cursor1.close()
                }
            }

        } else {
            //cancelled picking contact
            Toast.makeText(this.context, "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}