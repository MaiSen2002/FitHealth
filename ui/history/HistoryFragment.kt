package com.bloodpressure.bloodtracker.bptracker.ui.history

import android.content.Intent
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentHistoryBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BloodDataModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodType
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.result.ResultActivity
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: HistoryAdapter

    override fun createViewBinding() = FragmentHistoryBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        adapter = HistoryAdapter()
        binding.rcHistory.setHasFixedSize(false)
        binding.rcHistory.adapter = adapter
    }

    override fun initializeEvent() {
        viewModel.dataLiveData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        adapter.onItemClicked = {
            handleItemClicked(it)
        }

        adapter.onItemDeleteClicked = { bloodDataModel ->
            DeleteConfirmDialog.open(childFragmentManager) {
                when (bloodDataModel.type) {
                    BloodType.BMI -> {
                        viewModel.deleteBmiData(viewModel.listBmi.find { it.id == bloodDataModel.id }!!)
                        for (item in viewModel.listBmi) {
                            preferenceHelper.setBoolean(Utils.IS_CALCULATED, item.bmi == 0.0)
                        }
                    }

                    BloodType.BLOOD_SUGAR -> viewModel.deleteBloodSugar(viewModel.bloodSugar.find { it.id == bloodDataModel.id }!!)
                    BloodType.BLOOD_PRESSURE -> viewModel.deleteBloodPressure(viewModel.bloodPressures.find { it.id == bloodDataModel.id }!!)
                    else -> viewModel.deleteHeartRate(viewModel.heartRate.find { it.id == bloodDataModel.id }!!)
                }
            }
        }

        viewModel.isSuccessLiveData.observe(viewLifecycleOwner) {
            initializeData()
        }

        binding.btnStatistics.setOnSingleClickListener {
            StatisticsFragment().pushToScreen(requireActivity() as MainActivity)
        }
    }

    override fun initializeData() {
        viewModel.initData()
    }

    private fun handleItemClicked(bloodDataModel: BloodDataModel) {
        val intent = Intent(requireActivity(), ResultActivity::class.java)
        intent.putExtra(Utils.KEY_EDIT, getTypeEdit(bloodDataModel))
        intent.putExtra(Utils.KEY_FROM_HISTORY, true)
        intent.putExtra(Utils.KEY_TRACKER, getType(bloodDataModel))
        intent.putExtra(
            Utils.KEY_DATA,
            getData(bloodDataModel)
        )
        startActivity(intent)
    }

    private fun getData(bloodDataModel: BloodDataModel): String {
        return when (bloodDataModel.type) {
            BloodType.BLOOD_PRESSURE -> Gson().toJson(viewModel.bloodPressures.find { it.id == bloodDataModel.id })
            BloodType.BLOOD_SUGAR -> Gson().toJson(viewModel.bloodSugar.find { it.id == bloodDataModel.id })
            BloodType.HEART_RATE -> Gson().toJson(viewModel.heartRate.find { it.id == bloodDataModel.id })
            else -> Gson().toJson(viewModel.listBmi.find { it.id == bloodDataModel.id })
        }
    }

    private fun getType(bloodDataModel: BloodDataModel): Int {
        return when (bloodDataModel.type) {
            BloodType.BLOOD_PRESSURE -> 0
            BloodType.BLOOD_SUGAR -> 1
            BloodType.HEART_RATE -> 2
            BloodType.BMI -> 3
            else -> 4
        }
    }

    private fun getTypeEdit(bloodDataModel: BloodDataModel): String {
        return when (bloodDataModel.type) {
            BloodType.BLOOD_PRESSURE -> Utils.EDIT_BLOOD_PRESSURE
            BloodType.BLOOD_SUGAR -> Utils.EDIT_BLOOD_SUGAR
            BloodType.HEART_RATE -> Utils.EDIT_HEART_RATE
            BloodType.BMI -> ""
            else -> ""
        }
    }

    override fun isVisibleBottomNavigation() = true

}