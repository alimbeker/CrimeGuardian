package com.example.crimeguardian.data.model


data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)


data class NewsApiError(
    val error: NewsApiErrorData? = null
)

data class NewsApiErrorData(
    val code: Int? = null,
    val message: String? = null
)