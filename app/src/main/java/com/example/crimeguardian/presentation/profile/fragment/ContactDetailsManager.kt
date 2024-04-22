package com.example.crimeguardian.presentation.profile.fragment

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import com.example.crimeguardian.presentation.model.model.ContactDetails

object ContactDetailsManager {

    fun getContactDetails(context: Context, uri: Uri): ContactDetails {
        val contactId = getContactId(context, uri)
        val contactName = getContactName(context, contactId)
        val contactNumber = getContactPhoneNumber(context, contactId)

        return ContactDetails(contactId, contactName, contactNumber)
    }

    private fun getContactId(context: Context, uri: Uri): String {
        val cursor = context.contentResolver.query(
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

    private fun getContactName(context: Context, contactId: String): String {
        val projection = arrayOf(ContactsContract.Contacts.DISPLAY_NAME)
        val cursor = context.contentResolver.query(
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

    private fun getContactPhoneNumber(context: Context, contactId: String): String? {
        val cursor = context.contentResolver.query(
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
}