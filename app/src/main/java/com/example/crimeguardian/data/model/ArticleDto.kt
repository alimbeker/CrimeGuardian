package com.example.crimeguardian.data.model

data class ArticleDto(
    val source: Source? = null,
    val author: String? = null,
    val title: String? = null,
    val description: String? = null,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
)

data class Source(
    val id: String?,
    val name: String?,
)