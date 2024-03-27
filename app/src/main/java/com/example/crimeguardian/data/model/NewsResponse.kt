package com.example.crimeguardian.data.model


data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)


data class NewsApiError(
    val error: NewsApiErrorData? = null
)

data class NewsApiErrorData(
    val code: Int? = null,
    val message: String? = null
)