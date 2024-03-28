package com.example.crimeguardian.data.mapper

import com.example.crimeguardian.data.model.NewsResponseDto
import com.example.crimeguardian.presentation.model.model.NewsResponse

internal fun NewsResponseDto.toNewsResponse(): NewsResponse {
    return NewsResponse(
        articles = articles.map { it.toArticle() }
    )
}