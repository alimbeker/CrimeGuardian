package com.example.crimeguardian.presentation.news.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crimeguardian.core.BaseFragment
import com.example.crimeguardian.core.functional.Resource
import com.example.crimeguardian.databinding.FragmentNewsBinding
import com.example.crimeguardian.presentation.adapter.CrimeNewsAdapter
import com.example.crimeguardian.presentation.adapter.OffsetDecoration
import com.example.crimeguardian.presentation.adapter.ViewPagerAdapter
import com.example.crimeguardian.presentation.adapter.ViewPagerTransition
import com.example.crimeguardian.presentation.news.fragment.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: CrimeNewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        setupViewPager()

        observeViewModel()

        getAllData()

    }

    private fun setupViewPager() {
        //top-headlines
        ViewPagerTransition.transitions(binding.viewPager)

        viewModel.headlinesLiveData.observe(viewLifecycleOwner) {
            resource ->

            when(resource) {
                is Resource.Loading -> {
                    binding.loading.isVisible = true
                }

                is Resource.Success -> {
                    binding.loading.isVisible = false
                    binding.shimmerViewContainerViewPager.stopShimmer()
                    binding.shimmerViewContainerViewPager.visibility = View.GONE
                    binding.viewPager.adapter = ViewPagerAdapter(resource.data)
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

    private fun getAllData() {
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
        viewModel.newsLiveData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.loading.isVisible = true
                }

                is Resource.Success -> {
                    binding.loading.isVisible = false
                    val articles = resource.data
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
