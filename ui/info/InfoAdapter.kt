package com.bloodpressure.bloodtracker.bptracker.ui.info

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemInfoFeaturesBinding
import com.bloodpressure.bloodtracker.bptracker.domain.InfoData
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bumptech.glide.Glide

class InfoAdapter : Adapter<InfoAdapter.ViewHolder>() {
    var data = listOf<InfoData>()
    var onItemClicked: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemInfoFeaturesBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = 4

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemInfoFeaturesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnSingleClickListener { onItemClicked?.invoke(adapterPosition) }
        }

        fun bindView(infoData: InfoData) {
            binding.txtInfo.text = infoData.text
            Glide.with(binding.root).load(infoData.src).into(binding.ivInfo)
            binding.ivInfo.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(infoData.bg))
        }
    }
}