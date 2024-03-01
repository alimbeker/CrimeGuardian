package com.example.crimeguardian

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    private val PICK_CONTACT_REQUEST = 1  // Request code for the intent

    private lateinit var contactNameTextView: TextView
    private lateinit var contactNumberTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        contactNameTextView = view.findViewById(R.id.name)
        contactNumberTextView = view.findViewById(R.id.phone_number)

        val selectContactButton: Button = view.findViewById(R.id.extra_call)
        selectContactButton.setOnClickListener {
            pickContact()
        }

        return view
    }

    private fun pickContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, PICK_CONTACT_REQUEST)
    }


}
