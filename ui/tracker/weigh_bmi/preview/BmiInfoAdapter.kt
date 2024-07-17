package com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.preview

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemBmiInfoBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BmiData

class BmiInfoAdapter : Adapter<BmiInfoAdapter.ViewHolder>() {

    var data = listOf<BmiData>()
    var currentStatus = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBmiInfoBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = 8

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemBmiInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(bmiData: BmiData) {
            val color = if (bmiData.status == currentStatus) {
                getColor(bmiData.bmi)
            } else "#1E2D50"

            binding.txtStatus.setTextColor(Color.parseColor(color))
            binding.txtRange.setTextColor(Color.parseColor(color))
            binding.ivSelected.imageTintList = ColorStateList.valueOf(Color.parseColor(color))

            binding.ivSelected.visibility =
                if (bmiData.status == currentStatus) View.VISIBLE else View.INVISIBLE
            binding.txtStatus.text = bmiData.status
            binding.txtRange.text = bmiData.description
        }

        private fun getColor(bmi: Double): String {
            return when (bmi) {
                in 0.0..18.4 -> "#52AAEE"
                in 18.5..24.9 -> "#00C57E"
                else -> "#E34957"
            }
        }
    }

}