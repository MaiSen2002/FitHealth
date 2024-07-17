package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.preview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemBpRecentlyBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure

class BloodPressureAdapter : Adapter<BloodPressureAdapter.ViewHolder>() {

    var data = listOf<BloodPressure>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBpRecentlyBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemBpRecentlyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(bloodPressure: BloodPressure) {
            binding.txtSys.text = bloodPressure.systolic.toString()
            binding.txtDia.text = bloodPressure.diastolic.toString()
        }
    }
}