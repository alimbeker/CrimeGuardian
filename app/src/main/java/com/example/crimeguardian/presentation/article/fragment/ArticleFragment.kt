package com.example.crimeguardian.presentation.article.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.crimeguardian.R
import com.example.crimeguardian.databinding.FragmentArticleBinding


class ArticleFragment : Fragment() {

        private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webViewPage()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewPage(){
        binding.webView1.settings.javaScriptEnabled = true
        binding.webView1.webViewClient = WebViewClient()
        binding.webView1.loadUrl("https://www.numbeo.com/crime/in/Almaty")

    }





    }
