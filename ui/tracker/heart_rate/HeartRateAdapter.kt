package com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemBloodSugarBinding
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.util.Utils

class HeartRateAdapter : Adapter<HeartRateAdapter.ViewHolder>() {

    var data = listOf<HeartRate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBloodSugarBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemBloodSugarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(heartRate: HeartRate) {
            binding.txtStatus.text =
                Utils.getStatusHeartRate(binding.root.context, heartRate.status)
            binding.ivStatus.setBackgroundColor(Color.parseColor(heartRate.color.toString()))
            binding.txtStatus.setTextColor(Color.parseColor(heartRate.color.toString()))
            binding.txtBloodSugar.text = heartRate.range
        }
    }
}