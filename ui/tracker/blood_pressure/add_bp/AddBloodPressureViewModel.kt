package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.add_bp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodpressure.bloodtracker.bptracker.domain.BPInfoDataModel
import com.bloodpressure.bloodtracker.bptracker.domain.BloodPressure
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddBloodPressureViewModel @Inject constructor(
    private val bpRepository: AppRepository
) :
    ViewModel() {

    var data = listOf<BPInfoDataModel>()
    var bloodPressure = BloodPressure()

    private val _bpInfoLiveData: MutableLiveData<BPInfoDataModel> = MutableLiveData()
    val bpInfoLiveData: LiveData<BPInfoDataModel>
        get() = _bpInfoLiveData

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
        bloodPressure.apply {
            date = Calendar.getInstance().time.time
            time = toTimeString(
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE)
            )
        }
    }

    fun onDateChange(value: Long) {
        _dateLiveData.value = value
        bloodPressure.date = value
    }

    fun onTimeChange(hour: Int, minute: Int) {
        _timeLiveData.value = toTimeString(hour, minute)
        bloodPressure.time = toTimeString(hour, minute)
    }

    fun onSystolicChange(value: Int) {
        bloodPressure.systolic = value
        getInfoBloodPressure()
    }

    fun onDiastolicChange(value: Int) {
        bloodPressure.diastolic = value
        getInfoBloodPressure()
    }

    fun saveBloodPressure(context: Context, note: String) {
        bloodPressure.note = note
        bloodPressure.status = _bpInfoLiveData.value!!.status
        bloodPressure.icon = _bpInfoLiveData.value!!.icon

        viewModelScope.launch {
            bpRepository.addBloodPressure(bloodPressure)
            bloodPressure = bpRepository.getNewestBloodPressure(context)
            _isSuccessLiveData.postValue(true)
        }
    }

    fun getInfoBloodPressure() {
        val systolic = bloodPressure.systolic
        val diastolic = bloodPressure.diastolic

        _bpInfoLiveData.value = if (systolic > 180 || diastolic > 120)
            data[4]
        else if (systolic in 140..180 || diastolic in 90..120)
            data[3]
        else if (systolic in 130..139 || diastolic in 80..89)
            data[2]
        else if (systolic in 120..129 && diastolic in 60..79)
            data[1]
        else data[0]

    }

}