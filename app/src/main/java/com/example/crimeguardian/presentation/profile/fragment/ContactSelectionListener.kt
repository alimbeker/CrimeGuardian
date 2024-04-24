package com.example.crimeguardian.presentation.profile.fragment

import android.net.Uri

interface ContactSelectionListener {
    fun onContactSelected(uri: Uri)
    fun onContactSelectionCancelled()
}