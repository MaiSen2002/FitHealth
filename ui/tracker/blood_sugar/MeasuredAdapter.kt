package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemContentBinding
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemHeaderBinding

class MeasuredAdapter : Adapter<ViewHolder>() {

    var data: List<Pair<String, Int>> = listOf()
    var onItemClicked: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 0) {
            HeaderViewHolder(ItemHeaderBinding.inflate(inflater, parent, false))
        } else {
            ContentViewHolder(ItemContentBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].second
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bindView(data[position].first)
            is ContentViewHolder -> holder.bindView(data[position].first)
        }
    }

    inner class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        ViewHolder(binding.root) {
        fun bindView(header: String) {
            binding.txtHeader.text = header
        }
    }

    inner class ContentViewHolder(private val binding: ItemContentBinding) :
        ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { onItemClicked?.invoke(adapterPosition) }
        }

        fun bindView(header: String) {
            binding.txtContent.text = header
        }
    }
}