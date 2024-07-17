package com.bloodpressure.bloodtracker.bptracker.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemSettingsBinding
import com.bloodpressure.bloodtracker.bptracker.domain.SettingDataModel
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bumptech.glide.Glide

class SettingsAdapter : Adapter<SettingsAdapter.ViewHolder>() {

    var data = listOf<SettingDataModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClicked: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemSettingsBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnSingleClickListener { onItemClicked?.invoke(adapterPosition) }
        }

        fun bindView(item: SettingDataModel) {
            binding.txtSetting.text = item.featureName
            Glide.with(binding.root).load(item.icon).into(binding.ivSetting)
        }
    }

}