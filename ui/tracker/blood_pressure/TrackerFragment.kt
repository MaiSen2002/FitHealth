package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentTrackerBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.gone
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.extensions.visible
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.bloodpressure.bloodtracker.bptracker.util.Utils.Companion.getStatusBP
import com.bloodpressure.bloodtracker.bptracker.util.Utils.Companion.getStatusBmi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackerFragment : BaseFragment<FragmentTrackerBinding>() {
    private var isCalculated = false
    private val trackerViewModel: TrackerViewModel by viewModels()
    override fun createViewBinding() = FragmentTrackerBinding.inflate(layoutInflater)
    override fun initializeComponent() {
        trackerViewModel.initData()
        isCalculated = preferenceHelper.getBoolean(Utils.IS_CALCULATED) ?: false
    }


    override fun initializeEvent() {
        binding.apply {
            startPreview(bloodPressure.btnAddRecord)
            startPreview(bloodSugar.btnAddRecord)
            startPreview(heartRate.btnAddRecord)
            startPreview(weightBmi.btnAddRecord)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initializeData() {
        initDataBp()
        initDataBs()
        initDataHr()
        initDataBmi()
    }

    @SuppressLint("SetTextI18n")
    private fun initDataBp() {
        val listBp = trackerViewModel.bloodPressures

        binding.bloodPressure.tvStatus.isSelected = true
        binding.bloodSugar.tvStatus.isSelected = true
        binding.heartRate.tvStatus.isSelected = true
        binding.weightBmi.tvStatus.isSelected = true

        if (listBp.isEmpty()) {
            binding.bloodPressure.apply {
                tvNoDataMinBp.visible()
                tvNoDataMaxBp.visible()
                llMaxBp.gone()
                llMinBp.gone()
            }
            return
        } else {
            binding.bloodPressure.apply {
                tvNoDataMinBp.gone()
                tvNoDataMaxBp.gone()
                llMaxBp.visible()
                llMinBp.visible()
            }
        }
        val lastBp = listBp.last()
        if (lastBp.status.length > 8) {
            binding.bloodPressure.tvStatus.text =
                getStatusBP(requireContext(), lastBp.status)
        } else {
            binding.bloodPressure.tvStatus.text = getStatusBP(requireContext(), lastBp.status)
        }
        val minSysBp = listBp.minByOrNull { it.systolic }
        val maxSysBp = listBp.maxByOrNull { it.systolic }
        val minDiaBp = listBp.minByOrNull { it.diastolic }
        val maxDiaBp = listBp.maxByOrNull { it.diastolic }
        binding.bloodPressure.apply {
            tvMinSysBp.text = minSysBp?.systolic.toString()
            tvMinDiaBp.text = minDiaBp?.diastolic.toString()
            tvMaxSysBp.text = maxSysBp?.systolic.toString()
            tvMaxDiaBp.text = maxDiaBp?.diastolic.toString()
        }
    }

    private fun initDataBs() {
        val listBs = trackerViewModel.bloodSugars
        if (listBs.isEmpty()) {
            binding.bloodSugar.apply {
                tvNoDataMinBs.visible()
                tvNoDataMaxBs.visible()
                tvMinBs.gone()
                tvMaxBs.gone()
            }
            return
        } else {
            binding.bloodSugar.apply {
                tvNoDataMinBs.gone()
                tvNoDataMaxBs.gone()
                tvMinBs.visible()
                tvMaxBs.visible()
            }
        }
        val lastBs = listBs.last()
        binding.bloodSugar.tvStatus.text =
            Utils.getStatusFromSugarConcentration(requireContext(), lastBs.sugarConcentration)
        val minBs = listBs.minByOrNull { (it.sugarConcentration) }
        val maxBs = listBs.maxByOrNull { (it.sugarConcentration) }
        binding.bloodSugar.tvMinBs.text = minBs?.sugarConcentration.toString()
        binding.bloodSugar.tvMaxBs.text = maxBs?.sugarConcentration.toString()
    }

    private fun initDataHr() {
        val listHr = trackerViewModel.heartRates
        if (listHr.isEmpty()) {
            binding.heartRate.apply {
                tvNoDataMinHr.visible()
                tvNoDataMaxHr.visible()
                tvMinHr.gone()
                tvMaxHr.gone()
            }
            return
        } else {
            binding.heartRate.apply {
                tvNoDataMinHr.gone()
                tvNoDataMaxHr.gone()
                tvMinHr.visible()
                tvMaxHr.visible()
            }
        }
        val lastHr = listHr.last()
        binding.heartRate.tvStatus.text = Utils.getStatusHeartRate(
            requireContext(),
            lastHr.status
        )
        val minBs = listHr.minByOrNull { (it.heartRate) }
        val maxBs = listHr.maxByOrNull { (it.heartRate) }
        binding.heartRate.tvMinHr.text = minBs?.heartRate.toString()
        binding.heartRate.tvMaxHr.text = maxBs?.heartRate.toString()
    }

    @SuppressLint("SetTextI18n")
    private fun initDataBmi() {
        val listBmi = trackerViewModel.weightBmis
        if (listBmi.isEmpty()) {
            binding.weightBmi.apply {
                tvNoDataMinBmi.visible()
                tvNoDataMaxBmi.visible()
                tvMinBmi.gone()
                tvMaxBmi.gone()
            }
            return
        } else {
            binding.weightBmi.apply {
                tvNoDataMinBmi.gone()
                tvNoDataMaxBmi.gone()
                tvMinBmi.visible()
                tvMaxBmi.visible()
            }
        }
        val lastBmi = listBmi.last()
        if (lastBmi.status.length > 8) {
            binding.weightBmi.tvStatus.text =
                getStatusBmi(requireContext(), lastBmi.status)
        } else {
            binding.weightBmi.tvStatus.text = getStatusBmi(requireContext(), lastBmi.status)
        }
        val minBmi = listBmi.minByOrNull { (it.bmi) }
        val maxBmi = listBmi.maxByOrNull { (it.bmi) }
        binding.weightBmi.tvMinBmi.text = String.format("%.2f", minBmi?.bmi)
        binding.weightBmi.tvMaxBmi.text = String.format("%.2f", maxBmi?.bmi)
    }

    private fun startPreview(view: View) {
        view.setOnSingleClickListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java)
            intent.putExtra(Utils.KEY_TRACKER, getType(view))
            startActivity(intent)
        }
    }

    private fun getType(view: View): Int {
        return when (view) {
            binding.bloodPressure.btnAddRecord -> 0
            binding.bloodSugar.btnAddRecord -> 1
            binding.heartRate.btnAddRecord -> 2
            else -> 3
        }
    }

    override fun isVisibleBottomNavigation() = true

    override fun onResume() {
        super.onResume()

//        if (isCalculated) {
//            binding.weightBmi.txtCalculate.text = getString(R.string.recalculate)
//        } else {
//            binding.weightBmi.txtCalculate.text = getString(R.string.calculate)
//        }
    }

}