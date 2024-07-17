package com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentPreviewHeartRateBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.convertDateToString
import com.bloodpressure.bloodtracker.bptracker.extensions.hasNetworkConnection
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.extensions.toDateString
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewHeartRateFragment : BaseFragment<FragmentPreviewHeartRateBinding>() {
    private val viewModel: PreviewHeartRateViewModel by viewModels()
    private lateinit var adapter: HeartRateRecentAdapter

    companion object {
        var isFromTracker = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments == null) {
            return
        }
        viewModel.initArgument(arguments)
    }

    override fun createViewBinding() = FragmentPreviewHeartRateBinding.inflate(layoutInflater)

    override fun initializeView() {
    }

    override fun initializeComponent() {
        adapter = HeartRateRecentAdapter()
        binding.rcBloodSugar.setHasFixedSize(true)
        binding.rcBloodSugar.adapter = adapter
    }

    override fun initializeEvent() {
        binding.btnBack.setOnSingleClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        binding.btnAdd.setOnSingleClickListener {
            viewModel.heartRate = null
            openFragment()
        }

        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            adapter.data = it
        }
    }

    override fun initializeData() {
        viewModel.initData()
        viewModel.getDataRecent()
    }

    override fun bindView() {
        val heartRate = viewModel.heartRate!!
        binding.txtDate.text = heartRate.date.toDateString()
        binding.txtTime.text = heartRate.time
        binding.txtStatus.text =
            "${
                Utils.getStatusHeartRate(
                    requireContext(),
                    heartRate.status
                )
            } ${mContext.getString(R.string.heart_rate)}"
        binding.prgHeartRate.setProgress(heartRate.heartRate * 1.0, 200.0)
        binding.prgHeartRate.progressColor = Color.parseColor(heartRate.color.toString())
        binding.prgHeartRate.dotColor = Color.parseColor(heartRate.color.toString())

        bindViewBarChart()
    }

    private fun bindViewBarChart() {
        val barEntrySys = arrayListOf<BarEntry>()
        barEntrySys.add(BarEntry(0f, viewModel.heartRate!!.heartRate.toFloat()))

        val barDataSetSys = BarDataSet(barEntrySys, "")
        barDataSetSys.color = Color.parseColor("#1E2D50")
        barDataSetSys.setDrawValues(false)

        val barData = BarData(barDataSetSys)
        binding.barChart.data = barData

        barData.barWidth = 0.1f

        binding.barChart.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 200f
        }

        binding.barChart.legend.isEnabled = false

        binding.barChart.xAxis.apply {
            valueFormatter =
                IndexAxisValueFormatter(arrayListOf(viewModel.heartRate!!.date.convertDateToString("dd-MM")))
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
            setDrawGridLines(false)
        }

        binding.barChart.axisRight.isEnabled = false
        binding.barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.barChart.setDrawGridBackground(false)
        binding.barChart.description.isEnabled = false

        binding.barChart.xAxis.axisMinimum = 0.5f
        binding.barChart.invalidate()
    }

    fun openFragment() {
        if (requireActivity() is MainActivity) {
            AddHeartRateFragment().pushToScreen(activity as MainActivity)
        } else {
            AddHeartRateFragment().pushToScreen(activity as HomeActivity)
        }
    }

}