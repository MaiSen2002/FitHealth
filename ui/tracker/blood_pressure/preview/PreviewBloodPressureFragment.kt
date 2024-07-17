package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.preview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentPreviewBloodPressureBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure
import com.bloodpressure.bloodtracker.bptracker.extensions.convertDateToString
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.add_bp.AddBloodPressureFragment
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.bloodpressure.bloodtracker.bptracker.util.Utils.Companion.getStatusBP
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewBloodPressureFragment : BaseFragment<FragmentPreviewBloodPressureBinding>() {
    private val viewModel: PreviewBloodPressureViewModel by viewModels()
    private lateinit var adapter: BloodPressureAdapter

    companion object {
        var isFromTracker = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments == null) {
            return
        }
        viewModel.initArguments(arguments)
    }

    override fun createViewBinding() = FragmentPreviewBloodPressureBinding.inflate(layoutInflater)

    override fun initializeView() {
    }

    override fun initializeComponent() {
        adapter = BloodPressureAdapter()

        binding.rcBloodPressure.setHasFixedSize(false)
        binding.rcBloodPressure.adapter = adapter

    }

    override fun initializeEvent() {
        binding.btnBack.setOnSingleClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
        binding.btnAdd.setOnSingleClickListener {
            viewModel.bloodPressure = null
            startAddBloodPressure()
        }

        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            adapter.data = it
        }
    }

    private fun startAddBloodPressure() {
        if (activity is MainActivity) {
            AddBloodPressureFragment().pushToScreen(requireActivity() as MainActivity)
        } else {
            AddBloodPressureFragment().pushToScreen(requireActivity() as HomeActivity)
        }
    }

    override fun initializeData() {
        if (viewModel.bloodPressure == null) {
            viewModel.initData(mContext)
        }
        viewModel.getRecentBloodPressure()
    }

    override fun bindView() {
        val bloodPressure = viewModel.bloodPressure!!
        binding.txtStatus.text = getStatusBP(requireContext(), bloodPressure.status)
        binding.ivStatus.setImageResource(Utils.getSrc(bloodPressure.icon))
        binding.prgSystolic.setProgress(bloodPressure.systolic * 1.0, 300.0)
        binding.prgDiastolic.setProgress(bloodPressure.diastolic * 1.0, 300.0)

        bindViewBarChart(bloodPressure)
    }

    private fun bindViewBarChart(bloodPressure: BloodPressure) {
        val barEntrySys = arrayListOf<BarEntry>()
        barEntrySys.add(BarEntry(0f, bloodPressure.systolic * 1.0f))

        val barEntryDia = arrayListOf<BarEntry>()
        barEntryDia.add(BarEntry(0f, bloodPressure.diastolic * 1.0f))

        val barDataSetSys = BarDataSet(barEntrySys, mContext.getString(R.string.systolic))
        barDataSetSys.color = Color.parseColor("#B55559")
        barDataSetSys.setDrawValues(false)

        val barDataSetDia = BarDataSet(barEntryDia, mContext.getString(R.string.diastolic))
        barDataSetDia.color = Color.parseColor("#1E2D50")
        barDataSetDia.setDrawValues(false)

        binding.barChart.data = BarData(barDataSetSys, barDataSetDia).apply { barWidth = 0.1f }

        binding.barChart.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            orientation = Legend.LegendOrientation.HORIZONTAL
            textSize = 10f
            form = Legend.LegendForm.LINE
            formSize = 20f
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        }

        binding.barChart.setExtraOffsets(5f, 5f, 5f, 15f)

        binding.barChart.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 300f
        }

        binding.barChart.xAxis.apply {
            valueFormatter =
                IndexAxisValueFormatter(arrayListOf(bloodPressure.date.convertDateToString("dd-MM")))
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
        }

        val barWidth = binding.barChart.barData.barWidth

        //  khoảng cách giữa group
        val barSpace = 0.08f // x2 DataSet

        //  khoảng cách giữa các cột
        val groupSpace = 1 - ((barWidth + barSpace) * 2)
        //  tổng group

        //  set range trục x
        binding.barChart.xAxis.apply {
            setCenterAxisLabels(true)
            axisMinimum = 0f
            //  có thể x3 để kích thước các cột luôn bằng nhau

            axisMaximum =
                0 + binding.barChart.barData.getGroupWidth(groupSpace, barSpace) * 1
            setDrawGridLines(false)
        }

        binding.barChart.apply {
            setScaleEnabled(false)
            extraTopOffset = 16f
            setVisibleXRangeMaximum(4f)
            animateY(3000)
            axisRight.isEnabled = false
            description.isEnabled = false
            groupBars(0f, groupSpace, barSpace)
            invalidate()
        }
    }

}