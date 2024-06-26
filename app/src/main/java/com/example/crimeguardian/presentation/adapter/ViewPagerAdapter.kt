package com.example.crimeguardian.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.crimeguardian.R
import com.example.crimeguardian.presentation.model.model.news.Article
import com.example.crimeguardian.presentation.news.fragment.OnNewsClickListener

class ViewPagerAdapter(private val newsList: List<Article>, private val onNewsClickListener: OnNewsClickListener) : RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {
    private lateinit var view: View

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBackground = view.findViewById<ImageView>(R.id.ivBackground)!!
        val tvSource = view.findViewById<TextView>(R.id.tvSource)!!
        val tvHeading = view.findViewById<TextView>(R.id.tvHeading)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.top_headlines, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvSource.text = newsList[position].sourceName
        holder.tvHeading.text = newsList[position].title
        Glide.with(view).load(newsList[position].urlToImage).centerCrop().placeholder(R.drawable.no_image_avaliable).into(holder.ivBackground)

        holder.itemView.setOnClickListener {
            onNewsClickListener.onNewsItemClickListener(newsList[position])
        }
    }
}