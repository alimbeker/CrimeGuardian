package com.example.crimeguardian.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crimeguardian.core.functional.Resource
import com.example.crimeguardian.presentation.adapter.OffsetDecoration
import com.example.crimeguardian.presentation.adapter.CrimeNewsAdapter
import com.example.crimeguardian.databinding.FragmentNewsBinding
import com.example.crimeguardian.presentation.viewmodel.NewsViewModel


class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: CrimeNewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]


        setupRecyclerView()

        observeViewModel()

        viewModel.getAllData()

    }

    private fun setupRecyclerView() {
        adapter = CrimeNewsAdapter(maxItems = 5)

        binding.recyclerViewCrimeNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@NewsFragment.adapter
            setHasFixedSize(true)

            val offsetDecoration = OffsetDecoration(start = 4, top = 10, end = 2, bottom = 10)
            binding.recyclerViewCrimeNews.addItemDecoration(offsetDecoration)
        }
    }

    private fun observeViewModel() {
        viewModel.newsResponseLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loading.isVisible = true
                }

                is Resource.Success -> {
                    binding.loading.isVisible = false
                    val articles = resource.data?.articles
                    articles?.let {
                        adapter.submitArticles(it)
                    }
                }

                is Resource.Error -> {
                    binding.loading.isVisible = false

                    Toast.makeText(this.context, resource.exception.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                else -> Unit
            }
        }
    }


}
