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
import com.example.crimeguardian.databinding.FragmentNewsBinding
import com.example.crimeguardian.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val PICK_CONTACT_REQUEST = 1  // Request code for the intent

    private lateinit var contactNameTextView: TextView
    private lateinit var contactNumberTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        contactNameTextView = binding.name
        contactNumberTextView = binding.phoneNumber

        val selectContactButton = binding.extraCall
        selectContactButton.setOnClickListener {
            pickContact()
        }

        return binding.root
    }

    private fun pickContact() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        startActivityForResult(intent, PICK_CONTACT_REQUEST)
    }


}
