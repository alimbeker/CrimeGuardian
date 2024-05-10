package com.example.crimeguardian.presentation.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.crimeguardian.databinding.FragmentMainBinding


class MainFragment : Fragment() {
        private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        issuePage()
        newsPage()
        extraCallPage()
    }


    private fun issuePage(){
        binding.apply {
            imageMap.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToIssuesFragment()
                findNavController().navigate(action)
            }

        }
    }

    private fun newsPage(){
        binding.apply {
            imageNews.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToNewsFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun extraCallPage(){
        binding.apply {
            sosCall.setOnClickListener {
                val action = MainFragmentDirections.actionMainFragmentToProfileFragment()
                findNavController().navigate(action)
            }
        }
    }


}