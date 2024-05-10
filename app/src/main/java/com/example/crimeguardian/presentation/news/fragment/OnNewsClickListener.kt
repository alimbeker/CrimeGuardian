package com.example.crimeguardian.presentation.news.fragment

import com.example.crimeguardian.presentation.model.model.news.Article


interface OnNewsClickListener {
    fun onNewsItemClickListener(article: Article)
}