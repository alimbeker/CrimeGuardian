package com.example.crimeguardian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crimeguardian.adapter.CrimeNewsAdapter
import com.example.crimeguardian.databinding.FragmentNewsBinding
import com.example.crimeguardian.module.ApiClient
import kotlinx.coroutines.*

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var pageAdapter: CrimeNewsAdapter
    private val apiClient = ApiClient()
    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerViewCrimeNews
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Fetch data from API
        fetchDataFromApi()

        return binding.root
    }

    private fun fetchDataFromApi() {
        job = CoroutineScope(Dispatchers.Main).launch {
            try {
                val pageData = apiClient.getPageData()
                // Update RecyclerView with data
                pageAdapter = CrimeNewsAdapter(pageData)
                recyclerView.adapter = pageAdapter
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
