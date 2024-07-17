package com.bloodpressure.bloodtracker.bptracker.ui.info

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemInfoDetailBinding
import com.bloodpressure.bloodtracker.bptracker.domain.InfoDetailDataModel

class InformationAdapter :
    Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<InfoDetailDataModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemInfoDetailBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemInfoDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(infoDetailDataModel: InfoDetailDataModel) {
            binding.txtTitle.text = infoDetailDataModel.title
            binding.txtContent.text = infoDetailDataModel.content
        }
    }

}