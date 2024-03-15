package com.example.crimeguardian

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crimeguardian.api.Page
import com.example.crimeguardian.module.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val apiClient = ApiClient()

    private val _pageData = MutableLiveData<List<Page>>()
    val pageData: LiveData<List<Page>> = _pageData

    fun fetchDataFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val data = apiClient.getPageData()
                _pageData.postValue(data)
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}
