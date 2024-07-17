package com.bloodpressure.bloodtracker.bptracker.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseActivity
import com.bloodpressure.bloodtracker.bptracker.databinding.ActivityResultBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.preview.PreviewWeightBmiFragment
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultActivity : BaseActivity<ActivityResultBinding>() {

    override fun initializeView() {
        setStatusBarBackground(R.color.color_blood)
    }

    override fun initializeComponent() {
        val type = intent.getIntExtra(Utils.KEY_TRACKER, 0)
        val data = intent.getStringExtra(Utils.KEY_DATA)
        val isFromHistory = intent.getBooleanExtra(Utils.KEY_FROM_HISTORY, false)

        var bundle: Bundle? = null
        if (data != null) {
            bundle = bundleOf(getType(type) to data)
        }


        when (type) {
            0 -> {
                val fragment = ResultBloodPressureFragment()
                ResultBloodPressureFragment.isFromHistory = isFromHistory
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }

            1 -> {
                val fragment = ResultBloodSugarFragment()
//                EditBloodSugarFragment.isEdit = true
                ResultBloodSugarFragment.isFromHistory = isFromHistory
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }

            2 -> {
                val fragment = ResultHeartRateFragment()
//                ResultHeartRateFragment.isEdit = true
                ResultHeartRateFragment.isFromHistory = isFromHistory
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }

            3 -> {
                val fragment = PreviewWeightBmiFragment()
                PreviewWeightBmiFragment.isFromTracker = false
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }
        }
    }

    override fun initializeEvent() {

    }

    override fun initializeData() {

    }

    override fun bindView() {

    }

    private fun getType(type: Int): String {
        return when (type) {
            0 -> Utils.KEY_BLOOD_PRESSURE
            1 -> Utils.KEY_BLOOD_SUGAR
            2 -> Utils.KEY_HEART_RATE
            else -> Utils.KEY_WEIGHT_BMI
        }
    }

    override fun createViewBinding(layoutInflater: LayoutInflater) =
        ActivityResultBinding.inflate(layoutInflater)
}