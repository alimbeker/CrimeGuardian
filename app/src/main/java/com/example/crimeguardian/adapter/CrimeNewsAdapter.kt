package com.example.crimeguardian.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crimeguardian.data.Article
import com.example.crimeguardian.databinding.ItemCrimeNewsBinding

class CrimeNewsAdapter :
    ListAdapter<Article, CrimeNewsAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =
            ItemCrimeNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }

    inner class ArticleViewHolder(private val binding: ItemCrimeNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                typeOfCrime.text = article.title
                description.text = article.description

                Glide.with(binding.root.context)
                    .load(article.urlToImage)
                    .into(crimeNewsImage)
            }
        }
    }
}

class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
