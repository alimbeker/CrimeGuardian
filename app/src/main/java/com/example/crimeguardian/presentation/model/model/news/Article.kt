package com.example.crimeguardian.presentation.model.model.news

import com.example.crimeguardian.data.model.Source

data class Article(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val sourceName: String,
)
