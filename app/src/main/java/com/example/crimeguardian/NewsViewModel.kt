package com.example.crimeguardian

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crimeguardian.data.NewsResponse
import com.example.crimeguardian.module.NewsApiData
import com.example.crimeguardian.repository.PageRepository
import com.example.crimeguardian.repository.PageRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel : BaseViewModel() {
    private val repository: PageRepository = PageRepositoryImpl(NewsApiData.getApi())

    private val _newsResponseLiveData = MutableLiveData<NewsResponse?>()
    val newsResponseLiveData: LiveData<NewsResponse?> = _newsResponseLiveData

    fun getPageData() {
        launch(
            request = {
                repository.getPageData()
            },
            onSuccess = {
                _newsResponseLiveData.postValue(it)
            }
        )
    }
}

abstract class BaseViewModel: ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private var _loadingLiveData = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> = _loadingLiveData

    private var _exceptionLiveData = MutableLiveData<String?>()
    val exceptionLiveData: LiveData<String?> = _exceptionLiveData

    fun <T> launch(
        request: suspend () -> T,
        onSuccess: (T) -> Unit = { }
    ) {
        coroutineScope.launch {
            try {
                _loadingLiveData.postValue(true)
                val response = request.invoke()
                onSuccess.invoke(response)
            } catch (e: Exception) {
                _exceptionLiveData.postValue(e.message)
                Log.e(">>>", e.message.orEmpty())
            } finally {
                _loadingLiveData.postValue(false)
            }
        }
    }
}
