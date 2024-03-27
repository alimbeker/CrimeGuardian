package com.example.crimeguardian.presentation.model.model

import com.example.crimeguardian.data.model.ArticleDto

data class NewsResponse(
    val articles: List<ArticleDto>
)