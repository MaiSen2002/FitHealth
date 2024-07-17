package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.ItemFeatureBinding
import com.bloodpressure.bloodtracker.bptracker.helper.PreferenceHelper
import com.bumptech.glide.Glide

class FeatureAdapter(val preferenceHelper: PreferenceHelper) : Adapter<RecyclerView.ViewHolder>() {

    var data = arrayListOf<FeaturesModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onItemClicked: ((position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemFeatureBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bindView(data[position])
    }

    inner class ViewHolder(private val binding: ItemFeatureBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            initializeEvent()
        }

        private fun initializeEvent() {
            binding.root.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bindView(item: FeaturesModel) {
            binding.txtName.text = item.name
            Glide.with(binding.root).load(item.image).into(binding.ivFeature)
        }

    }

}