package com.example.crimeguardian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crimeguardian.adapter.CrimeNewsAdapter
import com.example.crimeguardian.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var adapter: CrimeNewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]


        setupRecyclerView()

        observeViewModel()

        viewModel.getPageData()

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = CrimeNewsAdapter(maxItems = 5)

        binding.recyclerViewCrimeNews.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@NewsFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        viewModel.newsResponseLiveData.observe(viewLifecycleOwner) { newsResponse ->
            newsResponse?.let {
                adapter.submitArticles(newsResponse.articles)
            }
        }
    }
}
