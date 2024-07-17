package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.preview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentPreviewBloodSugarBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.convertDateToString
import com.bloodpressure.bloodtracker.bptracker.extensions.getColor
import com.bloodpressure.bloodtracker.bptracker.extensions.hasNetworkConnection
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.extensions.toDateString
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.BloodSugarInfoAdapter
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.add.AddBloodSugarFragment
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewBloodSugarFragment : BaseFragment<FragmentPreviewBloodSugarBinding>() {
    private val viewModel: PreviewBloodSugarViewModel by viewModels()
    private lateinit var adapter: BloodSugarInfoAdapter
    override fun createViewBinding() = FragmentPreviewBloodSugarBinding.inflate(layoutInflater)

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

    override fun initializeView() {
    }

    override fun initializeComponent() {
        adapter = BloodSugarInfoAdapter()
        binding.rcBloodSugar.setHasFixedSize(false)
        binding.rcBloodSugar.adapter = adapter
    }

    override fun initializeEvent() {
        binding.btnBack.setOnSingleClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        binding.btnAdd.setOnSingleClickListener {
            viewModel.bloodSugar = null
            toFragment()
        }

        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            adapter.data = it
        }
    }

    override fun initializeData() {
        viewModel.initData()
        viewModel.getData()
    }

    override fun bindView() {
        val bloodSugar = viewModel.bloodSugar!!
        binding.txtDate.text = bloodSugar.date.toDateString()
        binding.txtTime.text = bloodSugar.time
        binding.txtMeasured.text = Utils.getNameMeasured(requireContext(), bloodSugar.measured)
        binding.txtNote.text = bloodSugar.note
        binding.txtBloodSugar.text = bloodSugar.sugarConcentration.toString()
        binding.txtBloodSugar.setTextColor(Color.parseColor(getColor(bloodSugar.sugarConcentration)))

        bindViewBarChart()
    }

    private fun bindViewBarChart() {
        val barEntrySys = arrayListOf<BarEntry>()
        barEntrySys.add(BarEntry(0f, viewModel.bloodSugar!!.sugarConcentration.toFloat()))

        val barDataSetSys = BarDataSet(barEntrySys, "")
        barDataSetSys.color = Color.parseColor("#1E2D50")
        barDataSetSys.setDrawValues(false)

        val barData = BarData(barDataSetSys)
        binding.barChart.data = barData

        barData.barWidth = 0.1f

        binding.barChart.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 15f
        }

        binding.barChart.legend.isEnabled = false

        binding.barChart.xAxis.apply {
            valueFormatter =
                IndexAxisValueFormatter(
                    arrayListOf(
                        viewModel.bloodSugar!!.date.convertDateToString(
                            "dd-MM"
                        )
                    )
                )
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

    private fun toFragment() {
        if (requireActivity() is MainActivity) {
            AddBloodSugarFragment().pushToScreen(requireActivity() as MainActivity)
        } else {
            AddBloodSugarFragment().pushToScreen(requireActivity() as HomeActivity)
        }
    }

}