package com.example.crimeguardian

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crimeguardian.adapter.CrimeNewsAdapter
import com.example.crimeguardian.data.CrimeNews
import com.example.crimeguardian.databinding.ActivityMainBinding
import com.example.crimeguardian.databinding.FragmentIncidentsBinding
import com.example.crimeguardian.databinding.FragmentNewsBinding


class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var crimeNewsAdapter: CrimeNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)

        //RecyclerView
        recyclerView = binding.recyclerViewCrimeNews
        crimeNewsAdapter =
            CrimeNewsAdapter(getDummyCrimeNewsList())  // Replace with your actual list of CrimeNews
        recyclerView.adapter = crimeNewsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }

    private fun getDummyCrimeNewsList(): List<CrimeNews> {
        // Replace this with your actual list of CrimeNews
        return listOf(
            CrimeNews(R.drawable.crime_1, getString(R.string.assault), R.string.text_crime_1),
            CrimeNews(R.drawable.crime_2, getString(R.string.assault), R.string.text_crime_2),
            CrimeNews(R.drawable.crime_3, getString(R.string.burglary), R.string.text_crime_3),
            CrimeNews(R.drawable.crime_4, getString(R.string.theft), R.string.text_crime_4)


        )
    }
}
