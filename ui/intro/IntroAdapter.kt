package com.bloodpressure.bloodtracker.bptracker.ui.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bloodpressure.bloodtracker.bptracker.databinding.LayoutIntro1Binding

class IntroAdapter(private val data: ArrayList<Triple<Int, String, String>>) :
    Adapter<IntroAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(LayoutIntro1Binding.inflate(inflater, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(data[position])
    }

    inner class ViewHolder(private val binding: LayoutIntro1Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Triple<Int, String, String>) {
            binding.im1.setImageResource(item.first)
            binding.t1.text = item.second
            binding.tv.text = item.third
        }
    }

}