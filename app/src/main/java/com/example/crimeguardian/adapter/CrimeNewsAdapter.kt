package com.example.crimeguardian.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crimeguardian.R
import com.example.crimeguardian.data.CrimeNews


class CrimeNewsAdapter(private val crimeNewsList: List<CrimeNews>) :
    RecyclerView.Adapter<CrimeNewsAdapter.CrimeNewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeNewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_crime_news, parent, false)
        return CrimeNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrimeNewsViewHolder, position: Int) {
        val crimeNews = crimeNewsList[position]

        holder.imageViewCrime.setImageResource(crimeNews.imageResId)  // Update with appropriate method based on the type you decide for imageUrl
        holder.textCrimeType.text = crimeNews.crimeType
        holder.textCrimeDescription.setText(crimeNews.crimeDescriptionResId)
    }

    override fun getItemCount(): Int {
        return crimeNewsList.size
    }

    class CrimeNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCrime: ImageView = itemView.findViewById(R.id.crime_news_image)
        val textCrimeType: TextView = itemView.findViewById(R.id.type_of_crime)
        val textCrimeDescription: TextView = itemView.findViewById(R.id.description)
    }
}
