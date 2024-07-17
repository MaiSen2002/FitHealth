package com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.preview

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodpressure.bloodtracker.bptracker.domain.BmiData
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PreviewWeightBmiViewModel @Inject constructor(private val repository: AppRepository) :
    ViewModel() {
    var bmiData: BmiData? = null
    var calculated = false

    fun initArguments(bundle: Bundle?) {
        val result = bundle?.getString(Utils.KEY_WEIGHT_BMI)
        result?.let {
            bmiData = Gson().fromJson(it, BmiData::class.java)
        }
    }

    fun initData() {
        if (bmiData != null) {
            calculated = true
            return
        }
        runBlocking {
            bmiData = repository.getNewestBmiData()
            calculated = false
        }
    }

    fun saveBmi() {
        viewModelScope.launch {
            bmiData?.let {
                repository.addBmi(it)
            }
        }
    }
}