package com.bloodpressure.bloodtracker.bptracker.ui.info

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.domain.InfoDetailDataModel
import com.bloodpressure.bloodtracker.bptracker.util.Utils

class InfoDetailViewModel : ViewModel() {
    var type = ""

    fun initArguments(bundle: Bundle) {
        type = bundle.getString(Utils.KEY_TYPE)!!
    }

    fun initData(context: Context): List<InfoDetailDataModel> {
        val data = arrayListOf<InfoDetailDataModel>()
        when (type) {
            Utils.KEY_BLOOD_PRESSURE -> {
                data.add(
                    InfoDetailDataModel(
                        context.getString(R.string.information),
                        context.getString(R.string.information_content)
                    )
                )
                data.add(
                    InfoDetailDataModel(
                        context.getString(R.string.what_is_blood_pressure),
                        context.getString(R.string.what_is_blood_pressure_content)
                    )
                )
                data.add(
                    InfoDetailDataModel(
                        context.getString(R.string.how_is_high_blood_pressure_diagnosed),
                        context.getString(R.string.how_is_high_blood_pressure_diagnosed_content)
                    )
                )
            }

            Utils.KEY_BLOOD_SUGAR -> {
                data.add(
                    InfoDetailDataModel(
                        context.getString(R.string.why_test_your_blood_sugar),
                        context.getString(R.string.why_test_your_blood_sugar_content)
                    )
                )
                data.add(
                    InfoDetailDataModel(
                        context.getString(R.string.type_1_diabetes),
                        context.getString(R.string.type_1_diabetes_content)
                    )
                )
                data.add(
                    InfoDetailDataModel(
                        context.getString(R.string.type_2_diabetes),
                        context.getString(R.string.type_2_diabetes_content)
                    )
                )
            }

            Utils.KEY_HEART_RATE -> {
                data.add(
                    InfoDetailDataModel(
                        context.getString(R.string.what_should_you_know_about_your_pulse_rate),
                        context.getString(R.string.what_should_you_know_about_your_pulse_rate_content)
                    )
                )
                data.add(
                    InfoDetailDataModel(
                        context.getString(R.string.how_other_factors_afect_pulse_rate),
                        context.getString(R.string.how_other_factors_afect_pulse_rate_content)
                    )
                )
            }

            Utils.KEY_WEIGHT_BMI -> {
                data.add(
                    InfoDetailDataModel(
                        context.getString(R.string.information),
                        context.getString(R.string.weigh_bmi_content)
                    )
                )
            }
        }
        return data
    }
}