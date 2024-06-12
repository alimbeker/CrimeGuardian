package com.example.crimeguardian.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crimeguardian.databinding.ItemDistrictBinding
import com.example.crimeguardian.presentation.model.model.main.District

class DistrictAdapter(private var districts: MutableList<District>) :
    RecyclerView.Adapter<DistrictAdapter.DistrictViewHolder>() {

    inner class DistrictViewHolder(private val binding: ItemDistrictBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(district: District) {
            binding.apply {
                image.setImageResource(district.imageResId)
                districtName.setText(district.name)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistrictViewHolder {
        val binding =
            ItemDistrictBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DistrictViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int) {
        holder.bind(districts[position])
    }

    override fun getItemCount(): Int {
        return districts.size
    }

    fun updateDistricts(newDistricts: List<District>) {
        districts.clear()
        districts.addAll(newDistricts)
        notifyDataSetChanged()
    }
}
