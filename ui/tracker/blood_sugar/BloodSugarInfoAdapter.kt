package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemBloodSugarInfoBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugar
import com.bloodpressure.bloodtracker.bptracker.extensions.getColor
import com.bloodpressure.bloodtracker.bptracker.extensions.toDateString
import com.bloodpressure.bloodtracker.bptracker.util.Utils

class BloodSugarInfoAdapter : Adapter<BloodSugarInfoAdapter.ViewHolder>() {

    var data = listOf<BloodSugar>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BloodSugarInfoAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemBloodSugarInfoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    override fun getItemCount() = data.size


    inner class ViewHolder(private val binding: ItemBloodSugarInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(bloodSugar: BloodSugar) {
            binding.txtDate.text = bloodSugar.date.toDateString()
            binding.txtTime.text = bloodSugar.time
            binding.txtMeasured.text =
                Utils.getNameMeasured(binding.root.context, bloodSugar.measured)
            binding.txtNote.text = bloodSugar.note
            binding.txtBloodSugar.text = bloodSugar.sugarConcentration.toString()
            binding.txtBloodSugar.setTextColor(Color.parseColor(getColor(bloodSugar.sugarConcentration)))
        }
    }
}