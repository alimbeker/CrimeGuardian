package com.example.crimeguardian.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crimeguardian.R
import com.example.crimeguardian.databinding.TopHeadlinesBinding
import com.example.crimeguardian.presentation.model.model.news.Article


class ViewPagerAdapter(private val newsList: List<Article>) : RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TopHeadlinesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    inner class MyViewHolder(private val binding: TopHeadlinesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvSource.text = article.sourceName
            binding.tvHeading.text = article.title
            Glide.with(binding.root)
                .load(article.urlToImage)
                .centerCrop()
                .placeholder(R.drawable.no_image_avaliable)
                .into(binding.ivBackground)
        }
    }
}
