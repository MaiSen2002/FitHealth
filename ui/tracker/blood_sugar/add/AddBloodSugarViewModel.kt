package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.add

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugar
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import java.util.Calendar

class AddBloodSugarViewModel :
    ViewModel() {
    var bloodSugar = BloodSugar()

    private val _dateLiveData: MutableLiveData<Long> = MutableLiveData()
    val dateLiveData: LiveData<Long>
        get() = _dateLiveData

    private val _timeLiveData: MutableLiveData<String> = MutableLiveData()
    val timeLiveData: LiveData<String>
        get() = _timeLiveData

    fun initArguments(bundle: Bundle?) {
        val result = bundle?.getString(Utils.KEY_BLOOD_SUGAR)
        result?.let {
            bloodSugar = Gson().fromJson(it, BloodSugar::class.java)
        }
    }

    fun initData() {
        bloodSugar.apply {
            date = Calendar.getInstance().time.time
            time = toTimeString(
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE)
            )
        }
    }

    fun onTimeChange(hour: Int, minute: Int) {
        _timeLiveData.value = toTimeString(hour, minute)
        bloodSugar.time = toTimeString(hour, minute)
    }

    fun onDateChange(value: Long) {
        _dateLiveData.value = value
        bloodSugar.date = value

    }

    fun onValueChange(value: Double) {
        bloodSugar.sugarConcentration = value
    }

}