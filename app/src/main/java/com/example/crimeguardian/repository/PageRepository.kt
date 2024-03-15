package com.example.crimeguardian.repository

import com.example.crimeguardian.api.Page
import com.example.crimeguardian.module.ApiClient

class PageRepository(private val apiClient: ApiClient) {

    suspend fun getPageData(): List<Page> {
        return apiClient.getPageData()
    }
}
