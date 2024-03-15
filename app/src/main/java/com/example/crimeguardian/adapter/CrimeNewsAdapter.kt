package com.example.crimeguardian.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crimeguardian.R
import com.example.crimeguardian.api.Page


class CrimeNewsAdapter(private var crimeNewsList: List<Page?>) :
    RecyclerView.Adapter<CrimeNewsAdapter.CrimeNewsViewHolder>() {

    fun updateData(newCrimeNewsList: List<Page?>) {
        crimeNewsList = newCrimeNewsList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeNewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_crime_news, parent, false)
        return CrimeNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CrimeNewsViewHolder, position: Int) {
        val crimeNews = crimeNewsList[position]

        // Update with appropriate method based on the type you decide for imageUrl
        crimeNews?.let {crimeNews ->
            holder.textCrimeDescription.text = crimeNews.title
            holder.textCrimeType.setText(R.string.text_crime_1)
            holder.imageViewCrime.setImageResource(R.drawable.crime_3)
        }

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
