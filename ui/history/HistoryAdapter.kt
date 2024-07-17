package com.bloodpressure.bloodtracker.bptracker.ui.history

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemHistoryBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BloodDataModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodType
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.extensions.toDateString

class HistoryAdapter :
    Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<BloodDataModel>()

    var onItemClicked: ((bloodDataModel: BloodDataModel) -> Unit)? = null
    var onItemDeleteClicked: ((bloodDataModel: BloodDataModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemHistoryBinding.inflate(inflater, parent, false))
    }

    fun setData(data: List<BloodDataModel>) {
        this.data.clear()
        this.data.addAll(data)

        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindView(data[position] as BloodDataModel)
    }

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        init {
            initEvent()
        }

        private fun initEvent() {
            binding.root.setOnSingleClickListener {
                val bloodDataModel = data[adapterPosition] as BloodDataModel
                if (bloodDataModel.isFileTemp) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.this_is_a_file_sample), Toast.LENGTH_SHORT
                    ).show()
                    return@setOnSingleClickListener
                }
                onItemClicked?.invoke(bloodDataModel)
            }

            binding.btnCancel.setOnSingleClickListener { onItemDeleteClicked?.invoke(data[position] as BloodDataModel) }
        }

        fun bindView(bloodDataModel: BloodDataModel) {
            binding.txtCategory.text = bloodDataModel.type.getName(binding.root.context)
            binding.txtDate.text = bloodDataModel.date.toDateString()
            binding.txtTime.text = bloodDataModel.time
            binding.txtWeekdays.text = bloodDataModel.weekdays
            binding.txtCategory.setTextColor(Color.parseColor(getColor(bloodDataModel.type)))
            binding.indicator.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(getColor(bloodDataModel.type)))

            binding.btnCancel.isVisible = !bloodDataModel.isFileTemp
        }

        private fun getColor(type: BloodType): String {
            return when (type) {
                BloodType.BLOOD_PRESSURE -> "#E73636"
                BloodType.BLOOD_SUGAR -> "#49B474"
                BloodType.HEART_RATE -> "#FE8A00"
                else -> "#496DB4"
            }
        }
    }

}