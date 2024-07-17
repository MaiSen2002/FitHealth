package com.bloodpressure.bloodtracker.bptracker.domain

import android.content.Context
import com.bloodpressure.bloodtracker.bptracker.R

enum class BloodType {
    BLOOD_PRESSURE,
    BLOOD_SUGAR,
    HEART_RATE,
    BMI,
    EDIT_BLOOD_PRESSURE;

    fun getName(context: Context): String{
        return when(this){
            BLOOD_PRESSURE -> context.getString(R.string.blood_pressure)
            BLOOD_SUGAR -> context.getString(R.string.blood_sugar)
            HEART_RATE -> context.getString(R.string.heart_rate)
            else -> context.getString(R.string.weight_bmi)
        }
    }
}