package com.example.crimeguardian.presentation.userprofile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.crimeguardian.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {
  private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonBack()
    }


    private fun buttonBack(){
        binding.btnToBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
            }
