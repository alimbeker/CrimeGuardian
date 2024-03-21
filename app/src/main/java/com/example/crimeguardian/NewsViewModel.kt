package com.example.crimeguardian

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crimeguardian.api.Page
import com.example.crimeguardian.module.TengriNewsApiData
import com.example.crimeguardian.repository.PageRepository
import com.example.crimeguardian.repository.PageRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewsViewModel : BaseViewModel() {
    private val repository: PageRepository = PageRepositoryImpl(TengriNewsApiData.getApi())

    private val _pageData = MutableLiveData<Page?>()
    val pageData: LiveData<Page?> = _pageData

    fun getPageData() {
        launch(
            request = {
                repository.getPageData()
            },
            onSuccess = {
                _pageData.postValue(it)
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
