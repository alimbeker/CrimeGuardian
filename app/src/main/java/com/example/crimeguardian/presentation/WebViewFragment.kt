package com.example.crimeguardian.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.crimeguardian.databinding.FragmentWebBinding

class WebViewFragment : Fragment() {
    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

    private val args: WebViewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = args.url
        loadUrl(url)
    }

    private fun loadUrl(url: String) {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
