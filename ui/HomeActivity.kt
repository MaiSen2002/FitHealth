package com.bloodpressure.bloodtracker.bptracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseActivity
import com.bloodpressure.bloodtracker.bptracker.databinding.ActivityHomeBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.edit.EditBloodPressureFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.preview.PreviewBloodPressureFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.edit.EditBloodSugarFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.preview.PreviewBloodSugarFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate.EditHeartRateFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate.PreviewHeartRateFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.preview.PreviewWeightBmiFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.weight_bmi.WeightBmiFragment
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun createViewBinding(layoutInflater: LayoutInflater) =
        ActivityHomeBinding.inflate(layoutInflater)

    override fun initializeView() {

        setStatusBarBackground(R.color.color_blood)

        val type = intent.getIntExtra(Utils.KEY_TRACKER, 0)
        val data = intent.getStringExtra(Utils.KEY_DATA)
        val edit = intent.getStringExtra(Utils.KEY_EDIT)

        var bundle: Bundle? = null
        if (data != null) {
            bundle = bundleOf(getType(type) to data)
        }

        when (type) {
            0 -> {
                val fragment = PreviewBloodPressureFragment()
                PreviewBloodPressureFragment.isFromTracker = true
                if (bundle != null) {
                    fragment.arguments = bundle
                }

                fragment.pushToScreen(this)
            }

            1 -> {
                val fragment = PreviewBloodSugarFragment()
                PreviewBloodSugarFragment.isFromTracker = true
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }

            2 -> {
                val fragment = PreviewHeartRateFragment()
                PreviewHeartRateFragment.isFromTracker = true
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }

            3 -> {
                val fragment = WeightBmiFragment()
                PreviewWeightBmiFragment.isFromTracker = true
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }

            4 -> {
                val fragment = EditBloodPressureFragment()
                EditBloodPressureFragment.isFromTracker = true
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }

            5 -> {
                val fragment = EditBloodSugarFragment()
                EditBloodSugarFragment.isEdit = true
                EditBloodSugarFragment.isFromTracker = true
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }

            6 -> {
                val fragment = EditHeartRateFragment()
                EditHeartRateFragment.isEdit = true
                EditHeartRateFragment.isFromTracker = true
                if (bundle != null) {
                    fragment.arguments = bundle
                }
                fragment.pushToScreen(this)
            }


        }
    }

    private fun getType(type: Int): String {
        return when (type) {
            0 -> Utils.KEY_BLOOD_PRESSURE
            1 -> Utils.KEY_BLOOD_SUGAR
            2 -> Utils.KEY_HEART_RATE
            3 -> Utils.KEY_WEIGHT_BMI
            4 -> Utils.KEY_BLOOD_PRESSURE
            5 -> Utils.KEY_BLOOD_SUGAR
            else -> Utils.KEY_HEART_RATE
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

}