package com.example.crimeguardian.presentation.news.fragment.detailed.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.crimeguardian.R
import com.example.crimeguardian.databinding.FragmentDetailedBinding


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

        Glide.with(requireContext())
            .load(args.urlToImage)
            .into(binding.IvBackground)

        binding.IvBackButton.setOnClickListener {
            Log.d("DetailedFragment", "Back button clicked")
            findNavController().navigateUp()
        }

    }
}