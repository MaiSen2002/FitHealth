package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.add_bp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemStatusBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BPInfoDataModel

class StatusAdapter : Adapter<StatusAdapter.ViewHolder>() {
    var data = listOf<BPInfoDataModel>()
    var currentId = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemStatusBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: BPInfoDataModel) {
            binding.ivStatus.setBackgroundColor(Color.parseColor(item.color))

            binding.ivSelected.isVisible = item.id == currentId
        }

    }

}