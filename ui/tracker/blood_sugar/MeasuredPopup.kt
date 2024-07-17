package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.bloodpressure.bloodtracker.bptracker.databinding.PopupMeasuredBinding
import com.bloodpressure.bloodtracker.bptracker.util.Utils

class MeasuredPopup(private val context: Context, private val anchorWidth: Int) : PopupWindow() {
    private val binding: PopupMeasuredBinding
    private lateinit var adapter: MeasuredAdapter
    private var onItemClicked: ((position: Int) -> Unit)? = null

    companion object {
        fun show(context: Context, anchor: View, onItemClicked: (position: Int) -> Unit) {
            val popup = MeasuredPopup(context, anchor.width)
            popup.onItemClicked = onItemClicked
            popup.showAsDropDown(anchor)
        }
    }

    init {
        val inflater = LayoutInflater.from(context)
        binding = PopupMeasuredBinding.inflate(inflater, null, false)
        contentView = binding.root

        width = anchorWidth
        height = WindowManager.LayoutParams.WRAP_CONTENT

        isOutsideTouchable = true
        elevation = 16f

        initComponent()
        initEvent()
    }

    private fun initComponent() {
        adapter = MeasuredAdapter()
        binding.rcMeasured.setHasFixedSize(true)
        adapter.data = Utils.getMeasured(context)
        binding.rcMeasured.adapter = adapter
    }

    private fun initEvent() {
        adapter.onItemClicked = {
            onItemClicked?.invoke(it)
            dismiss()
        }
    }
}