package com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRate
import com.bloodpressure.bloodtracker.bptracker.domain.HeartRateStatus
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddHeartRateViewModel @Inject constructor(private val repository: AppRepository) :
    ViewModel() {
    var heartRate = HeartRate()

    private val _isSaveSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isSaveSuccess: LiveData<Boolean>
        get() = _isSaveSuccess

    private val _dateLiveData: MutableLiveData<Long> = MutableLiveData()
    val dateLiveData: LiveData<Long>
        get() = _dateLiveData

    private val _timeLiveData: MutableLiveData<String> = MutableLiveData()
    val timeLiveData: LiveData<String>
        get() = _timeLiveData

    fun initArguments(bundle: Bundle?) {
        val dataText = bundle?.getString(Utils.KEY_HEART_RATE)
        dataText?.let {
            heartRate = Gson().fromJson(it, HeartRate::class.java)
        }
    }

    fun onTimeChange(hour: Int, minute: Int) {
        _timeLiveData.value = toTimeString(hour, minute)
        heartRate.time = toTimeString(hour, minute)
    }

    fun onDateChange(value: Long) {
        _dateLiveData.value = value
        heartRate.date = value

    }

    fun initData(context: Context) {
        heartRate.apply {
            date = Calendar.getInstance().time.time
            time = toTimeString(
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE)
            )
            heartRate = 80
            status = HeartRateStatus.NORMAL.ordinal
            description = context.getString(R.string.resting_heart_rate_normal)
            icon = R.drawable.ic_normal_heart_rate
            color = "#00C57E"
        }
    }

    fun addHeartRate() {
        viewModelScope.launch {
            repository.addHeartRate(heartRate)
            heartRate = repository.getNewestHeartRate()
            _isSaveSuccess.postValue(true)
        }
    }

    fun updateHeartRate() {
        viewModelScope.launch {
            repository.updateHeartRate(heartRate)
            _isSaveSuccess.postValue(true)
        }
    }

    fun onValueChange(value: Int, context: Context) {
        if (value <= 60) {
            heartRate.color = "#41ACE9"
            heartRate.icon = R.drawable.ic_low_heart_rate
            heartRate.description = context.getString(R.string.resting_heart_rate_low)
            heartRate.status = HeartRateStatus.LOW.ordinal
        } else if (value in 61..100) {
            heartRate.color = "#00C57E"
            heartRate.icon = R.drawable.ic_normal_heart_rate
            heartRate.description = context.getString(R.string.resting_heart_rate_normal)
            heartRate.status = HeartRateStatus.NORMAL.ordinal
        } else {
            heartRate.color = "#FB5555"
            heartRate.icon = R.drawable.ic_diabetes_heart_rate
            heartRate.description = context.getString(R.string.resting_heart_rate_diabetes)
            heartRate.status = HeartRateStatus.DIABETES.ordinal
        }

        heartRate.heartRate = value
    }
}