package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemBloodSugarBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugarInfo

class BloodSugarAdapter : Adapter<BloodSugarAdapter.ViewHolder>() {

    var data = listOf<BloodSugarInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBloodSugarBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = 4

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemBloodSugarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(bloodSugarInfo: BloodSugarInfo) {
            binding.txtStatus.text = bloodSugarInfo.status
            binding.ivStatus.setBackgroundColor(Color.parseColor(bloodSugarInfo.color.toString()))
            binding.txtBloodSugar.text = bloodSugarInfo.range

            binding.txtStatus.setTextColor(Color.parseColor(bloodSugarInfo.color.toString()))
        }
    }
}