package com.bloodpressure.bloodtracker.bptracker.ui.history

import android.graphics.Color
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentStatisticsBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.convertDateToString
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {
    private val viewModel: StatisticsViewModel by viewModels()
    override fun createViewBinding() = FragmentStatisticsBinding.inflate(layoutInflater)
    override fun initializeComponent() {

    }

    override fun initializeEvent() {
        binding.btnBack.setOnSingleClickListener { requireActivity().supportFragmentManager.popBackStack() }

        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            bindViewBloodPressure()
            bindViewBloodSugar()
            bindViewHeartRate()
            bindViewBmi()
        }


    }

    override fun initializeData() {
        viewModel.initData()
    }

    override fun bindView() {

    }

    private fun bindViewBloodPressure() {
        val barEntrySys = arrayListOf<BarEntry>()

        val bloodPressures = viewModel.data.bloodPressures

        bloodPressures.forEachIndexed { index, it ->
            barEntrySys.add(BarEntry(index * 1F, it.systolic * 1.0f))
        }

        val barEntryDia = arrayListOf<BarEntry>()
        bloodPressures.forEachIndexed { index, it ->
            barEntryDia.add(BarEntry(index * 1F, it.diastolic * 1.0f))
        }

        val barDataSetSys = BarDataSet(barEntrySys, mContext.getString(R.string.systolic))
        barDataSetSys.color = Color.parseColor("#B55559")
        barDataSetSys.setDrawValues(false)

        val barDataSetDia = BarDataSet(barEntryDia, mContext.getString(R.string.diastolic))
        barDataSetDia.color = Color.parseColor("#1E2D50")
        barDataSetDia.setDrawValues(false)

        val barData = BarData(barDataSetSys, barDataSetDia).apply { barWidth = 0.25f }
        binding.brtBloodPressure.data = barData

        binding.brtBloodPressure.legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            orientation = Legend.LegendOrientation.HORIZONTAL
            textSize = 10f
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            form = Legend.LegendForm.LINE
            formSize = 20f
        }

        //  set range trục y
        binding.brtBloodPressure.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 300f
        }

        val type = bloodPressures.map { it.date.convertDateToString("dd-MM") }
        //  cấu hình trục x
        binding.brtBloodPressure.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(type)
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
        }

        val barWidth = binding.brtBloodPressure.barData.barWidth
        val barSpace = 0.08f
        val groupSpace = 1 - ((barWidth + barSpace) * 2)
        val groupCount = type.size

        binding.brtBloodPressure.setExtraOffsets(5f, 5f, 5f, 15f)

        //  set range trục x
        binding.brtBloodPressure.xAxis.apply {
            setCenterAxisLabels(true)
            axisMinimum = 0f
            //  có thể x3 để kích thước các cột luôn bằng nhau

            axisMaximum =
                0 + binding.brtBloodPressure.barData.getGroupWidth(
                    groupSpace,
                    barSpace
                ) * groupCount
            setDrawGridLines(false)
        }
        //  tắt cột y bên phải

        binding.brtBloodPressure.apply {
            setScaleEnabled(false)

            extraTopOffset = 16f
            setVisibleXRange(4f, 4f)
            animateY(3000)
            axisRight.isEnabled = false
            description.isEnabled = false
            groupBars(0f, groupSpace, barSpace)
            invalidate()
        }
    }

    private fun bindViewBloodSugar() {
        val barEntries = arrayListOf<BarEntry>()
        val bloodSugars = viewModel.data.bloodSugars
        bloodSugars.forEachIndexed { index, it ->
            barEntries.add(BarEntry(index * 1F, it.sugarConcentration.toFloat()))
        }

        val barDataSetSys = BarDataSet(barEntries, "")
        barDataSetSys.color = Color.parseColor("#1E2D50")
        barDataSetSys.setDrawValues(false)

        binding.brtBloodSugar.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 15f
        }

        val type = bloodSugars.map { it.date.convertDateToString("dd-MM") }

        binding.brtBloodSugar.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(type)
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
        }

        val barData = BarData(barDataSetSys).apply { barWidth = 0.3f }
        binding.brtBloodSugar.data = barData

        binding.brtBloodSugar.axisRight.isEnabled = false
        binding.brtBloodSugar.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.brtBloodSugar.xAxis.setDrawGridLines(false)
        binding.brtBloodSugar.setDrawGridBackground(false)
        binding.brtBloodSugar.animateY(3000)
        binding.brtBloodSugar.description.isEnabled = false
        binding.brtBloodSugar.legend.isEnabled = false
        binding.brtBloodSugar.setVisibleXRange(6F, 6F)
        binding.brtBloodSugar.invalidate()
    }

    private fun bindViewHeartRate() {
        val heartRates = viewModel.data.heartRates
        val barEntrySys = arrayListOf<BarEntry>()
        heartRates.forEachIndexed { index, it ->
            barEntrySys.add(BarEntry(index * 1F, it.heartRate.toFloat()))
        }

        val barDataSetSys = BarDataSet(barEntrySys, "")
        barDataSetSys.color = Color.parseColor("#1E2D50")
        barDataSetSys.setDrawValues(false)

        val barData = BarData(barDataSetSys)
        barData.barWidth = 0.3F
        binding.brtHeartRate.data = barData

        binding.brtHeartRate.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 140f
        }

        val type = heartRates.map { it.date.convertDateToString("dd-MM") }

        binding.brtHeartRate.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(type)
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
        }

        binding.brtHeartRate.axisRight.isEnabled = false
        binding.brtHeartRate.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.brtHeartRate.xAxis.setDrawGridLines(false)
        binding.brtHeartRate.setDrawGridBackground(false)
        binding.brtHeartRate.description.isEnabled = false
        binding.brtHeartRate.legend.isEnabled = false
        binding.brtHeartRate.setVisibleXRange(6F, 6F)
        binding.brtHeartRate.invalidate()
    }

    private fun bindViewBmi() {
        val barEntrySys = arrayListOf<BarEntry>()
        val listBmi = viewModel.data.listBmi
        listBmi.forEachIndexed { index, it ->
            barEntrySys.add(BarEntry(index * 1F, it.bmi.toFloat()))
        }

        val barDataSetSys = BarDataSet(barEntrySys, mContext.getString(R.string.bmi))
        barDataSetSys.color = Color.parseColor("#1E2D50")
        barDataSetSys.setDrawValues(false)

        val barData = BarData(barDataSetSys).apply { barWidth = 0.3f }
        binding.brtBmi.data = barData

        binding.brtBmi.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 45f
        }

        val type = listBmi.map { it.date.convertDateToString("dd-MM") }

        binding.brtBmi.xAxis.apply {
            valueFormatter = IndexAxisValueFormatter(type)
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f
        }

        binding.brtBmi.axisRight.isEnabled = false
        binding.brtBmi.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.brtBmi.xAxis.setDrawGridLines(false)
        binding.brtBmi.setDrawGridBackground(false)
        binding.brtBmi.setVisibleXRange(6F, 6F)
        binding.brtBmi.description.isEnabled = false
        binding.brtBmi.legend.isEnabled = false
        binding.brtBmi.setVisibleXRangeMaximum(6F)
        binding.brtBmi.invalidate()
    }

}