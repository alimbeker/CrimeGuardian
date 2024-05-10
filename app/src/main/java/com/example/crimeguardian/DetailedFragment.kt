package com.example.crimeguardian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.crimeguardian.databinding.FragmentDetailedBinding
import com.example.crimeguardian.databinding.TopHeadlinesBinding


class DetailedFragment : Fragment(R.layout.fragment_detailed) {

    private lateinit var binding: FragmentDetailedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = DetailedFragmentArgs.fromBundle(requireArguments())
        binding.TvAuthor.text = args.sourceName
        binding.TvHeading.text = args.title
        binding.TvDescription.text = args.description
    }
}