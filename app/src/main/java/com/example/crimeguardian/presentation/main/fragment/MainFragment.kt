package com.example.crimeguardian.presentation.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.crimeguardian.R
import com.example.crimeguardian.databinding.FragmentMainBinding
import com.example.crimeguardian.presentation.adapter.DistrictAdapter
import com.example.crimeguardian.presentation.adapter.OffsetDecoration
import com.example.crimeguardian.presentation.model.model.main.District

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val districts = listOf(
        District(R.string.medeu_district, R.drawable.medeu_district),
        District(R.string.almaly_district, R.drawable.almaly_district),
        District(R.string.alatau_district, R.drawable.alatau_district),
        District(R.string.auezov_district, R.drawable.auezov_district),
        District(R.string.bostandyk_district, R.drawable.bostandyk_district),
        District(R.string.turksib_district, R.drawable.turksib_district),
    )

    private lateinit var adapter: DistrictAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDistrictAdapter()
        setupNavigation()
        setupSearchView()
    }

    private fun setupDistrictAdapter() {
        adapter = DistrictAdapter(districts.toMutableList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val offsetDecoration = OffsetDecoration(start = 6, top = 10, end = 6, bottom = 10)
        binding.recyclerView.addItemDecoration(offsetDecoration)
    }

    private fun setupSearchView() {
        val searchView = binding.searchView // Ensure you are using the correct reference
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredDistricts = if (newText.isNullOrBlank()) {
                    districts
                } else {
                    districts.filter {
                        getString(it.name).contains(newText, ignoreCase = true)
                    }
                }
                adapter.updateDistricts(filteredDistricts)
                return true
            }
        })
    }

    private fun setupNavigation() {
        binding.map.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToIssuesFragment()
            findNavController().navigate(action)
        }

        binding.news.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToNewsFragment()
            findNavController().navigate(action)
        }

        binding.article.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToArticleFragment()
            findNavController().navigate(action)
        }

        binding.sos.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToProfileFragment()
            findNavController().navigate(action)
        }

        binding.instruction.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToInstructionFragment()
            findNavController().navigate(action)
        }

        binding.ai.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAIFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
