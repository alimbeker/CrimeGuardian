package com.example.crimeguardian.presentation.profile.fragment

object ContactNameFormatter {
    fun getShortenedName(contactName: String): String {
        return contactName.replace(Regex("[^\\p{L} ]"), "").split(" ")
            .filter { it.isNotEmpty() }.joinToString("") { it[0].uppercase() }
    }

}
