package com.example.crimeguardian.data.model


data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>,
)


