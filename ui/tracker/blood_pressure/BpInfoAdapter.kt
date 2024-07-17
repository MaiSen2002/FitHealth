package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemInfoBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BPInfoDataModel
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.bumptech.glide.Glide

class BpInfoAdapter : RecyclerView.Adapter<BpInfoAdapter.ViewHolder>() {
    var data = listOf<BPInfoDataModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemInfoBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = 5

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    class ViewHolder(private val binding: ItemInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(bpInfoDataModel: BPInfoDataModel) {
            binding.txtSystolic.text = bpInfoDataModel.systolic
            binding.txtDiastolic.text = bpInfoDataModel.diastolic
            binding.txtStatus.text = bpInfoDataModel.status
            binding.txtIndicator.text = bpInfoDataModel.textIndicator
            binding.lytStatus.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(bpInfoDataModel.color.toString()))

            Glide.with(binding.root).load(Utils.getSrc(bpInfoDataModel.icon)).into(binding.ivStatus)
        }
    }
}