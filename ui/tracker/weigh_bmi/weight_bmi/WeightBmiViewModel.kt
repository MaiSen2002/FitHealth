package com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.weight_bmi

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.domain.BmiData
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class WeightBmiViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    var bmiData = BmiData()

    private val _isSuccessLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isSuccessLiveData: LiveData<Boolean>
        get() = _isSuccessLiveData
    private val _dateLiveData: MutableLiveData<Long> = MutableLiveData()
    val dateLiveData: LiveData<Long>
        get() = _dateLiveData
    private val _timeLiveData: MutableLiveData<String> = MutableLiveData()
    val timeLiveData: LiveData<String>
        get() = _timeLiveData

    fun initData() {
        bmiData.apply {
            date = Calendar.getInstance().time.time
            time = toTimeString(
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE)
            )
        }
    }


    fun onDateChange(value: Long) {
        _dateLiveData.value = value
        bmiData.date = value
    }

    fun onTimeChange(hour: Int, minute: Int) {
        _timeLiveData.value = toTimeString(hour, minute)
        bmiData.time = toTimeString(hour, minute)
    }

    fun validateData() = bmiData.height == 0 && bmiData.weight == 0
    fun onHeightChange(value: Int) {
        bmiData.height = value
    }

    fun onWeightChange(value: Int) {
        bmiData.weight = value
    }

    fun onGenderChange(isMale: Boolean) {
        bmiData.gender = isMale
    }

    fun calculateBmi(context: Context) {
        val bmi = bmiData.weight / (bmiData.height * 0.01 * bmiData.height * 0.01)
        bmiData.bmi = bmi
        bmiData.status = when (bmi) {
            in 0.0..15.9 -> context.getString(R.string.very_severely_underweight)
            in 16.0..16.9 -> context.getString(R.string.severely_underweight)
            in 17.0..18.4 -> context.getString(R.string.underweight)
            in 18.5..24.9 -> context.getString(R.string.normal)
            in 25.0..29.9 -> context.getString(R.string.overweight)
            in 30.0..34.9 -> context.getString(R.string.obese_class_1)
            in 35.0..39.9 -> context.getString(R.string.obese_class_2)
            else -> context.getString(R.string.obese_class_3)
        }
    }

    fun saveData() {
        viewModelScope.launch {
//            repository.addBmi(bmiData)
            _isSuccessLiveData.postValue(true)
        }
    }
}