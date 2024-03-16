package com.example.crimeguardian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crimeguardian.adapter.CrimeNewsAdapter
import com.example.crimeguardian.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var crimeNewsAdapter: CrimeNewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        recyclerView = binding.recyclerViewCrimeNews
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        // Initialize adapter with empty list or null
        crimeNewsAdapter = CrimeNewsAdapter(emptyList())
        recyclerView.adapter = crimeNewsAdapter


        observeViewModel()

        viewModel.getPageData()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.pageData.observe(viewLifecycleOwner) { pageData ->
            crimeNewsAdapter.updateData(pageData)
        }
    }
}
