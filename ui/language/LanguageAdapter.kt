package com.bloodpressure.bloodtracker.bptracker.ui.language

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemLanguageBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import javax.inject.Inject

class LanguageAdapter @Inject constructor() :
    Adapter<LanguageAdapter.LanguageViewHolder>() {

    var data = listOf<LanguageModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val binding =
            ItemLanguageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount() = data.size


    inner class LanguageViewHolder(val binding: ItemLanguageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnSingleClickListener {
                resetChecked()
                data[adapterPosition].active = true
                notifyDataSetChanged()
            }

            binding.btnSelected.setOnSingleClickListener {
                resetChecked()
                data[adapterPosition].active = true
                notifyDataSetChanged()
            }
        }

        fun bindData(languageModel: LanguageModel) {
            binding.txtName.text = languageModel.name

            if (languageModel.active) {
                binding.btnSelected.buttonTintList =
                    ColorStateList.valueOf(Color.parseColor("#5D5FEF"))
                binding.btnSelected.isChecked = true
            } else {
                binding.btnSelected.buttonTintList =
                    ColorStateList.valueOf(Color.parseColor("#8D9DA5"))
                binding.btnSelected.isChecked = false
            }

            binding.icLang.setImageResource(languageModel.drawable)
        }

        private fun resetChecked() {
            data.forEach { it.active = false }
        }
    }
}