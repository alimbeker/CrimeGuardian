package com.example.crimeguardian.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crimeguardian.databinding.ItemCrimeNewsBinding
import com.example.crimeguardian.presentation.model.model.news.Article
import com.example.crimeguardian.presentation.news.fragment.OnNewsClickListener

class CrimeNewsAdapter(private val maxItems: Int, private val onNewsClickListener: OnNewsClickListener) :
    ListAdapter<Article, CrimeNewsAdapter.ArticleViewHolder>(ArticleDiffCallback()) {
    private var articles: List<Article> = emptyList()

    fun submitArticles(articles: List<Article>) {
        this.articles = articles.take(maxItems)
        submitList(this.articles)
    }

    override fun getItemCount(): Int {
        return if (articles.size > maxItems) maxItems else articles.size
    }

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
                source.text = article.sourceName
                description.text = article.title

                Glide.with(binding.root.context)
                    .load(article.urlToImage)
                    .into(crimeNewsImage)
            }

            itemView.setOnClickListener {
                onNewsClickListener.onNewsItemClickListener(article)
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
