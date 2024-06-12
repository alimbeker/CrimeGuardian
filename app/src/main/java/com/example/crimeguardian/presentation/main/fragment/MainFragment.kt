package com.example.crimeguardian.presentation.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
        District(R.string.almaty_district, R.drawable.almaly_district),
        District(R.string.almaty_district, R.drawable.alatau_district),
        District(R.string.auezov_district, R.drawable.auezov_district),
        District(R.string.auezov_district, R.drawable.bostandyk_district),
        District(R.string.auezov_district, R.drawable.auezov_district),
        District(R.string.turksib_district, R.drawable.turksib_district),

        )

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

    }

    private fun setupDistrictAdapter() {
        val adapter = DistrictAdapter(districts)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        val offsetDecoration = OffsetDecoration(start = 6, top = 10, end = 6, bottom = 10)
        binding.recyclerView.addItemDecoration(offsetDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
