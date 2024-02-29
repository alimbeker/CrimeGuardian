package com.example.crimeguardian.data

data class CrimeNews(
    val imageResId: Int,  // You can use Int if you are using drawable resource ID
    val crimeType: String,
    val crimeDescriptionResId: Int
)
