package com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemHeartRateBinding
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.extensions.toDateString
import com.bloodpressure.bloodtracker.bptracker.util.Utils

class HeartRateRecentAdapter : Adapter<HeartRateRecentAdapter.ViewHolder>() {

    var data = listOf<HeartRate>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeartRateRecentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemHeartRateBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: HeartRateRecentAdapter.ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemHeartRateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("Range")
        fun bindView(heartRate: HeartRate) {
            binding.txtDate.text = heartRate.date.toDateString()
            binding.txtTime.text = heartRate.time
            binding.txtStatus.text =
                "${
                    Utils.getStatusHeartRate(
                        binding.root.context,
                        heartRate.status
                    )
                } ${binding.root.context.getString(R.string.heart_rate)}"
            binding.prgHeartRate.progressColor = Color.parseColor(heartRate.color)
            binding.prgHeartRate.setProgress(heartRate.heartRate * 1.0, 200.0)
            binding.prgHeartRate.dotColor = Color.parseColor(heartRate.color)
        }
    }

}